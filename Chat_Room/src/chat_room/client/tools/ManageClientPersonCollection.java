package chat_room.client.tools;

import java.util.HashMap;
import java.util.Set;

import chat_room.client.view.ClientFrame;

/**
 * 管理个人聊天界面的集合
 * @author Administrator
 *
 */
public class ManageClientPersonCollection {
	private static HashMap<String, ClientFrame> hm = new HashMap<String, ClientFrame>();
	
	/**
	 * 根据字符串找到对应的个人聊天界面
	 * @param str 规则：Sender Getter
	 * @param cf 个人聊天界面
	 */
	public static  void addClientPersonCollection(String str,ClientFrame cf){
		hm.put(str, cf);
	}
	
	/**
	 * 根据字符串返回对应的聊天界面
	 * @param str 规则：Sender Getter
	 * @return 对应的聊天界面
	 */
	public static ClientFrame getClientPerson(String str){
		return hm.get(str);
	}
	
	/**
	 * 根据字符串查看对应的聊天界面是否已经存在
	 * @param str
	 * @return
	 */
	public static boolean isExist(String str){
		boolean b = false;
		if(hm.get(str)!=null){
			b = true;
		}
		return b;
	}
	
	/**
	 * 根据字符串移除对应的聊天界面
	 * @param str
	 */
	public static void removeClientPerson(String str){
		hm.remove(str);
	}
	
	/**
	 * 返回其所有字符串的集合
	 * @return	集合
	 */
	public static Set<String> getClientPersonSet(){
		return hm.keySet();
	}
}
