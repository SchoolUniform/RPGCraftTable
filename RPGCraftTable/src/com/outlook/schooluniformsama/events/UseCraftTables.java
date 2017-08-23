package com.outlook.schooluniformsama.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.Blueprint;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.TempData;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;
import com.outlook.schooluniformsama.gui.FurnaceGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;
import com.outlook.schooluniformsama.utils.Msg;
import com.outlook.schooluniformsama.utils.Util;

public class UseCraftTables implements Listener{
	
	@EventHandler
	public void openCraftTables(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player))return;
		if(e.getSlotType()==SlotType.OUTSIDE)return;
		Player p=(Player) e.getWhoClicked();
		
		if(e.getInventory().getTitle().equals(MainData.WORKBENCHITEMS[7])){
			//Player opening the workbench
			if(e.getRawSlot()<54&&!WorkbenchGUI.materials.contains(e.getRawSlot())&&e.getRawSlot()!=16&&e.getRawSlot()!=49){
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(WorkbenchGUI.createWorkbenchGUI());
				return;
			}
			if(e.getRawSlot()==49){
				e.setCancelled(true);
				ItemStack blueprint=e.getInventory().getItem(16);
				
				Object blueprintDatas[] = Blueprint.getBluesprintDatas(blueprint);
				if(blueprintDatas == null){
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"NotFindBlueprint",true);
					p.closeInventory();
					p.openInventory(WorkbenchGUI.createWorkbenchGUI());
					return;
				}else if((WorkbenchType)blueprintDatas[2]!=WorkbenchType.WORKBENCH){
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"TheBlueprintIsWrong",true);
					p.closeInventory();
					p.openInventory(WorkbenchGUI.createWorkbenchGUI());
					return;
				}
				
				WorkbenchRecipe wr = WorkbenchRecipe.load(blueprintDatas[0].toString(), blueprintDatas[1].toString());
				
				if(wr!=null && wr.containsShape(e.getInventory())){
					try{
						WorkbenchTimer wt = TempData.createTimerTemp.get(p.getName()).toWorkbenchTimer();
						wt.start(wr);
						MainData.WORKBENCH.put(Util.getWorkbenchID(wt), wt);
						TempData.openingWorkbench.remove(p.getName());
					}catch(Exception exp){
						Msg.sendMsgToPlayer(p, "Exception", true);
						return;
					}
					//Enter
					if((boolean)blueprintDatas[4]){
						givePlayerItem(p, blueprint.clone(), blueprint.getAmount()-1);
						givePlayerItem(p,Blueprint.subTimes(blueprint.clone(), (int)(double)blueprintDatas[3]),1);
					}else{
						givePlayerItem(p, blueprint);
					}
					//after
					for(int i:WorkbenchGUI.materials)
						e.getInventory().setItem(i, null);
					e.getInventory().setItem(16, null);
					p.closeInventory();
				}else{
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"TheBlueprintIsWrong",true);
					return;
				}
			}
		}else if(e.getInventory().getTitle().equals(MainData.WORKBENCHITEMS[7]+"*")){
			if(e.getRawSlot()<54&&!WorkbenchGUI.materials.contains(e.getRawSlot())&&!WorkbenchGUI.products.contains(e.getRawSlot())&&e.getRawSlot()!=16){
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
					Msg.sendMsgToPlayer(p, "CreateRecipeFailed", true);
					return;
				}
				if(WorkbenchRecipe.createRecipe(e.getInventory(),(WorkbenchRecipe) TempData.createRecipeTemp.get(p.getName()))){
					Msg.sendMsgToPlayer(p, "CreateRecipeSucceed", true);
					p.closeInventory();
				}else{
					Msg.sendMsgToPlayer(p, "CreateRecipeFailed", true);
					return;
				}
			}
		}else if(e.getInventory().getTitle().equals(MainData.WORKBENCHITEMS[7]+"§3§l*")){
			e.setCancelled(true);
			WorkbenchTimer wt=(WorkbenchTimer) MainData.WORKBENCH.get(TempData.openingWorkbench.get(p.getName()));
			if(e.getRawSlot()==49){
				if(wt.isOver()){
					MainData.WORKBENCH.remove(Util.getWorkbenchID(wt));
					for(int i:WorkbenchGUI.products)
						if(e.getInventory().getItem(i)!=null)
							givePlayerItem(p, e.getInventory().getItem(i));
					p.closeInventory();
					return;
				}else{
					Msg.sendMsgToPlayer(p, "WorkbenchUndone", true);
					return;
				}
			}else{
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(WorkbenchGUI.checkPass(e.getInventory(), wt));
				return;
			}
		}else if(e.getInventory().getTitle().equals(Msg.getMsg("Watcher", false))){
			e.setCancelled(true);
			p.closeInventory();
			p.openInventory(e.getInventory());
			return;
		}else if(e.getInventory().getTitle().equals(MainData.FURNACEITEMS[7])){
			if(e.getRawSlot()<54&&!FurnaceGUI.mSlot.contains(e.getRawSlot())&&e.getRawSlot()!=4&&e.getRawSlot()!=49){
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(FurnaceGUI.createFurnaceGUI());
				return;
			}
			
			if(e.getRawSlot()==49){
				e.setCancelled(true);
				ItemStack blueprint=e.getInventory().getItem(4);
				
				Object blueprintDatas[] = Blueprint.getBluesprintDatas(blueprint);
				if(blueprintDatas == null){
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"NotFindBlueprint",true);
					p.closeInventory();
					p.openInventory(FurnaceGUI.createFurnaceGUI());
					return;
				}else if((WorkbenchType)blueprintDatas[2]!=WorkbenchType.FURNACE){
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"TheBlueprintIsWrong",true);
					p.closeInventory();
					p.openInventory(FurnaceGUI.createFurnaceGUI());
					return;
				}
				
				FurnaceRecipe fr = FurnaceRecipe.load(blueprintDatas[0].toString(), blueprintDatas[1].toString());
				
				if(fr!=null && fr.containsShape(e.getInventory())){
					try{
						FurnaceTimer ft = TempData.createTimerTemp.get(p.getName()).toFurnaceTimer();
						ft.start(fr,FurnaceRecipe.getBlocks(ft.getLocation()));
						MainData.WORKBENCH.put(Util.getWorkbenchID(ft), ft);
						TempData.openingWorkbench.remove(p.getName());
					}catch(Exception exp){
						Msg.sendMsgToPlayer(p, "Exception", true);
						return;
					}
					//Enter
					if((boolean)blueprintDatas[4]){
						givePlayerItem(p, blueprint.clone(), blueprint.getAmount()-1);
						givePlayerItem(p,Blueprint.subTimes(blueprint.clone(), (int)(double)blueprintDatas[3]),1);
					}else{
						givePlayerItem(p, blueprint);
					}
					//after
					for(int i:FurnaceGUI.mSlot)
						e.getInventory().setItem(i, null);
					e.getInventory().setItem(4, null);
					p.closeInventory();
				}else{
					e.setCancelled(true);
					Msg.sendMsgToPlayer(p,"TheBlueprintIsWrong",true);
					return;
				}
			}
			
			
		}else if(e.getInventory().getTitle().equals(MainData.FURNACEITEMS[7]+"*")){
			if(e.getRawSlot()<54&&!FurnaceGUI.mSlot.contains(e.getRawSlot())&&!FurnaceGUI.pSlot.contains(e.getRawSlot())&&e.getRawSlot()!=49){
				e.setCancelled(true);
				return;
			}
			if(e.getRawSlot()==49){
				boolean isNull=true,isNull2=true;
				for(int i:FurnaceGUI.mSlot)
					if(e.getInventory().getItem(i)!=null)isNull=false;
				for(int i:FurnaceGUI.pSlot)
					if(e.getInventory().getItem(i)!=null)isNull2=false;
				if(isNull||isNull2){
					Msg.sendMsgToPlayer(p, "CreateRecipeFailed", true);
					return;
				}
				if(FurnaceRecipe.createFurnaceRecipe(e.getInventory(),(FurnaceRecipe) TempData.createRecipeTemp.get(p.getName()))){
					Msg.sendMsgToPlayer(p, "CreateRecipeSucceed", true);
					p.closeInventory();
				}else{
					Msg.sendMsgToPlayer(p, "CreateRecipeFailed", true);
					return;
				}
			}
		}else if(e.getInventory().getTitle().equals(MainData.FURNACEITEMS[7]+"§3§l*")){
			e.setCancelled(true);
			FurnaceTimer ft=(FurnaceTimer) MainData.WORKBENCH.get(TempData.openingWorkbench.get(p.getName()));
			if(e.getRawSlot()==49){
				if(ft.isBad()){
					MainData.WORKBENCH.remove(Util.getWorkbenchID(ft));
					Msg.sendMsgToPlayer(p, "FurnaceFailedMsg", true);
					p.closeInventory();
					return;
				}
				if(ft.isOver()){
					MainData.WORKBENCH.remove(Util.getWorkbenchID(ft));
					for(int i:FurnaceGUI.mSlot)
						if(e.getInventory().getItem(i)!=null)
							givePlayerItem(p, e.getInventory().getItem(i));
					p.closeInventory();
					return;
				}else{
					Msg.sendMsgToPlayer(p, "WorkbenchUndone", true);
					return;
				}
			}else{
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(FurnaceGUI.checkHeatSource(e.getInventory(), ft));
				return;
			}
		}
		
	}
	
	@EventHandler
	public void closeCraftTables(InventoryCloseEvent e){
		if(e.getInventory().getTitle().equals(MainData.WORKBENCHITEMS[7])){
			for(int i:WorkbenchGUI.materials)
				if(e.getInventory().getItem(i)!=null)
					givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(i));
			if(e.getInventory().getItem(16)!=null)
				givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(16));
		}else if(e.getInventory().getTitle().equals(MainData.FURNACEITEMS[7])){
			for(int i:FurnaceGUI.mSlot)
				if(e.getInventory().getItem(i)!=null)
					givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(i));
			if(e.getInventory().getItem(4)!=null)
				givePlayerItem((Player)e.getPlayer(),e.getInventory().getItem(4));
		}
		
		
		/*TempData.openingWorkbench.remove(e.getPlayer().getName());
		TempData.createRecipeTemp.remove(e.getPlayer().getName());
		TempData.createTimerTemp.remove(e.getPlayer().getName());*/
	}
	
	public static void givePlayerItem(Player p,ItemStack item,int amount){
		item.setAmount(amount);
		int index=0;
		for(ItemStack is:p.getInventory().getContents())
			if(is==null && index++<p.getInventory().getContents().length-5){
				p.getInventory().addItem(item);
				return;
			}
		p.getWorld().dropItem(p.getLocation(), item);
	}
	
	public static void givePlayerItem(Player p,ItemStack item){
		int index=0;
		for(ItemStack is:p.getInventory().getContents())//.getStorageContents())
			if(is==null && index++<p.getInventory().getContents().length-5){
				p.getInventory().addItem(item);
				return;
			}
		p.getWorld().dropItem(p.getLocation(), item);
	}
}
