package com.outlook.schooluniformsama.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.data.WorkbenchRecipe;

public class RecipeWatcherGUI extends PluginRPGCT{
	
	private static Inventory recipeWatcher(WorkbenchRecipe wr,String fileName){
		Inventory inv=Bukkit.createInventory(null, 54,"§3§l配方 - "+wr.getName()+":"+fileName);
		for(int i=0;i<54;i++)
			if(!(WorkbenchGUI.materials.contains(i)||WorkbenchGUI.products.contains(i)))
				inv.setItem(i, Items.createPItem((short)15, "§7§l讨厌啦~不要总是看着人家啦~"));
		return wr.setInv(inv);
	}
	
	public static Inventory openRecipeWatcher(String name,String fileName){
		WorkbenchRecipe wr=WorkbenchRecipe.load(name,fileName);
		if(wr==null)return null;
		return recipeWatcher(wr,fileName);
	}

}
