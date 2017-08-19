package com.outlook.schooluniformsama.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.data.TempData;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;
import com.outlook.schooluniformsama.gui.FurnaceGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;
import com.outlook.schooluniformsama.utils.Util;

public class CreateCarftTable extends PluginRPGCT implements Listener{
	@EventHandler
	public void OpenTable(PlayerInteractEvent e){
		Block sign=e.getClickedBlock();
		if(sign==null||sign.getType().name().equalsIgnoreCase("air")||
				e.getAction()!=Action.RIGHT_CLICK_BLOCK)return;	
		
		//Workbench
		if(sign.getType().name().equalsIgnoreCase(rpgct.getWorkbenchItems()[0])&&
				compare(sign.getRelative(BlockFace.UP).getType(),rpgct.getWorkbenchItems()[3])&&
				compare(sign.getRelative(BlockFace.DOWN).getType(),rpgct.getWorkbenchItems()[4])){
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getWorkbenchItems()[1])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getWorkbenchItems()[2])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getWorkbenchItems()[6])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getWorkbenchItems()[5])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getWorkbenchItems()[6])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getWorkbenchItems()[5])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getWorkbenchItems()[2])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getWorkbenchItems()[1])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getWorkbenchItems()[2])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getWorkbenchItems()[1])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getWorkbenchItems()[5])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getWorkbenchItems()[6])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getWorkbenchItems()[5])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getWorkbenchItems()[6])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getWorkbenchItems()[1])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getWorkbenchItems()[2])){
				e.setCancelled(true);
				openWorkbench(e.getPlayer(), sign);
				return;
			}
			
		}
		
		
		//Fire Craft Table
		//当上方方块与下方方块不为指定方块时返回.	
		if(sign.getType().name().equalsIgnoreCase(rpgct.getFurnaceItems()[0])&&
				compare(sign.getRelative(BlockFace.UP).getType(),rpgct.getFurnaceItems()[3])&&
				compare(sign.getRelative(BlockFace.DOWN).getType(),rpgct.getFurnaceItems()[4])){
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getFurnaceItems()[1])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getFurnaceItems()[2])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getFurnaceItems()[6])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getFurnaceItems()[5])){
				e.setCancelled(true);
				openFCT(e.getPlayer(), sign);
				return;
			}
			
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getFurnaceItems()[6])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getFurnaceItems()[5])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getFurnaceItems()[2])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getFurnaceItems()[1])){
				e.setCancelled(true);
				openFCT(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getFurnaceItems()[2])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getFurnaceItems()[1])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getFurnaceItems()[5])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getFurnaceItems()[6])){
				e.setCancelled(true);
				openFCT(e.getPlayer(), sign);
				return;
			}
			if(compare(sign.getRelative(BlockFace.NORTH).getType(),rpgct.getFurnaceItems()[5])&&
					compare(sign.getRelative(BlockFace.SOUTH).getType(),rpgct.getFurnaceItems()[6])&&
					compare(sign.getRelative(BlockFace.EAST).getType(),rpgct.getFurnaceItems()[1])&&
					compare(sign.getRelative(BlockFace.WEST).getType(),rpgct.getFurnaceItems()[2])){
				e.setCancelled(true);
				openFCT(e.getPlayer(), sign);
				return;
			}
		}
		//Fire Craft Table End
		
	}
	private void openFCT(Player p,Block b){
		
		if(MainData.WORKBENCH.containsKey(Util.getWorkbenchID(b))){
			if(MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName().equals(p.getName()))
				
		}
		
		
		TempData.createTimerTemp.put(p.getName(), new TableTimer(p.getName(), WorkbenchType.FURNACE, b.getWorld().getName(), b.getX(), b.getY(), b.getZ()));
		
		
		ItemStack itemKey=Items.createItemKey(Material.STAINED_GLASS_PANE, (short)15,
				b.getWorld().getName(), b.getX(), b.getY(), b.getZ());
		if(rpgct.getFCT().containsKey(itemKey.getItemMeta().getDisplayName()))
			if(rpgct.getFCT(itemKey.getItemMeta().getDisplayName())[0].equals(p.getName()))
				p.openInventory(FurnaceGUI.openFCTGUI(itemKey));
			else
				p.sendMessage(rpgct.getMsg("UseByOther"));
		else
			p.openInventory(FurnaceGUI.createFCTGUI(itemKey));
	}
	
	private void openWorkbench(Player p,Block b){
		
		if(MainData.WORKBENCH.containsKey(Util.getWorkbenchID(b))){
			if(MainData.WORKBENCH.get(Util.getWorkbenchID(b)).getPlayerName().equals(p.getName()))
				p.openInventory(WorkbenchGUI.openWorkbenchGUI((WorkbenchTimer) MainData.WORKBENCH.get(Util.getWorkbenchID(b))));
			else
				//useByother
				;
		}else{			
			TempData.createTimerTemp.put(p.getName(), new TableTimer(p.getName(), WorkbenchType.WORKBENCH, b.getWorld().getName(), b.getX(), b.getY(), b.getZ()));
			p.openInventory(WorkbenchGUI.createWorkbenchGUI());
		}
		
		
		
		
		
		//UseByOther
		ItemStack itemKey=Items.createItemKey(Material.STAINED_GLASS_PANE, (short)15,
				b.getWorld().getName(), b.getX(), b.getY(), b.getZ());
		if(rpgct.getWorkbench().containsKey(itemKey.getItemMeta().getDisplayName()))
			if(rpgct.getWorkbench(itemKey.getItemMeta().getDisplayName())[0].equals(p.getName()))
				p.openInventory(WorkbenchGUI.openWorkbenchGUI(itemKey));
			else
				p.sendMessage(rpgct.getMsg("UseByOther"));
		else
			p.openInventory(WorkbenchGUI.createWorkbenchGUI(itemKey));
	}
	
	private boolean compare(Material type, String name){
		if(name==null||name.equalsIgnoreCase("null"))return true;
		return type.name().equalsIgnoreCase(name);
	}
	
}
