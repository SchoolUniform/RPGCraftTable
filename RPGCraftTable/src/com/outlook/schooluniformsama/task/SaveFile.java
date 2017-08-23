package com.outlook.schooluniformsama.task;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;

public class SaveFile implements Runnable{

	@Override
	public void run() {
		saveWorkbench();
	}
	
	public static void saveWorkbench(){
		File data=new File(MainData.DATAFOLDER+File.separator+"workbench.yml");
		YamlConfiguration dataC=YamlConfiguration.loadConfiguration(data);
		LinkedList<String> l=new LinkedList<String>();
		for(Map.Entry<String, TableTimer> entry : MainData.WORKBENCH.entrySet()){
			l.add(entry.getKey());
			dataC.set(entry.getKey()+".playerName", entry.getValue().getPlayerName());
			dataC.set(entry.getKey()+".worldName", entry.getValue().getWorldName());
			dataC.set(entry.getKey()+".x", entry.getValue().getX());
			dataC.set(entry.getKey()+".y", entry.getValue().getY());
			dataC.set(entry.getKey()+".z", entry.getValue().getZ());
			dataC.set(entry.getKey()+".fileName",entry.getValue().getFileName());
			dataC.set(entry.getKey()+".recipeName", entry.getValue().getRecipeName());
			dataC.set(entry.getKey()+".type", entry.getValue().getType().name());
			dataC.set(entry.getKey()+".time", entry.getValue().getTime());
			
			switch(entry.getValue().getType()){
				case FURNACE:
					FurnaceTimer ft=(FurnaceTimer)entry.getValue();
					dataC.set(entry.getKey()+".extra", ft.getExtraTemperature());
					dataC.set(entry.getKey()+".isBad", ft.isBad());
					break;
				case WORKBENCH:
					break;
			}
			
		}
		
		dataC.set("list", l);
		try {dataC.save(data);} catch (IOException e) {}
	}

}
