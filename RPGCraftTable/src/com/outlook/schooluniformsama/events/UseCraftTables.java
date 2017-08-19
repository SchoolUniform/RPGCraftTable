package com.outlook.schooluniformsama.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.outlook.schooluniformsama.data.Blueprint;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.data.WorkbenchRecipe;
import com.outlook.schooluniformsama.gui.RecipeWatcherGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;
import com.outlook.schooluniformsama.utils.Util;

public class UseCraftTables extends PluginRPGCT implements Listener{
	
	@EventHandler
	public void openCraftTables(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player))return;
		if(e.getSlotType()==SlotType.OUTSIDE)return;
		Player p=(Player) e.getWhoClicked();
		//Workbench start
		if(e.getInventory().getTitle().equals(rpgct.getWorkbenchItems()[7])){
			//Player Use GUI
			
			if(e.getRawSlot()<54&&!WorkbenchGUI.materials.contains(e.getRawSlot())
					&&e.getRawSlot()!=16&&e.getRawSlot()!=49){
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(WorkbenchGUI.createWorkbenchGUI(e.getClickedInventory().getItem(0)));
				return;
			}
			if(e.getRawSlot()==49){
				e.setCancelled(true);
				//Blueprint start
				Object blueprintDatas[] = Blueprint.getBluesprintDatas(e.getInventory().getItem(16));
				if(blueprintDatas == null){
					e.setCancelled(true);
					p.sendMessage(rpgct.getMsg("NotFindBlueprint"));
					p.closeInventory();
					p.openInventory(WorkbenchGUI.createWorkbenchGUI(e.getClickedInventory().getItem(0)));
					return;
				}else if((boolean)blueprintDatas[5]){
					e.setCancelled(true);
					p.sendMessage(rpgct.getMsg("TheBlueprintIsWrong"));
					p.closeInventory();
					p.openInventory(WorkbenchGUI.createWorkbenchGUI(e.getClickedInventory().getItem(0)));
					return;
				}
				//Blueprint End
				WorkbenchRecipe wr=WorkbenchRecipe.load(Util.removeColour(blueprintDatas[0].toString()),blueprintDatas[1].toString());
				
				if(wr!=null && wr.containsShape(e.getInventory())){
					//Enter
					if((boolean)blueprintDatas[4]){
						givePlayerItem(p, e.getClickedInventory().getItem(16), e.getClickedInventory().getItem(16).getAmount()-1);
						givePlayerItem(p,Blueprint.subTimes(e.getClickedInventory().getItem(16), (int)(double)blueprintDatas[2]));
					}else{
						givePlayerItem(p, e.getClickedInventory().getItem(16));
					}
					ItemMeta key=e.getInventory().getItem(0).getItemMeta();
					String loc=key.getLore().get(0)+","+key.getLore().get(1)+","+key.getLore().get(2)+","+key.getLore().get(3);
					rpgct.addWorkbench(key.getDisplayName(), p.getName()+","+wr.getName()+"|+|"+blueprintDatas[1]+",0,"+loc);
					//after
					for(int i:WorkbenchGUI.materials)
						e.getInventory().setItem(i, null);
					e.getInventory().setItem(16, null);
					p.closeInventory();
				}else{
					p.sendMessage(rpgct.getMsg("TheBlueprintIsWrong"));
					return ;
				}
			}
			//OP Use GUI
		}else if(e.getInventory().getTitle().equals(rpgct.getWorkbenchItems()[7]+"*")){    //OP Use GUI
			if(e.getRawSlot()<54&&!WorkbenchGUI.materials.contains(e.getRawSlot())
					&&!WorkbenchGUI.products.contains(e.getRawSlot())&&e.getRawSlot()!=16){
				e.setCancelled(true);
				return;
			}
			if(e.getRawSlot()==16){
				boolean isNull=true,isNull2=true;
				for(int i:WorkbenchGUI.materials)
					if(e.getInventory().getItem(i)!=null)isNull=false;
				for(int i:WorkbenchGUI.products)
					if(e.getInventory().getItem(i)!=null)isNull2=false;
				if(isNull||isNull2){
					p.sendMessage("§3§lRPGCraftTable > §b喂,你材料或者生成物是空的你造嘛?");
					return;
				}
				if(WorkbenchRecipe.createWorkbenchRecipe(e.getInventory()))
					p.sendMessage("§3§lRPGCraftTable > §b配方创建成功了!");
				p.closeInventory();
				
			}
			
		}else if(e.getInventory().getTitle().equals(rpgct.getWorkbenchItems()[7]+"§3§l*")){
			//Player get Item GUI
			e.setCancelled(true);
			
			if((e.getRawSlot()<54&&e.getRawSlot()!=49)||(e.getRawSlot()==49&&!e.getInventory().getItem(e.getRawSlot()).
					getItemMeta().getDisplayName().equals("§9§l已完成 - 点击获取物品!"))){
				p.closeInventory();
				p.openInventory(WorkbenchGUI.openWorkbenchGUI(e.getClickedInventory().getItem(0)));
				return;
			}
			if(e.getRawSlot()==49){
				for(int i:WorkbenchGUI.products)
					if(e.getInventory().getItem(i)!=null)
						givePlayerItem(p, e.getInventory().getItem(i));
				rpgct.removeWorkbench(e.getInventory().getItem(0).getItemMeta().getDisplayName());
				p.closeInventory();
			}
			
			//Workbench End
			
			//Watcher Start
		}else if(e.getInventory().getTitle().contains("§3§l配方 - ")){
			e.setCancelled(true);
			if(e.getSlotType()==SlotType.OUTSIDE)return;
			if((e.getRawSlot()<54)){
				p.closeInventory();
				p.openInventory(RecipeWatcherGUI.openRecipeWatcher(e.getInventory().getTitle().replaceAll("§3§l配方 - ", "").split(":")[0],
						e.getInventory().getTitle().replaceAll("§3§l配方 - ", "").split(":")[1]));
				return;
			}
			//Watcher End
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	@EventHandler
	public void closeCraftTables(InventoryCloseEvent e){
		if(e.getInventory().getTitle().equals(rpgct.getWorkbenchItems()[7])){
			for(int i:WorkbenchGUI.materials)
				if(e.getInventory().getItem(i)!=null)
					givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(i));
			if(e.getInventory().getItem(16)!=null)
				givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(16));
		}
	}
	
	public static void givePlayerItem(Player p,ItemStack item,int amount){
		item.setAmount(amount);
		for(ItemStack is:p.getInventory().getContents())
			if(is==null){
				p.getInventory().addItem(item);
				return;
			}
		p.getWorld().dropItem(p.getLocation(), item);
	}
	
	public static void givePlayerItem(Player p,ItemStack item){
		for(ItemStack is:p.getInventory().getContents())//.getStorageContents())
			if(is==null){
				p.getInventory().addItem(item);
				return;
			}
		p.getWorld().dropItem(p.getLocation(), item);
	}
}
