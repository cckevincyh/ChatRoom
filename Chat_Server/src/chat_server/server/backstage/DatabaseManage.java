package chat_server.server.backstage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chat.common.User;

/**
 * 服务器后台的数据库处理类
 * @author Administrator
 *
 */
public class DatabaseManage {
	//定义连接数据库所需要的对象
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection ct = null;
	
	public void init(){
		//加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//得到连接
			ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/ChatRoomDao","root","c0223");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DatabaseManage(){
		this.init();
	}
	
	
	//取得用户信息
	public ResultSet GetUser(String Name){
		try {
			ps = ct.prepareStatement("select * from Users where Name=?");
			ps.setString(1, Name);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	//注册用户信息
	public boolean Register(User user){
		boolean b = true;
		try {
			ps = ct.prepareStatement("insert into Users(Name,PassWord) values(?,?)");
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassWords());
			if(ps.executeUpdate()!=1)  // 执行sql语句
			{
				b=false;
			}
		} catch (SQLException e) {
			b = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	//对是否登陆进行修改
	public boolean Update_IsLogin(User user,int isLogin){
		boolean b = true;
		try {
			ps = ct.prepareStatement("update Users set IsLogin=? where Name=?");
			ps.setInt(1, isLogin);
			ps.setString(2, user.getName());
			if(ps.executeUpdate()!=1)  // 执行sql语句
			{
				b=false;
			}
		} catch (SQLException e) {
			b = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	
	
	
	//关闭数据库资源
	public void close()
	{
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(ct!=null) ct.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
