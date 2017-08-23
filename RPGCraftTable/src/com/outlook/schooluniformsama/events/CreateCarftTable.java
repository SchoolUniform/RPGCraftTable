package com.outlook.schooluniformsama.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.TempData;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;
import com.outlook.schooluniformsama.gui.FurnaceGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;
import com.outlook.schooluniformsama.utils.Msg;
import com.outlook.schooluniformsama.utils.Util;

public class CreateCarftTable implements Listener{
	@EventHandler
	public void OpenTable(PlayerInteractEvent e){
		Block sign=e.getClickedBlock();
		if(sign==null||sign.getType().name().equalsIgnoreCase("air")||
				e.getAction()!=Action.RIGHT_CLICK_BLOCK)return;	
		
		//Workbench start
		if(sign.getType().name().equalsIgnoreCase(MainData.WORKBENCHITEMS[0])&&
				compare(sign.getRelative(BlockFace.UP).getType(),MainData.WORKBENCHITEMS[3])&&
				compare(sign.getRelative(BlockFace.DOWN).getType(),MainData.WORKBENCHITEMS[4])){
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.WORKBENCHITEMS[1])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.WORKBENCHITEMS[2])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.WORKBENCHITEMS[6])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.WORKBENCHITEMS[5])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.WORKBENCHITEMS[6])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.WORKBENCHITEMS[5])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.WORKBENCHITEMS[2])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.WORKBENCHITEMS[1])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.WORKBENCHITEMS[2])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.WORKBENCHITEMS[1])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.WORKBENCHITEMS[5])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.WORKBENCHITEMS[6])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.WORKBENCHITEMS[5])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.WORKBENCHITEMS[6])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.WORKBENCHITEMS[1])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.WORKBENCHITEMS[2])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			
		}
		//Workbench End
		
		
		//Furnace Start
		if(sign.getType().name().equalsIgnoreCase(MainData.FURNACEITEMS[0])&&
				compare(sign.getRelative(BlockFace.UP).getType(),MainData.FURNACEITEMS[3])&&
				compare(sign.getRelative(BlockFace.DOWN).getType(),MainData.FURNACEITEMS[4])){
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.FURNACEITEMS[1])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.FURNACEITEMS[2])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.FURNACEITEMS[6])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.FURNACEITEMS[5])){
				e.setCancelled(true);
				openFurnace(e.getPlayer(), sign);
				return;
			}
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.FURNACEITEMS[6])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.FURNACEITEMS[5])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.FURNACEITEMS[2])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.FURNACEITEMS[1])){
				e.setCancelled(true);
				openFurnace(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.FURNACEITEMS[2])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.FURNACEITEMS[1])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.FURNACEITEMS[5])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.FURNACEITEMS[6])){
				e.setCancelled(true);
				openFurnace(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),MainData.FURNACEITEMS[5])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),MainData.FURNACEITEMS[6])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),MainData.FURNACEITEMS[1])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),MainData.FURNACEITEMS[2])){
				e.setCancelled(true);
				openFurnace(e.getPlayer(), sign);
				return;
			}
		}
		//Furnace End
		
	}
	private void openFurnace(Player p,Block b){
		if(MainData.WORKBENCH.containsKey(Util.getWorkbenchID(b))){
			if(MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName().equals(p.getName())){
				TempData.openingWorkbench.put(p.getName(), Util.getWorkbenchID(b));
				p.openInventory(FurnaceGUI.openFurnaceGUI((FurnaceTimer) MainData.WORKBENCH.get(Util.getWorkbenchID(b))));
			}else Msg.sendMsgToPlayer(p, "WorkbenchIsUsing", new String[]{"%player%"},
					new String[]{MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName()},true);
		}else{			
			TempData.createTimerTemp.put(p.getName(), new TableTimer(p.getName(), WorkbenchType.FURNACE, 
					b.getWorld().getName(), b.getX(), b.getY(), b.getZ()));
			p.openInventory(FurnaceGUI.createFurnaceGUI());
		}
	}
	
	private void openWorkbench(Player p,Block b){
		if(MainData.WORKBENCH.containsKey(Util.getWorkbenchID(b))){
			if(MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName().equals(p.getName())){
				TempData.openingWorkbench.put(p.getName(), Util.getWorkbenchID(b));
				p.openInventory(WorkbenchGUI.openWorkbenchGUI((WorkbenchTimer) MainData.WORKBENCH.get(Util.getWorkbenchID(b))));
			}else Msg.sendMsgToPlayer(p, "WorkbenchIsUsing", new String[]{"%player%"},
					new String[]{MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName()},true);
		}else{			
			TempData.createTimerTemp.put(p.getName(), new TableTimer(p.getName(), WorkbenchType.WORKBENCH, 
					b.getWorld().getName(), b.getX(), b.getY(), b.getZ()));
			p.openInventory(WorkbenchGUI.createWorkbenchGUI());
		}
	}
	
	private boolean compare(Material type, String name){
		if(name==null||name.equalsIgnoreCase("null"))return true;
		return type.name().equalsIgnoreCase(name);
	}
	
}
