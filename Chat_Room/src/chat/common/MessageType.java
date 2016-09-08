package chat.common;

public interface MessageType {
	public String UserLogin = "@UsersLogin";	//用户登陆信息
	public String UserRegister = "@UserRegister"; //用户注册信息
	public String Login_Success = "@Login_Success";//登陆成功
	public String Login_Fail = "@Login_Fail";//登陆失败
	public String Register_Success = "@Register_Success"; //注册成功
	public String Register_Fail = "Register_Fail";	//注册失败
	public String Common_Message_ToAll ="@Common_Message_ToAll";//发送普通消息给所有人
	public String Common_Message_ToPerson ="@Common_Message_ToPerson" ;//发送普通消息给个人
	public String Send_FileToAll = "@Send_FileToAll";//发送文件给所有人
	public String Send_FileToPerson = "@Send_FileToPerson";//发送文件给个人
	public String Get_Online = "@Get_Online";//获得在线人员
	public String Send_Online = "@Send_Online";//返回在线人员
	public String SendUser = "@Send_User";//发送用户信息
	public String CommonMessage ="@CommonMessage";	//普通消息
	public String System_Messages = "@System_Messages"; //系统消息
	public String Login = "@Login";	//登陆过了
	public String NoLogin = "@NoLogin"; //没登陆过
	public String Recive = "@Recive";	//接收
	public String NoRecive = "@NoRevice";	//拒绝接收
}
