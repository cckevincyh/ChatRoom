package chat_server.server.backstage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import chat.common.Message;
import chat.common.MessageType;
import chat_server.server.tools.ServerThreadCollection;
import chat_server.server.view.Server_Frame;

/**
 * 登陆成功后继续与客户端通信的服务器后台线程类
 * @author Administrator
 *	Server_Continue_Connect_Client_Thread
 */
public class ServerConClient implements Runnable{
	private Socket s = null;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private String UserName;
	private boolean isConnect = true;
	//将客户端的Socket传入
	public ServerConClient(Socket s,String UserName){
		this.s = s;
		this.UserName = UserName;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(isConnect){
				//接受客户端继续发来的信息
				try{
					ois = new ObjectInputStream(s.getInputStream());
				}catch(SocketException e){
					ServerThreadCollection.RemoveServerContinueConnetClient(UserName);
					//更新服务器的在线用户
					ServerUpdataOnline();
					//通知其他人更新在线用户
					UpdataOnline();
					//关闭Socket
					s.close();
					e.printStackTrace();
					break;
				}
				Message mess = (Message)ois.readObject();
				//判断信息的类型，并进行转发处理
				Server_Frame.showMessage(mess);
				//如果是发给全部人的信息
				if(mess.getMessageType().equals(MessageType.Common_Message_ToAll)){
					//获得在线用户
					String string = ServerThreadCollection.GetOnline();
					String[] strings = string.split(" ");
					String Name = null;
					for(int i=0;i<strings.length;i++){
						Name = strings[i];
						if(!mess.getSender().equals(Name)){
							//设置接收用户
							mess.setGetter(Name);
							//获得其他服务器端与客户端通信的线程
							ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
							os = new ObjectOutputStream(sccc.s.getOutputStream());
							os.writeObject(mess);
						}
					}
				}else if(mess.getMessageType().equals(MessageType.Common_Message_ToPerson)){
					//根据获得者取得服务器端与客户端通信的线程
					ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(mess.getGetter());
					os = new ObjectOutputStream(sccc.s.getOutputStream());
					os.writeObject(mess);
				}else if(mess.getMessageType().equals(MessageType.Send_FileToAll)){
					ServerManage.Send_SystemMessage("系统消息："+mess.getSender()+"给所有人发送了文件名为："+mess.getContent()+"的文件\r\n");
					Server_Frame.ShowSystemMessage("系统消息："+mess.getSender()+"给所有人发送了文件名为："+mess.getContent()+"的文件\r\n");
					//如果是发送文件给所有人
					SendFileThread r = new SendFileThread(mess,0);
					Thread t = new Thread(r);
					t.start();

				}else if(mess.getMessageType().equals(MessageType.Send_FileToPerson)){
					//如果是发送给个人
					Server_Frame.ShowSystemMessage(mess.getSender()+"给"+mess.getGetter()+"发送了文件名为："+mess.getContent()+"的文件\r\n");
					SendFileThread r = new SendFileThread(mess,1);
					Thread t = new Thread(r);
					t.start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通知其他人用户名为在线用户要更新了
	 * @param Name 用户名
	 */
	public void UpdataOnline(){
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		for(int i=0;i<strings.length;i++){
			String Getter = strings[i];
			Message mess = new Message();
			//发送在线用户的名单
			mess.setContent(string);	
			mess.setGetter(Getter);
			mess.setMessageType(MessageType.Send_Online);
			try {
				//取出每个服务器端与客户端通信的线程
				ServerConClient scc = ServerThreadCollection.getServerContinueConnetClient(Getter);
				if(scc!=null){
					os = new ObjectOutputStream(scc.s.getOutputStream());
					os.writeObject(mess);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
			
	}

	/**
	 * 服务器更新在线用户列表的方法
	 */
	public void ServerUpdataOnline(){
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		//设置在线用户
		Server_Frame.SetOnLline(string);
	}
	
	

	public Socket getS() {
		return s;
	}


	public void setS(Socket s) {
		this.s = s;
	}
	
	/**
	 * 关闭线程序的方法
	 */
	public void CloseThread(){
		this.isConnect = false;
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
