package cn.zlpc.servlet.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器缓存数据
 * @author Hocean
 *
 */
public class OnlineUser {

	public static final Map<String, String> STORE = new HashMap<String, String>();

	public static final OnlineUser USER = OnlineUser.getInstance();

	private static OnlineUser getInstance() {
		// TODO Auto-generated method stub
		return new OnlineUser();
	}

}
