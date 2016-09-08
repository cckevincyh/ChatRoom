package chat.common;

import java.io.Serializable;

/**
 * 用户类
 * @author c
 *
 */
public class User implements Serializable{
	private String Name;
	private String PassWords;
	private String Type;	//注册或是登陆

	public User(){
		
	}
	
	public User(String Name,String PassWords){
		this.Name = Name;
		this.PassWords = PassWords;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassWords() {
		return PassWords;
	}

	public void setPassWords(String passWords) {
		PassWords = passWords;
	}
	
	
}
