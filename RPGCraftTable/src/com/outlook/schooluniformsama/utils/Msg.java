package com.outlook.schooluniformsama.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.outlook.schooluniformsama.data.MainData;

public class Msg {
	private static YamlConfiguration i18nMsg;
	
	/**
	 * You must run this method when the plugin is loaded.
	 */
	public static void init(){
		i18nMsg = YamlConfiguration.loadConfiguration(Msg.class.getResourceAsStream("/lang/"+MainData.LANG+".yml"));
	}
	
	/**
	 * Get a message from messages.yml. you can let some variable change to what string you want.
	 * The variable and value are string array. You must let the variable and value at the same index, 
	 * for example: a message's id is "hello" and the message is "Hello %player%! now is %time%" -> 
	 * getDIYMsg(hello,new String[]{"%player%","%time%"},new String[]{"School_Uniform","14:36"},false);
	 * so you will get a string that is "Hello School_Uniform! now is 14:36"
	 * @param id
	 * @param variable
	 * @param value
	 * @param addPrefix
	 * @return
	 */
	/*
	public static String getDIYMsg(String id,String[] variable ,String[] value,boolean addPrefix){
		String msg = diyMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg.replace(variable[i], value[i]);
		if(addPrefix)
			return diyMsg.getString("Prefix")+msg;
		return msg;
	}
	
	public static String getDIYMsg(String id,boolean addPrefix){
		if(addPrefix)
			return diyMsg.getString("Prefix")+diyMsg.getString(id);
		return diyMsg.getString(id);
	}
	
	public static void sendDIYMsgToPlayer(Player p, String id,String[] variable , String[] value,boolean addPrefix){
		if(!p.isOnline())return;
		String msg = diyMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg.replace(variable[i], value[i]);
		if(addPrefix)
			p.sendMessage(diyMsg.getString("Prefix")+msg);
		else
			p.sendMessage(msg);
	}
	
	public static void sendDIYMsgToPlayer(Player p, String id,boolean addPrefix){
		if(!p.isOnline())return;
		if(addPrefix)
			p.sendMessage(diyMsg.getString("Prefix")+diyMsg.getString(id));
		else
			p.sendMessage(diyMsg.getString(id));
	}
	
	public static void sendDIYMsgToPlayer(String playerName, String id,String[] variable , String[] value,boolean addPrefix){
		Player p = Bukkit.getPlayer(playerName);
		if(!p.isOnline())return;
		String msg = diyMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg.replace(variable[i], value[i]);
		if(addPrefix)
			p.sendMessage(diyMsg.getString("Prefix")+msg);
		else
			p.sendMessage(msg);
	}
	
	public static void sendDIYMsgToPlayer(String playerName, String id,boolean addPrefix){
		Player p = Bukkit.getPlayer(playerName);
		if(!p.isOnline())return;
		if(addPrefix)
			p.sendMessage(diyMsg.getString("Prefix")+diyMsg.getString(id));
		else
			p.sendMessage(diyMsg.getString(id));
	}
	
	*/
	public static String getMsg(String id,String[] variable ,String[] value,boolean addPrefix){
		String msg = i18nMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg=msg.replaceAll(variable[i], value[i]);
		if(addPrefix)
			return MainData.PREFIX+msg;
		return msg;
	}
	
	public static String getMsg(String id,boolean addPrefix){
		if(addPrefix)
			return MainData.PREFIX+i18nMsg.getString(id);
		return i18nMsg.getString(id);
	}
	
	public static void sendMsgToPlayer(Player p, String id,String[] variable , String[] value,boolean addPrefix){
		if(!p.isOnline())return;
		String msg = i18nMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg=msg.replaceAll(variable[i], value[i]);
		if(addPrefix)
			p.sendMessage(MainData.PREFIX+msg);
		else
			p.sendMessage(msg);
	}
	
	public static void sendMsgToPlayer(Player p, String id,boolean addPrefix){
		if(!p.isOnline())return;
		if(addPrefix)
			p.sendMessage(MainData.PREFIX+i18nMsg.getString(id));
		else
			p.sendMessage(i18nMsg.getString(id));
	}
	
	public static void sendMsgToPlayer(String playerName, String id,String[] variable , String[] value,boolean addPrefix){
		Player p = Bukkit.getPlayer(playerName);
		if(!p.isOnline())return;
		String msg = i18nMsg.getString(id);
		for(int i=0;i<variable.length;i++)
			msg=msg.replaceAll(variable[i], value[i]);
		if(addPrefix)
			p.sendMessage(MainData.PREFIX+msg);
		else
			p.sendMessage(msg);
	}
	
	public static void sendMsgToPlayer(String playerName, String id,boolean addPrefix){
		Player p = Bukkit.getPlayer(playerName);
		if(!p.isOnline())return;
		if(addPrefix)
			p.sendMessage(MainData.PREFIX+i18nMsg.getString(id));
		else
			p.sendMessage(i18nMsg.getString(id));
	}
	
}
