package com.outlook.schooluniformsama.data;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static ItemStack createItemKey(Material type,short damage,String world,int x,int y,int z){
		ItemStack is=new ItemStack(type,1,damage);
		ItemMeta im=is.getItemMeta();
		//set Key
		im.setDisplayName(world+Long.toHexString(Long.parseLong((x+""+y+""+z+"").replace("-", "010"))));
		im.setLore(Arrays.asList(world,x+"",y+"",z+""));
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack createItemKey(Material type,short damage,String key){
		ItemStack is=new ItemStack(type,1,damage);
		ItemMeta im=is.getItemMeta();
		//set Key
		im.setDisplayName(key);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack createPItem(short damage,String name){
		ItemStack is=new ItemStack(Material.STAINED_GLASS_PANE,1,damage);
		ItemMeta im=is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
	
	public static  ItemStack getPlaceholder10(short data,int pass){
		ItemStack sw=new ItemStack(Material.STAINED_GLASS_PANE,1,data);
		ItemMeta im = sw.getItemMeta();
		if(pass>=100)
			im.setDisplayName("§9§l已完成 - 点击获取物品!");
		else
			im.setDisplayName("§9§l已完成 "+pass+"%");
		sw.setItemMeta(im);
		return sw;
	}
}
