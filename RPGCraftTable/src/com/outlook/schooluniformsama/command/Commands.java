package com.outlook.schooluniformsama.command;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.TempData;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.gui.FurnaceGUI;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;
import com.outlook.schooluniformsama.task.SaveFile;
import com.outlook.schooluniformsama.utils.Msg;

public class Commands {
	@Command(args = { "[RecipeName]","[FileName]","[NeedTime]","[SaveTime]","[MinTemperature]","[MaxTemperature]"}, cmd = "cfr", des = "CreateFurnaceRecipeDes")
	public void createFurnaceRecipe(Player p,String args[]){
		
		if(!p.hasPermission("RPGCraftTable.admin")){
			Msg.sendMsgToPlayer(p, "NoPermission", true);
			return;
		}
		
		if(args.length!=7){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}
		
		if(isInt(args[3])==null){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}else if(isInt(args[4])==null){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}else if(isDouble(args[5])==null){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}else if(isDouble(args[6])==null){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}

		 File f=new File(MainData.DATAFOLDER+File.separator+"recipe"+File.separator+"furnace"+File.separator+args[2]+".yml");
		if(f.exists()&&YamlConfiguration.loadConfiguration(f).getString(args[1]+".name","null")!="null"){
			Msg.sendMsgToPlayer(p, "RecipeIsExists", true);
			return;
		}
		
		TempData.createRecipeTemp.put(p.getName(), new FurnaceRecipe(args[1], args[2], isInt(args[3]), isInt(args[4]), isDouble(args[5]), isDouble(args[6])));
		p.openInventory(FurnaceGUI.createFurnaceMakerGUI());
	}
	
	@Command(args = { "[RecipeName]","[FileName]","[NeedTime]" }, cmd = "cwr", des = "CreateWorkbenchRecipeDes")
	public void createWorkbenchRecipe(Player p,String args[]){
		
		if(!p.hasPermission("RPGCraftTable.admin")){
			Msg.sendMsgToPlayer(p, "NoPermission", true);
			return;
		}
		
		if(args.length!=4){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}
		if(isInt(args[3])==null){
			Msg.sendMsgToPlayer(p, "CommandIsWrong", true);
			return;
		}
		
		 File f=new File(MainData.DATAFOLDER+File.separator+"recipe"+File.separator+"workbench"+File.separator+args[2]+".yml");
		 if(f.exists()&&YamlConfiguration.loadConfiguration(f).getString(args[1]+".name","null")!="null"){
				Msg.sendMsgToPlayer(p, "RecipeIsExists", true);
				return;
		}
		 
		 TempData.createRecipeTemp.put(p.getName(), new WorkbenchRecipe(args[1], args[2], isInt(args[3])));
	   	p.openInventory(WorkbenchGUI.createWorkbenchRecipeGUI());
	}
	
	@Command(cmd = "save", des = "CommandSave")
	public void saveFiles(Player p,String args[]){
		
		if(!p.hasPermission("RPGCraftTable.admin")){
			Msg.sendMsgToPlayer(p, "NoPermission", true);
			return;
		}
		
		SaveFile.saveWorkbench();
		Msg.sendMsgToPlayer(p, "SaveData", true);
	}
	
	/*
	@Command(cmd = "of",args={"[Number]"},des = "")
	public void openFurnace(Player p,String args[]){
		
	}*/
	
	private Integer isInt(String num){
		int n;
		try {
			n=Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return null;
		}
		return n;
	}
	
	private Double isDouble(String num){
		double n;
		try {
			n=Double.parseDouble(num);
		} catch (NumberFormatException e) {
			return null;
		}
		return n;
	}
	
}
