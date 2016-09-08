package chat_room.client.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.common.Message;
import chat.common.MessageType;
import chat_room.client.backstage.ClienManage;
import chat_room.client.backstage.ClientConServer;
import chat_room.client.tools.ManageClientPersonCollection;
import chat_room.client.tools.Tools;
/**
 * 群聊的界面
 * @author Administrator
 *
 */
public class Client_Frame extends JFrame {
	//定义需要的组件
	private JLabel jla1;	//标签
	private static JLabel jla2;
	private JTextArea jta1,jta2;	//文本区域
	private JButton jb1;	//按钮
	private static JButton jb2;//按钮
	public JButton jb3,jb4;//按钮
	private static JList jl;	//列表
	private JScrollPane jsp1,jsp2,jsp3;	//滚动条
	private  ClienManage cm;	//后台处理对象
	private  JFrame jf;
	private static String[] onLine = {""};	//在线用户的数组
	private ClientFrame client;	//个人聊天界面对象
	private String Name;
	private JFileChooser jfc;	//文件选择器
	public Client_Frame(final String Name,final ClienManage cm){
		super("欢迎"+Name+"进入聊天室");
		this.Name = Name;
		this.cm = cm;
		this.jf = this;
		Container c = this.getContentPane();
		//设置空布局
		this.setLayout(null);
		//设置窗体大小
		this.setSize(500,410);
		jla1 = new JLabel("在线人数：");
		jla1.setBounds(365, 2, 70, 20);
		c.add(jla1);
		
		jla2 = new JLabel();
		jla2.setBounds(430, 2, 20, 20);
		c.add(jla2);
		
		jta1 = new JTextArea();
		//设置不可编辑
		jta1.setEditable(false);
		jsp1 = new JScrollPane(jta1);
		jsp1.setBounds(10, 20, 350, 200);
		c.add(jsp1);
		
		jta2 = new JTextArea();
		//获取光标
		jta2.grabFocus();
		jsp2 = new JScrollPane(jta2);
		jsp2.setBounds(10, 225, 350, 120);
		c.add(jsp2);
		
		jl = new JList(onLine);
		jsp3 = new JScrollPane(jl);
		jsp3.setBounds(365, 20, 120, 325);
		c.add(jsp3);
		
		jb1 = new JButton("发送");
		jb1.setBounds(300, 350, 60, 25);
		c.add(jb1);
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String con = jta2.getText();
	             Message mess = new Message();
	             mess.setSender(Name);
	             mess.setMessageType(MessageType.Common_Message_ToAll);
	             String time = (new Date().toLocaleString());
	             mess.setTime(time);
	             mess.setContent(con);
	             jta1.append(Name+"    "+time+"\r\n"+con+"\r\n");
	             cm.SendMessage(mess);
	             jta2.setText("");
	             //获取光标
	     		jta2.grabFocus();
			}
		});
		
		jb2 = new JButton("单人聊天 ");
		jb2.setEnabled(false);
		jb2.setBounds(380, 350, 100, 25);
		//注册单人聊天按扭的事件监听
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//返回定位选择索引。
				int i = jl.getAnchorSelectionIndex();
				if(i==-1){
					JOptionPane.showMessageDialog(jf, "请选中一行");
				}else{
					if(!Name.equals(onLine[i])){
						String str = Name+" "+onLine[i];
						//如果不存在这个界面就创建
						if(!ManageClientPersonCollection.isExist(str)){
							client = new ClientFrame(Name, onLine[i],cm);
						}
						//将个人聊天界面添加入集合中
						ManageClientPersonCollection.addClientPersonCollection(str, client);
					}else{
						JOptionPane.showMessageDialog(jf,"请选择其他在线用户");
					}
				}
			}
		});
		c.add(jb2);
		
		
		jb3 = new JButton("发送文件");
		jb3.setBounds(10, 350, 100, 25);
		//发送文件按扭注册事件监听
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jfc = new JFileChooser();
				jfc.showOpenDialog(jf);
				Message mess = new Message();
				mess.setMessageType(MessageType.Send_FileToAll);
				mess.setSender(Name);
				//设置文件名
				mess.setContent(jfc.getName(jfc.getSelectedFile()));
				//获得路径
				//选择了才能发送
				if(jfc.getSelectedFile().toPath().toString()!=null){
					//发送消息类型
					cm.SendMessage(mess);
					String path = jfc.getSelectedFile().toPath().toString();
					//发送文件
					cm.SendFile(path);
				}
			}
		});
		c.add(jb3);

		jb4 = new JButton("清空聊天记录");
		jb4.setBounds(150, 350, 120, 25);
		//清空聊天记录按扭注册事件监听
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jta1.setText("");
			}
		});
		c.add(jb4);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗体大小不可改变
		this.setResizable(false);
		Tools.setFrameCenter(this);
		this.setVisible(true);
		
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	/**
	 * 将普通信息显示在群聊窗口
	 * @param mess
	 */
	public void showMessageToAll(Message mess){
		jta1.append(mess.getSender()+"    "+mess.getTime()+"\r\n"+mess.getContent()+"\r\n");
	}
	
	public void ShowSystemMessage(Message mess){
		jta1.append(mess.getContent());
	}
	
	
	
	/**
     * 设置在线用户的方法
     * @param strings 在线用户的字符串
     */
    public static void SetOnLline(String string){
    	if(string.equals("")){
    		jb2.setEnabled(false);
	    		final String[] strings = {""};
	    		//同时显示在线人数
	       	 ShowOnlineNumber("0");
	       	jl.setModel(new AbstractListModel() {
	   		 
	            public int getSize(){ 
	           	 return strings.length; 
	            }
	   		 
	            public Object getElementAt(int i){ 
	           	 return strings[i]; 
	           	 }
	            
	        });
    	}else{
    		jb2.setEnabled(true);
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
     * 显示对话框
     * @param s 信息
     */
    public void ShowMessageDialog(String s){
    	JOptionPane.showMessageDialog(jf, s);
    	jf.dispose();
    }
    
    
   

	/**
     * 显示在线人数的方法
     * @param num 人数
     */
    public static void ShowOnlineNumber(String num){
    	jla2.setText(num);
    }

    /**
     * 根据用户的选择返回值
     * @return
     */
    public int ShowIsRecive(){
    	int i = JOptionPane.showConfirmDialog(jf, "是否要接收文件");
    	return i;
    }
    
	
}

