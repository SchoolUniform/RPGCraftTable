package com.outlook.schooluniformsama.task;

import org.bukkit.block.Block;

import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.gui.FurnaceGUI;

public class CraftTablesTimer extends PluginRPGCT implements Runnable{

	@Override
	public void run() {
		//workbench
		for(String key:rpgct.getWorkbench().keySet()){
			Object[] obj=rpgct.getWorkbench(key);
			Block b=rpgct.getServer().getWorld(obj[3].toString()).getBlockAt((int)obj[4], (int)obj[5], (int)obj[6]);
			if(b==null || !b.getType().name().equals(rpgct.getWorkbenchItems()[0]))
				rpgct.removeWorkbench(key);
			else
				rpgct.resetWorkbench(key,obj[0]+","+obj[1]+","+((int)obj[2]+1)+","+obj[3]+","+obj[4]+","+obj[5]+","+obj[6]);
		}
		
		//Fire Craft Table
		for(String key:rpgct.getFCT().keySet()){
			String[] obj=rpgct.getFCT(key);
			Block b=rpgct.getServer().getWorld(obj[6]).getBlockAt(Integer.parseInt(obj[7]), 
					Integer.parseInt(obj[8]), Integer.parseInt(obj[9]));
			if(b==null || !b.getType().name().equals(rpgct.getFurnaceItems()[0])){
				rpgct.removeFCT(key);	
				continue;
			}else {
				//ÍâÖÃÎÂ¶È
				if(Double.parseDouble(obj[3])>0)
					obj[3]=(Double.parseDouble(obj[3])-1)+"";
				else if(Double.parseDouble(obj[3])<0)
					obj[3]=(Double.parseDouble(obj[3])+1)+"";
				
				if(Double.parseDouble(obj[4])>=0&&(FurnaceGUI.checkHeatSource(obj)+Double.parseDouble(obj[3])>=Double.parseDouble(obj[4])))
					rpgct.resetFCT(key,obj[0]+","+obj[1]+","+(Integer.parseInt(obj[2])+1)+","+obj[3]+","+obj[4]+","+obj[5]+","+obj[6]+","+obj[7]+","+obj[8]);
				else if(Double.parseDouble(obj[4])<0&&(FurnaceGUI.checkHeatSource(obj)+Double.parseDouble(obj[3])<=Double.parseDouble(obj[4])))
					rpgct.resetFCT(key,obj[0]+","+obj[1]+","+(Integer.parseInt(obj[2])+1)+","+obj[3]+","+obj[4]+","+obj[5]+","+obj[6]+","+obj[7]+","+obj[8]);
				else
					rpgct.resetFCT(key,obj[0]+","+obj[1]+","+Integer.parseInt(obj[2])+","+obj[3]+","+obj[4]+","+obj[5]+","+obj[6]+","+obj[7]+","+obj[8]);
			}
		}
		
	}

	
}
