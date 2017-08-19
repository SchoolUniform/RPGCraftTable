package com.outlook.schooluniformsama.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;

public class WorkbenchGUI extends PluginRPGCT{
	public static final  List<Integer> materials = Arrays.asList(10,11,12,13,19,20,21,22,28,29,30,31,37,38,39,40);
	public static final  List<Integer> products = Arrays.asList(34,33,43,42);
	
	private static Inventory createDefaultGUI(String title){
		Inventory inv=Bukkit.createInventory(null, 54,title);
		for(int i=0;i<54;i++)
			if(!(materials.contains(i)||products.contains(i)))
				inv.setItem(i, Items.createPItem((short)15, ""));
		inv.setItem(16, null);
		return inv;
	}
	
	public static Inventory createWorkbenchGUI(){
		Inventory inv=createDefaultGUI(rpgct.getWorkbenchItems()[7]);
		inv.setItem(49, Items.createPItem((short) 14, "§b§l==>§c§l开始制造§b§l<=="));
		return inv;
	}
	
	public static Inventory createWorkbenchRecipeGUI(ItemStack itemKey){
		Inventory inv=createDefaultGUI(rpgct.getWorkbenchItems()[7]+"*");
		inv.setItem(0, itemKey);
		inv.setItem(16, Items.createPItem((short) 14, "§b§l==>§c§l保存§b§l<=="));
		return inv;
	}

	public static Inventory openWorkbenchGUI(WorkbenchTimer wt){
		Inventory inv=createDefaultGUI(rpgct.getWorkbenchItems()[7]+"§3§l*");
		WorkbenchRecipe wr=WorkbenchRecipe.load(wt.getRecipeName(),wt.getFileName());
		return checkPass(wr.setInv(inv),wt);
	}
	
	public static  Inventory checkPass(Inventory inv,WorkbenchTimer wt){
		if(wt.isOver())
			inv.setItem(49, Items.getPlaceholder10((short)14, 100));
		else
			inv.setItem(49, Items.getPlaceholder10((short)5,  (int) (wt.getTime()/wt.getNeedTime())));
		return inv;
	}

}
