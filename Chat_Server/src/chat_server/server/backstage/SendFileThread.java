package chat_server.server.backstage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import chat.common.Message;
import chat_server.server.tools.ServerThreadCollection;
/**
 * 服务器接收文件的线程类
 * @author Administrator
 *
 */
public class SendFileThread implements Runnable{
	private Socket s;
	private ServerSocket ss;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private Message mess;
	private int Type;	//0为发送给所有人，1为发送给个人
	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	public SendFileThread(Message mess,int Type){
		this.mess = mess;
		this.Type = Type;
		try {
			ss = new ServerSocket(8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			s = ss.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str;
		Long Name = null;;
		try {
			//封装输入流
			try {
				bis = new BufferedInputStream((s.getInputStream()));
				Name = System.currentTimeMillis();
				bos = new BufferedOutputStream(new FileOutputStream(Name+""+mess.getContent()));
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
			if(Type==0){
				//发送给所有人
				SendFileToAll(Name+""+mess.getContent());
			}else if(Type==1){
				//发送给个人
				SendFileToPerson(Name+""+mess.getContent());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				if(ss!=null){
					ss.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 发送给个人
	 */
	public void SendFileToPerson(String FileName){
		Socket s1 = null;;
		try {
			//根据获得者取得服务器端与客户端通信的线程
			ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(mess.getGetter());
			 bis = new BufferedInputStream(new FileInputStream(FileName));
			os = new ObjectOutputStream(sccc.getS().getOutputStream());
			os.writeObject(mess);
			InetAddress ip = sccc.getS().getInetAddress();
			s1 = new Socket(ip, 7777);
			bos = new BufferedOutputStream(s1.getOutputStream());
			String str;

			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(s1!=null){
					s1.shutdownOutput();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if(s1!=null){
					s1.close();
				}
				if(bis!=null){
					bis.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 发送文件给所有人
	 */
	public void SendFileToAll(String FileName){
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		String Name = null;
		for(int i=0;i<strings.length;i++){
			Name = strings[i];
			if(!mess.getSender().equals(Name)){
				//设置接收用户
				mess.setGetter(Name);
				Socket s1 = null;;
				try {
					 bis = new BufferedInputStream(new FileInputStream(FileName));
					//获得其他服务器端与客户端通信的线程
					ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
					os = new ObjectOutputStream(sccc.getS().getOutputStream());
					os.writeObject(mess);
					InetAddress ip = sccc.getS().getInetAddress();
					s1 = new Socket(ip, 7777);
					bos = new BufferedOutputStream(s1.getOutputStream());
					String str;
					byte[] bys = new byte[1024];
					int len = 0;
					while ((len = bis.read(bys)) != -1) {
						bos.write(bys, 0, len);
						bos.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						s1.shutdownOutput();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if(s1!=null){
							s1.close();
						}
						if(bis!=null){
							bis.close();
						}
						if(bos!=null){
							bos.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//休眠避免同步执行造成异常
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
