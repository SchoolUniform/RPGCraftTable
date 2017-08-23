package com.outlook.schooluniformsama.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;
import com.outlook.schooluniformsama.utils.Msg;

public class WorkbenchGUI{
	public static final  List<Integer> materials = Arrays.asList(10,11,12,13,19,20,21,22,28,29,30,31,37,38,39,40);
	public static final  List<Integer> products = Arrays.asList(34,33,43,42);
	
	private static Inventory createDefaultGUI(String title){
		Inventory inv=Bukkit.createInventory(null, 54,title);
		for(int i=0;i<54;i++)
			if(!(materials.contains(i)||products.contains(i)))
				inv.setItem(i, Items.createPItem((short)15, " "));
		inv.setItem(16, null);
		return inv;
	}
	
	public static Inventory createWorkbenchGUI(){
		Inventory inv=createDefaultGUI(MainData.WORKBENCHITEMS[7]);
		inv.setItem(49, Items.createPItem((short) 14, Msg.getMsg("WorkbenchStart", false)));
		return inv;
	}
	
	public static Inventory createWorkbenchRecipeGUI(){
		Inventory inv=createDefaultGUI(MainData.WORKBENCHITEMS[7]+"*");
		inv.setItem(16, Items.createPItem((short) 14, Msg.getMsg("SaveRecipe", false)));
		return inv;
	}

	public static Inventory openWorkbenchGUI(WorkbenchTimer wt){
		Inventory inv=createDefaultGUI(MainData.WORKBENCHITEMS[7]+"§3§l*");
		WorkbenchRecipe wr=WorkbenchRecipe.load(wt.getRecipeName(),wt.getFileName());
		return checkPass(wr.setInv(inv),wt);
	}
	
	public static  Inventory checkPass(Inventory inv,WorkbenchTimer wt){
		if(wt.isOver())
			inv.setItem(49, Items.createPItem((short)14, Msg.getMsg("WorkbenchProgress2", false)));
		else
			inv.setItem(49, Items.createPItem((short)5, Msg.getMsg("WorkbenchProgress1", new String[]{"%pass%"}, 
					new String[]{((wt.getTime()/(double)wt.getNeedTime())*100)+""}, false)));
		
		return inv;
	}

}
