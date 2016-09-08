package chat_room.client.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import chat.common.User;
import chat_room.client.backstage.ClienManage;
import chat_room.client.tools.Tools;
/**
 * 注册的界面
 * @author Administrator
 *
 */
public class Register_Frame extends JFrame {
	//定义需要的组件
	private JLabel jl1,jl2,jl3;	//标签
	private JTextField jtf;	//文本框
	private JPasswordField jpf1,jpf2;	//密码文本框
	private JButton jb1,jb2,jb3;	//按钮
	private JFrame jf;
	public Register_Frame(){
		super("注册界面");
		this.jf = this;
		this.setLayout(null);
		this.setSize(400,300);
		Container c = this.getContentPane();
		jl1 = new JLabel("用户名");
		jl1.setBounds(100, 60, 50, 20);
		c.add(jl1);
		
		jtf = new JTextField();
		//获取光标
		jtf.grabFocus();
		jtf.setBounds(160, 60, 120, 20);
		c.add(jtf);
		
		jl2 = new JLabel("密码");
		jl2.setBounds(100, 110, 50, 20);
		c.add(jl2);
		
		jpf1 = new JPasswordField();
		jpf1.setBounds(160, 110, 120, 20);
		c.add(jpf1);
		
		jl3 = new JLabel("确认密码");
		jl3.setBounds(100, 162, 70, 20);
		c.add(jl3);
		
		jpf2 = new JPasswordField();
		jpf2.setBounds(160, 162, 120, 20);
		c.add(jpf2);
		
		
		jb1 = new JButton("注册");
		jb1.setBounds(90, 210, 60, 20);
		c.add(jb1);
		 //注册按扭添加事件监听
        jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String Name = jtf.getText();
				String PassWord = new String(jpf1.getPassword());
				String RePassWord = new String(jpf2.getPassword());
				if(Name.equals("")||PassWord.equals("")){
					JOptionPane.showMessageDialog(jf, "用户名或密码不能为空");
				}else if(RePassWord.equals("")){
					JOptionPane.showMessageDialog(jf, "请确认密码");
				}else if(!RePassWord.equals(PassWord)){
					JOptionPane.showMessageDialog(jf, "确认密码错误");
				}else{
					User user = new User(Name, PassWord);
					ClienManage cm = new ClienManage();
					if(cm.IsConnect()){
						if(cm.Register(user)){
							JOptionPane.showMessageDialog(jf, "注册成功");
							jf.dispose();
							Login_Frame login = new Login_Frame();
						}else{
							JOptionPane.showMessageDialog(jf, "注册失败");
							jf.dispose();
						}
					}else{
						JOptionPane.showMessageDialog(jf, "连接不到服务器");
						jf.dispose();
					}
				}
				
			}
		});
		jb2 = new JButton("重置");
		jb2.setBounds(170, 210, 60, 20);
		c.add(jb2);
		//重置按扭注册事件监听
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jtf.setText("");
				jpf1.setText("");
				jpf2.setText("");
				//获取光标
				jtf.grabFocus();
			}
		});
		
		jb3 = new JButton("取消");
		jb3.setBounds(250, 210, 60, 20);
		c.add(jb3);
		//取消按扭注册事件监听
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jf.dispose();
				Login_Frame login = new Login_Frame();
			}
		});
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Tools.setFrameCenter(this);
		this.setVisible(true);
	}
	
	
}