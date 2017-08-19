package com.outlook.schooluniformsama.data.wbtimer;

import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;

public class WorkbenchTimer extends TableTimer{
	
	public WorkbenchTimer(String playerName,String worldName,int x,int y,int z){
		super(playerName,WorkbenchType.WORKBENCH,worldName,x,y,z);
	}
	
	public WorkbenchRecipe getRecipe(){
		return WorkbenchRecipe.load(recipeName, fileName);
	}
	
	public void start(WorkbenchRecipe recipe){
		this.time=0;
		this.needTime=recipe.getNeedTime();
		this.fileName=recipe.getFileName();
		this.recipeName=recipe.getFileName();
	}
	
	
	
}
