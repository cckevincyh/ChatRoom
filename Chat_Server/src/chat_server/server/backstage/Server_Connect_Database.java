package chat_server.server.backstage;

import java.sql.ResultSet;
import java.sql.SQLException;

import chat.common.User;

/**
 * 服务器端与数据库通信的后台类
 * @author c
 *
 */
public class Server_Connect_Database {
	
	private ResultSet rs = null;
	private DatabaseManage databaseManage = null;
	
	/**
	 * 检查是否登陆成功的方法
	 * @param user 用户对象
	 * @return 返回是否登陆成功
	 */
	public boolean CheckLogin(User user){
		boolean b = false;
		databaseManage = new DatabaseManage();
		rs = databaseManage.GetUser(user.getName());
		try {
			rs.next();
			String Password = rs.getString(2);
			if(Password.equals(user.getPassWords())){
				b =true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}finally{
			databaseManage.close();
		}
		return b;
	}
	
	/**
	 * 检查是否注册成功的方法
	 * @param user 用户对象
	 * @return 返回是否注册成功
	 */
	public boolean CheckRegister(User user){
		databaseManage = new DatabaseManage();
		return databaseManage.Register(user);
	}
	
	
	
	/**
	 * 检查是否重复登陆的方法
	 * @param user 用户对象
	 * @return 是否重复登陆,登陆过的返回true,否则返回false
	 */
	public boolean Check_IsLogin(User user){
		boolean b = false;
		databaseManage = new DatabaseManage();
		rs = databaseManage.GetUser(user.getName());
		try {
			rs.next();
			int IsLogin = rs.getInt(3);
			if(IsLogin==0){
				b =false;
			}else if(IsLogin==1){
				b = true;
			}
		} catch (SQLException e) {
			//如果还没注册则会抛出SQLException
			//所以这里设置为没有登陆过，交给验证是否登陆成功
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}finally{
			databaseManage.close();
		}
		return b;
	}
	
	
	/**
	 * 返回成功修改登陆情况
	 * @param user 用户对象
	 */
	public boolean  Update_IsLogin(User user,int isLogin){
		databaseManage = new DatabaseManage();
		return databaseManage.Update_IsLogin(user,isLogin);
	}
	
}
