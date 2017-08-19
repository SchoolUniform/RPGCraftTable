package com.outlook.schooluniformsama.data;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.outlook.schooluniformsama.utils.Util;

public class Blueprint extends PluginRPGCT {
	
	public static Object[] getBluesprintDatas(ItemStack item){
		// name type times temperature hasTimes isFurnace
		if(!isBluesprint(item))return null;
		List<String> lore = item.getItemMeta().getLore();
		String name = getLoreString("name", lore);
		String type = getLoreString("type", lore);
		double times = getLore("times", lore);
		double temperature = getLore("temperature", lore);
		return new Object[]{name,type,times,temperature,times == -1.1111111?false:true ,temperature == -1.1111111?false:true};
	}
	
	public static ItemStack subTimes(ItemStack item,int times){
		ItemMeta im=item.getItemMeta();
		List<String> lore = im.getLore();
		for(String line:lore){
			if(line.contains(rpgct.getBlueprintTable("times"))){
				System.out.println();
				int temp=line.lastIndexOf(times+"");
				lore.set(lore.indexOf(line), line.substring(0, temp)+--times);
				break;
			}
		}
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private static boolean isBluesprint(ItemStack item){
		if(item==null || !item.hasItemMeta() || !item.getItemMeta().hasLore())return false;
		List<String> lore = item.getItemMeta().getLore();
		if(getLoreString("name", lore) == null)return false;
		if(getLoreString("type", lore) == null)return false;
		return true;
		/*
			blueprintTable:
			  - name:蓝图名
			  - times:使用次数
			  - temperature:温度
			  - type:种类
		 */
	}
	
	private static String getLoreString(String tabel,List<String> lore){
		tabel=rpgct.getBlueprintTable(tabel);
		for(String line:lore){
			line=Util.removeColour(line);
			if(line.contains(tabel))
				return line.split(":")[1].replace(" ", "");
		}
		return null;
	}
	
	private static double getLore(String tabel,List<String> lore){
		tabel=rpgct.getBlueprintTable(tabel);
		for(String line:lore){
			line=Util.removeColour(line);
			if(line.contains(tabel))
				return Double.parseDouble(line.split(":")[1].replaceAll(" ", ""));
		}
		return -1.1111111;
	}
}
