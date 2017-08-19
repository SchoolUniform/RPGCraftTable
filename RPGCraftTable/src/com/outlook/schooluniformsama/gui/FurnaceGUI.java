package com.outlook.schooluniformsama.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.FurnaceRecipe;
import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;

public class FurnaceGUI extends PluginRPGCT{
	public static final List<Integer> mSlot=Arrays.asList(12,13,14,21,22,23);
	public static final List<Integer> pSlot=Arrays.asList(39,40,41);
	public static final List<Integer> tSlot=Arrays.asList(16,25,34,43);
	public static final List<Integer> passSlot=Arrays.asList(10,19,28,37);
	
	//创建默认的界面
	private static Inventory createDefGUI(String title){
		Inventory inv = Bukkit.createInventory(null,54 , title);
		for(int i=0;i<54;i++)
			if(!mSlot.contains(i)&&!pSlot.contains(i)&&!tSlot.contains(i)&&!passSlot.contains(i))
				inv.setItem(i, Items.createPItem((short)15, ""));
		for(int i:passSlot)
			inv.setItem(i, Items.createPItem((short)0, ""));
		for(int i:tSlot)
			inv.setItem(i, Items.createPItem((short)0, ""));
		inv.setItem(4, null);
		return inv;
	}
	
	public static Inventory createFCTGUI(){
		Inventory inv=createDefGUI(rpgct.getFurnaceItems()[7]);
		inv.setItem(49, Items.createPItem((short) 14, "§b§l==>§c§l开始制造§b§l<=="));
		return inv;
	}
	
	public static Inventory openFCTGUI(FurnaceTimer ft){
		Inventory inv=createDefGUI(rpgct.getFurnaceItems()[7]+"§3§l*");
		return checkHeatSource(FurnaceRecipe.load(rpgct.getFCT(itemKey.getItemMeta().getDisplayName())[1].split(":")[0],
				rpgct.getFCT(itemKey.getItemMeta().getDisplayName())[1].split(":")[1]).setInv(inv));
	}
	
	public static Inventory createFCTPGUI(ItemStack itemKey){
		Inventory inv=createDefGUI(rpgct.getFurnaceItems()[7]+"*");
		inv.setItem(0, itemKey);
		inv.setItem(49, Items.createPItem((short) 14, "§b§l==>§c§l保存§b§l<=="));
		return inv;
	}
	
	public static double checkHeatSource(String value[]){
		return FurnaceRecipe.getBlocks(Bukkit.getServer().getWorld(value[5]).
				getBlockAt(Integer.parseInt(value[6]),Integer.parseInt(value[7]),Integer.parseInt(value[8])).getLocation());
	}
	
	public static Inventory checkHeatSource(Inventory inv,FurnaceTimer ft){
		
		if(ft.isBad()){
			
		}
		
		
		
		
		
		
		String value[] =rpgct.getFCT(inv.getItem(0).getItemMeta().getDisplayName());
		double tem=Double.parseDouble(value[3])+checkHeatSource(value);
		FurnaceRecipe fctr=FurnaceRecipe.load(value[1].split(":")[0],value[1].split(":")[1]);
		double tem2= fctr.getTemperature();
		
		if(tem2>=0){
			double x=tem/tem2;
			if(x>0)
				inv.setItem(43,  Items.createPItem((short)1, "§e§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>0.25)
				inv.setItem(34,  Items.createPItem((short)1, "§e§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>0.5)
				inv.setItem(25,  Items.createPItem((short)1, "§e§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>0.75)
				inv.setItem(16,  Items.createPItem((short)1, "§e§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
		}else{
			double x=tem/tem2;
			if(x>0.25)
				inv.setItem(43,  Items.createPItem((short)3, "§3§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>0.5)
				inv.setItem(34,  Items.createPItem((short)3, "§3§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>0.75)
				inv.setItem(25,  Items.createPItem((short)3, "§3§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
			if(x>1)
				inv.setItem(16,  Items.createPItem((short)3, "§3§l温度: "+_2f(tem)+"℃ - "+_2f(x*100)+"%"));
		}
		int time=Integer.parseInt(value[2]);
		double x2=(double)time/fctr.getTime();
		if(x2>=0)
			inv.setItem(37,  Items.getPlaceholder10((short)5, (int)(x2*100)));
		if(x2>=0.5)
			inv.setItem(28,  Items.getPlaceholder10((short)5, (int)(x2*100)));
		if(x2>=0.75)
			inv.setItem(19,  Items.getPlaceholder10((short)5, (int)(x2*100)));
		if(x2>=1)
			inv.setItem(10,  Items.getPlaceholder10((short)5, (int)(x2*100)));
		if(x2>=1)
			inv.setItem(49, Items.createPItem((short) 14, "§b§l==>§c§lOK§b§l<=="));
		return inv;
	}
	
	private static String _2f(double d){
		return String.format("%.2f", d);
	}
}
