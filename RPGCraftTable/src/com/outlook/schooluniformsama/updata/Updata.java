package com.outlook.schooluniformsama.updata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.configuration.file.YamlConfiguration;

public class Updata {
	// https://raw.githubusercontent.com/SchoolUniform/RPGCraftTable/master/RPGCraftTable/plugin.yml
	public static long version;
	
	public static void checkVersion(){
		version =Long.parseLong( YamlConfiguration.loadConfiguration(Updata.class.getResourceAsStream("plugin.yml")).getString("version").replaceAll(".", "").replaceAll(" ", ""));
		long newVersion =0 ;
		try {
			YamlConfiguration plugin = YamlConfiguration.loadConfiguration(new URL("https://raw.githubusercontent.com/SchoolUniform/RPGCraftTable/master/RPGCraftTable/plugin.yml").openStream());
			newVersion=Long.parseLong( plugin.getString("version").replaceAll(".", "").replaceAll(" ", ""));
		} catch (MalformedURLException e) {
			
		} catch (IOException e) {
			
		}
		if(newVersion>version);
	}
	
}
