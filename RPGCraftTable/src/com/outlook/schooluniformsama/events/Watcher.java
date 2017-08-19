package com.outlook.schooluniformsama.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.outlook.schooluniformsama.data.Blueprint;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.gui.RecipeWatcherGUI;

public class Watcher extends PluginRPGCT implements Listener{
	
	@EventHandler
	public void watcher(PlayerInteractEvent e){
		if(!(e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK))
			return;
		Object blueprintDatas[] = Blueprint.getBluesprintDatas(e.getItem());
		if(blueprintDatas == null)return;
		Inventory inv=RecipeWatcherGUI.openRecipeWatcher(blueprintDatas[0].toString(),blueprintDatas[1].toString());
		if(inv==null)return;
		e.getPlayer().openInventory(inv);
	}

}
