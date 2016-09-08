package chat_room.client.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.common.Message;
import chat.common.MessageType;
import chat_room.client.backstage.ClienManage;
import chat_room.client.tools.ManageClientPersonCollection;
import chat_room.client.tools.Tools;

/**
 * 单人聊天的界面
 * @author Administrator
 *
 */
public class ClientFrame extends JFrame implements WindowListener{
	private static JTextArea jta1;	//文本区域
	private JTextArea jta2;
	private JButton jb1,jb2,jb3;	//按扭
	private JScrollPane jsp1,jsp2;	//滚动条
	private ClienManage cm;	//后台处理对象
	private JFileChooser jfc;	//文件选择器
	private String Sender;
	private String Getter;
	private JFrame jf;
	public ClientFrame(final String Sender,final String Getter,final ClienManage cm){
		super(Sender+"正在与"+Getter+"聊天中");
		this.jf = this;
		this.cm = cm;
		this.Sender = Sender;
		this.Getter = Getter;
		Container c = this.getContentPane();
		//设置大小
		this.setSize(400, 380);
		//设置空布局
		this.setLayout(null);
		
		jta1 = new JTextArea();
		//设置不可编辑
		jsp1 = new JScrollPane(jta1);
		jta1.setEditable(false);
		jsp1.setBounds(10, 10, 375, 190);
		c.add(jsp1);
		
		jta2 = new JTextArea();
		jsp2 = new JScrollPane(jta2);
		//获取光标
		jta2.grabFocus();
		jsp2.setBounds(10, 205, 375, 110);
		c.add(jsp2);
		
		
		jb1 = new JButton("发送");
		jb1.setBounds(320, 320, 60,20);
		//发送按扭注册事件监听
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String con = jta2.getText();
				String Time = (new Date()).toLocaleString();
				Message mess = new Message();
				mess.setContent(con);
				mess.setTime(Time);
				mess.setSender(Sender);
				mess.setGetter(Getter);
				mess.setMessageType(MessageType.Common_Message_ToPerson);
				jta1.append(Sender+"    "+Time+"\r\n"+con+"\r\n");
				//发送消息
				cm.SendMessage(mess);
				jta2.setText("");
				//获取光标
				jta2.grabFocus();
			}
		});
		c.add(jb1);
		
		jb2 = new JButton("发送文件");
		jb2.setBounds(10, 320, 100, 20);
		//发送文件按扭注册事件监听
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jfc = new JFileChooser();
				jfc.showOpenDialog(jf);
				Message mess = new Message();
				mess.setMessageType(MessageType.Send_FileToPerson);
				mess.setSender(Sender);
				mess.setGetter(Getter);
				String FileName = jfc.getName(jfc.getSelectedFile());
				//设置文件名
				mess.setContent(FileName);
				
				if( jfc.getSelectedFile().toPath().toString()!=null){
					//发送消息类型
					cm.SendMessage(mess);
					//获得路径
					String path = jfc.getSelectedFile().toPath().toString();
					//发送文件
					cm.SendFile(path);
					Message m = new Message();
					m.setMessageType(MessageType.Common_Message_ToPerson);
					m.setSender(Sender);
					m.setGetter(Getter);
					m.setTime(new Date().toLocaleString());
					m.setContent("我给你发送了文件名为："+FileName+" 的文件\r\n");
					ShowMessage(m);
					cm.SendMessage(m);
				}
			}
		});
		c.add(jb2);
		
		jb3 = new JButton("清空聊天记录");
		jb3.setBounds(150, 320, 120,20);
		//清空聊天记录按扭注册事件监听
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jta1.setText("");
			}
		});
		c.add(jb3);
		
		//注册窗口事件监听
		this.addWindowListener(this);
		//设置大小不可改变
		this.setResizable(false);
		//设置在屏幕中间
		Tools.setFrameCenter(this);
		this.setVisible(true);
	}
	
	
	/***
	 * 显示信息在个人聊天界面
	 * @param mess
	 */
	public static void ShowMessage(Message mess){
		jta1.append(mess.getSender()+"    "+mess.getTime()+"\r\n"+mess.getContent()+"\r\n");
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		String str = Sender+" "+Getter;
		ManageClientPersonCollection.removeClientPerson(str);
	}


	@Override
	public void windowClosed(WindowEvent e) {
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
