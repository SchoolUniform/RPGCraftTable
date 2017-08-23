package com.outlook.schooluniformsama.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.utils.Msg;

public class RecipeWatcherGUI{
	
	private static Inventory workbenchWatcher(WorkbenchRecipe wr){
		Inventory inv=Bukkit.createInventory(null, 54,Msg.getMsg("Watcher", false));
		for(int i=0;i<54;i++)
			if(!(WorkbenchGUI.materials.contains(i)||WorkbenchGUI.products.contains(i)))
				inv.setItem(i, Items.createPItem((short)15, ""));
		return wr.setInv(inv);
	}
	
	private static Inventory furnaceWatcher(FurnaceRecipe fr){
		Inventory inv=Bukkit.createInventory(null, 54,Msg.getMsg("Watcher", false));
		for(int i=0;i<54;i++)
			if(!FurnaceGUI.mSlot.contains(i)&&!FurnaceGUI.pSlot.contains(i)&&!FurnaceGUI.tSlot.contains(i)&&!FurnaceGUI.passSlot.contains(i))
				inv.setItem(i, Items.createPItem((short)15, ""));
		for(int i:FurnaceGUI.passSlot)
			inv.setItem(i, Items.createPItem((short)5, Msg.getMsg("FurnaceWatcher3",new String[]{"%time%"},new String[]{fr.getNeedTime()+""}, false)));
		if(fr.getMinTemperature()>=0){
			for(int i:FurnaceGUI.tSlot)
				inv.setItem(i, Items.createPItem((short)1, Msg.getMsg("FurnaceWatcher1",new String[]{"%minTemperature%"},new String[]{fr.getMinTemperature()+""}, false)));
		}else{
			for(int i:FurnaceGUI.tSlot)
				inv.setItem(i, Items.createPItem((short)3, Msg.getMsg("FurnaceWatcher2",new String[]{"%minTemperature%"},new String[]{fr.getMinTemperature()+""}, false)));
		}
		
		return fr.setInv(inv);
	}
	
	public static Inventory openRecipeWatcher(WorkbenchType wt,String fileName,String name){
		switch(wt){
			case WORKBENCH:
				return workbenchWatcher(WorkbenchRecipe.load(name, fileName));
			case FURNACE:
				return furnaceWatcher(FurnaceRecipe.load(name, fileName));
		}
		return null;
	}

}
