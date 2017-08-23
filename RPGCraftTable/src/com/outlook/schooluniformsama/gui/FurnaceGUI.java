package com.outlook.schooluniformsama.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.utils.Msg;
import com.outlook.schooluniformsama.utils.Util;

public class FurnaceGUI{
	public static final List<Integer> mSlot=Arrays.asList(12,13,14,21,22,23);
	public static final List<Integer> pSlot=Arrays.asList(39,40,41);
	public static final List<Integer> tSlot=Arrays.asList(16,25,34,43);
	public static final List<Integer> passSlot=Arrays.asList(10,19,28,37);
	
	//创建默认的界面
	private static Inventory createDefGUI(String title){
		Inventory inv = Bukkit.createInventory(null,54 , title);
		for(int i=0;i<54;i++)
			if(!mSlot.contains(i)&&!pSlot.contains(i)&&!tSlot.contains(i)&&!passSlot.contains(i))
				inv.setItem(i, Items.createPItem((short)15, " "));
		for(int i:passSlot)
			inv.setItem(i, Items.createPItem((short)0, " "));
		for(int i:tSlot)
			inv.setItem(i, Items.createPItem((short)0, " "));
		inv.setItem(4, null);
		return inv;
	}
	
	public static Inventory createFurnaceGUI(){
		Inventory inv=createDefGUI(MainData.FURNACEITEMS[7]);
		inv.setItem(49, Items.createPItem((short) 14, Msg.getMsg("FurnaceStart", false)));
		return inv;
	}
	
	public static Inventory openFurnaceGUI(FurnaceTimer ft){
		Inventory inv=createDefGUI(MainData.FURNACEITEMS[7]+"§3§l*");
		return checkHeatSource(FurnaceRecipe.load(ft.getRecipeName(),ft.getFileName()).setInv(inv),ft);
	}
	
	public static Inventory createFurnaceMakerGUI(){
		Inventory inv=createDefGUI(MainData.FURNACEITEMS[7]+"*");
		inv.setItem(49, Items.createPItem((short) 14, Msg.getMsg("SaveRecipe", false)));
		return inv;
	}
	
	public static Inventory checkHeatSource(Inventory inv,FurnaceTimer ft){
		ItemStack temp;
		if(ft.isBad()){
			for(int i:passSlot)
				inv.setItem(i,  Items.createPItem((short)12, Msg.getMsg("FurnaceFailed", false)));
			inv.setItem(49,   Items.createPItem((short)5, Msg.getMsg("WorkbenchProgress2", false)));
			return inv;
		}
		
		double nowTemperature = FurnaceRecipe.getBlocks(ft.getLocation());
		double pass=(ft.getExtraTemperature()+nowTemperature)/ft.getMinTemperature();
		if(ft.getMinTemperature()>0){
			temp= Items.createPItem((short)1, Msg.getMsg("FurnaceWorkProgress+", new String[]{"%temperature%","%minTemperature%"},
					new String[]{Util.keepXDecimalPlaces(2,nowTemperature),Util.keepXDecimalPlaces(2, ft.getMinTemperature())},false));
			if(pass>0)
				inv.setItem(43,temp );
			if(pass>0.25)
				inv.setItem(34,temp);
			if(pass>0.5)
				inv.setItem(25,temp);
			if(pass>0.75)
				inv.setItem(16,temp);
		}else{
			temp= Items.createPItem((short)3, Msg.getMsg("FurnaceWorkProgress-", new String[]{"%temperature%","%minTemperature%"},
					new String[]{Util.keepXDecimalPlaces(2,nowTemperature),Util.keepXDecimalPlaces(2, ft.getMinTemperature())},false));
			if(pass>0)
				inv.setItem(43,temp );
			if(pass>0.25)
				inv.setItem(34,temp);
			if(pass>0.5)
				inv.setItem(25,temp);
			if(pass>0.75)
				inv.setItem(16,temp);
		}
		//Time
		pass=ft.getTime()/(double)ft.getNeedTime();
		temp=Items.createPItem((short)5, Msg.getMsg("FurnaceTimeProgress", new String[]{"%timeProgress%","%time%"},
				new String[]{Util.keepXDecimalPlaces(2,pass*100),(ft.getNeedTime()-ft.getTime())+""},false));
		if(pass>=0)
			inv.setItem(37, temp );
		if(pass>=0.5)
			inv.setItem(28,  temp);
		if(pass>=0.75)
			inv.setItem(19,  temp);
		if(pass>=1){
			inv.setItem(10,   temp);
			inv.setItem(49,   Items.createPItem((short)5, Msg.getMsg("WorkbenchProgress2", false)));
		}
		//SaveTime
		if(ft.getTime()>ft.getNeedTime()){
			pass=ft.getTime()/(double)(ft.getNeedTime()+ft.getSaveTime());
			temp=Items.createPItem((short)12, Msg.getMsg("FuranceSaveProgress", new String[]{"%time%"},
					new String[]{(ft.getNeedTime()+ft.getSaveTime()-ft.getTime())+""},false));
			if(pass>=0)
				inv.setItem(37,  temp);
			if(pass>=0.5)
				inv.setItem(28,  temp);
			if(pass>=0.75)
				inv.setItem(19,  temp);
			if(pass>=1)
				inv.setItem(10,  temp);
		}
		return inv;
	}
}
