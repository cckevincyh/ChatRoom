package chat_server.server.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.common.Message;
import chat.common.MessageType;
import chat_server.server.backstage.ServerConClient;
import chat_server.server.backstage.ServerManage;
import chat_server.server.tools.ServerThreadCollection;
import chat_server.server.tools.Tools;

public class Server_Frame extends JFrame{
	//定义需要的组件
	private JButton jb1,jb2,jb3;	//按钮
	private static JButton jb4;
	private static JList jl;	//列表
	private static JTextArea jta1,jta2;	//文本区域
	private JLabel jla1;	//标签
	private static JLabel jla2;
	private JScrollPane jsp1,jsp2,jsp3;	//滚动条
    private ServerManage server;
    private JFrame jf;
    private static String[] onLine={""};//在线用户的数组
	public Server_Frame(){
		super("聊天室服务器");
		this.jf = this;
		Container c = this.getContentPane();
		//设置窗体大小
		this.setSize(500,410);
		//设置空布局
		this.setLayout(null);
		jb1 = new JButton("开启服务器");
		jb1.setBounds(100, 10, 100, 30);
		c.add(jb1);
		//开启服务器按扭注册事件监听
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server = new ServerManage();
				Thread t = new Thread(server);
				t.start();
				jb1.setEnabled(false);
				jb2.setEnabled(true);
				jb3.setEnabled(true);
				jta2.setEditable(true);
			}
		});
		  
		jb2 = new JButton("关闭服务器");
		jb2.setBounds(260, 10, 100, 30);
		c.add(jb2);
	    jb2.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		server.CloseServer();
				jb1.setEnabled(true);
				jb2.setEnabled(false);
				jb3.setEnabled(false);
				jta2.setEditable(false);
			}
		});
	    
	    
	    jla1 = new JLabel("在线人数：");
	    jla1.setBounds(370, 30, 80, 20);
	    c.add(jla1);
	    
	    jla2 = new JLabel("0");
	    jla2.setBounds(440, 30, 30, 20);
	    c.add(jla2);
	        
		jta1 = new JTextArea();
		//设置不可编辑
		jta1.setEditable(false);
		jsp1 = new JScrollPane(jta1);
		jsp1.setBounds(10, 50, 350, 200);
		c.add(jsp1);
		
		jl = new JList(onLine);
		jsp2 = new JScrollPane(jl);
		jsp2.setBounds(365, 50, 120, 285);
		c.add(jsp2);
		
		jta2 = new JTextArea();
		jta2.setEditable(false);
		jsp3 = new JScrollPane(jta2);
		jsp3.setBounds(10, 255, 350, 80);
		c.add(jsp3);
		
		jb3 = new JButton("发送系统消息");
		jb3.setEnabled(false);
		jb3.setBounds(230, 340, 130, 30);
		c.add(jb3);
		//发送系统消息按扭的事件监听
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = jta2.getText();
				//显示系统信息
				ShowSystemMessage("系统消息："+message+"\r\n");
				server.Send_SystemMessage("系统消息："+message+"\r\n");
				jta2.setText("");
				//获取光标
				jta2.grabFocus();
			}
		});
		
		jb4 = new JButton("强制下线");
		jb4.setEnabled(false);
		jb4.setBounds(370, 340, 100, 30);
		//注册强制下线按扭的事件监听
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = jl.getAnchorSelectionIndex();
				if(i==-1){
					JOptionPane.showMessageDialog(jf, "请选中一行");
				}else{
					//取出选中的用户，并取出其线程序
					ServerConClient scc = ServerThreadCollection.getServerContinueConnetClient(onLine[i]);
					scc.CloseThread();
				}
				
			}
		});
		c.add(jb4);
		
		
		//设置窗体大小不可改变
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Tools.setFrameCenter(this);
		this.setVisible(true);
		
	}
	
	
	
	
	
	/**
     * 设置在线用户的方法
     * @param strings 在线用户的字符串
     */
    public static void SetOnLline(String string){
    	if(string.equals("")){
	    		final String[] strings = {""};
	    		 //同时显示在线人数
	       	 ShowOnlineNumber("0");
	       	jb4.setEnabled(false);
	       	 jl.setModel(new AbstractListModel() {	 
	             public int getSize(){ 
	            	 return strings.length; 
	             } 
	             public Object getElementAt(int i){ 
	            	 return strings[i]; 
	            	 }
	             
	         });
    	}else{
    		jb4.setEnabled(true);
    		final String[] strings = string.split(" ");
    		onLine = strings;
	    	 //同时显示在线人数
	    	 ShowOnlineNumber(new Integer(strings.length).toString());
	    	 jl.setModel(new AbstractListModel() {	 
	             public int getSize(){ 
	            	 return strings.length; 
	             } 
	             public Object getElementAt(int i){ 
	            	 return strings[i]; 
	            	 }
	             
	         });
    	 }
    }
    
    /**
     * 显示信息在文本区域
     * @param mess
     */
    public static void showMessage(Message mess){
    	if(mess.getMessageType().equals(MessageType.Common_Message_ToAll)){
    		jta1.append(mess.getSender()+"对所有人说： "+mess.getContent()+"\r\n");
    	}else if(mess.getMessageType().equals(MessageType.Common_Message_ToPerson)){
    		jta1.append(mess.getSender()+"对"+mess.getGetter()+"说："+mess.getContent()+"\r\n");
    	}else if(mess.getMessageType().equals(MessageType.CommonMessage)){
    		jta1.append(mess.getContent()+"\r\n");
    	}
    	
    }

    /**
     * 显示在线人数
     */
    public static void ShowOnlineNumber(String num){
    	jla2.setText(num);
    }
    
    /**
     * 显示系统信息
     * @param str
     */
    public static void ShowSystemMessage(String str){
    	jta1.append(str);
    }

}
