package com.outlook.schooluniformsama;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.WorkbenchRecipe;
import com.outlook.schooluniformsama.gui.RecipeWatcherGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;

public class Commands {
	public static boolean cmd(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			Player p=(Player)sender;
			if(label.equalsIgnoreCase("rpgct")){
				if(args.length==4){
					if(args[0].equalsIgnoreCase("cwp")&&p.hasPermission("RPGCraftTable.admin")){
						p.openInventory(WorkbenchGUI.createWorkbenchRecipeGUI(
								Items.createItemKey(Material.STAINED_GLASS_PANE, (short)15, args[1]+";"+args[2]+";"+args[3])));
						return true;
					}
				}else if(args.length==2){
					/*
					if(args[0].equalsIgnoreCase("getBlueprint")&&p.hasPermission("RPGCraftTable.admin"))
						p.getWorld().dropItem(p.getLocation(), WorkbenchRecipe.load(args[1]).getBlueprint());
					else if(args[0].equalsIgnoreCase("wr")&&p.hasPermission("RPGCraftTable.admin")){
						Inventory inv=RecipeWatcherGUI.openRecipeWatcher(args[1]);
						if(inv==null)
							p.sendMessage("§3§lRPGCraftTable > §b喂,你说的配方我没找到唉!");
						else
							p.openInventory(inv);
					}
					return true;
					 */
				}
			}
		}
		return false;
	}
}
