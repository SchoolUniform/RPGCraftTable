package com.outlook.schooluniformsama.data;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.utils.Util;

public class Blueprint {
	
	public static Object[] getBluesprintDatas(ItemStack item){
		// RecipeName(String) FileName(String) Type(WorkbenchType) times(int) 
		if(item==null || !item.hasItemMeta() || !item.getItemMeta().hasLore())
			return null;
		List<String> lore = item.getItemMeta().getLore();
		String name = getLoreString("RecipeName", lore);
		if(name==null)return null;
		String fileName = getLoreString("FileName", lore);
		if(fileName==null)return null;
		String type = getLoreString("Type",lore);
		//MainData.BLUEPRINTLABEL.get("WorkbenchType").equalsIgnoreCase(type) || MainData.BLUEPRINTLABEL.get("FurnaceType").equalsIgnoreCase(type))
		if(type==null || !(MainData.BLUEPRINTLABEL.get("WorkbenchType").equalsIgnoreCase(type) || MainData.BLUEPRINTLABEL.get("FurnaceType").equalsIgnoreCase(type)))
			return null;
		double times = getLore("Times", lore);
		WorkbenchType wt=null;
		if(type.equals(MainData.BLUEPRINTLABEL.get("WorkbenchType"))){
			wt=WorkbenchType.WORKBENCH;
		}else if(type.equals(MainData.BLUEPRINTLABEL.get("FurnaceType"))){
			wt=WorkbenchType.FURNACE;
		}
		return new Object[]{name,fileName,wt,times,times==-1.1111111?false:true};
	}
	
	public static ItemStack subTimes(ItemStack item,int times){
		item.setAmount(1);
		ItemMeta im=item.getItemMeta();
		List<String> lore = im.getLore();
		for(String line:lore){
			if(line.contains(MainData.BLUEPRINTLABEL.get("Times"))){
				int temp=line.lastIndexOf(times+"");
				lore.set(lore.indexOf(line), line.substring(0, temp)+(--times));
				break;
			}
		}
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private static String getLoreString(String tabel,List<String> lore){
		tabel=MainData.BLUEPRINTLABEL.get(tabel);
		for(String line:lore){
			line=Util.removeColour(line);
			if(line.contains(tabel))
				return line.split(":")[1].replace(" ", "");
		}
		return null;
	}
	
	private static double getLore(String tabel,List<String> lore){
		tabel=MainData.BLUEPRINTLABEL.get(tabel);
		for(String line:lore){
			line=Util.removeColour(line);
			if(line.contains(tabel))
				return Double.parseDouble(line.split(":")[1].replaceAll(" ", ""));
		}
		return -1.1111111;
	}
}
