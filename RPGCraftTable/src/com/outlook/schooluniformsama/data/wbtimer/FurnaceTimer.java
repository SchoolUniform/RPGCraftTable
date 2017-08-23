package com.outlook.schooluniformsama.data.wbtimer;

import org.bukkit.Bukkit;

import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;

public class FurnaceTimer extends TableTimer{
	private double minTemperature;
	private double maxTemperature;
	private double extraTemperature;
	private double lastTemperature;
	private int saveTime;
	private boolean isBad=false;
	
	public FurnaceTimer(String playerName,String worldName,int x,int y,int z){
		super(playerName,WorkbenchType.FURNACE,worldName,x,y,z);
	}
	
	public FurnaceTimer(String playerName,String worldName,int x,int y,int z,double extra,boolean isBad){
		super(playerName,WorkbenchType.FURNACE,worldName,x,y,z);
		this.extraTemperature=extra;
		this.isBad=isBad;
	}
	
	public void loadData(double extra,boolean isBad,int time){
		this.time=time;
		this.extraTemperature=extra;
		this.isBad=isBad;
	}
	
	public FurnaceRecipe getRecipe(){
		return FurnaceRecipe.load(recipeName, fileName);
	}
	
	public void start(FurnaceRecipe fr,double last){
		this.needTime=fr.getNeedTime();
		this.fileName=fr.getFileName();
		this.recipeName=fr.getName();
		this.minTemperature = fr.getMinTemperature();
		this.maxTemperature=fr.getMaxTemperature();
		this.saveTime = fr.getSaveTime();
		this.lastTemperature=last;
	}
	
	@Override
	public void subTime() {
		
		if(!Bukkit.getWorld(worldName).getBlockAt(x, y, z).getType().name().equalsIgnoreCase(MainData.FURNACEITEMS[0]))
			return;
		
		double nowTemperature = FurnaceRecipe.getBlocks(getLocation());
		extraTemperature+=lastTemperature-nowTemperature;
		if(minTemperature>=0){
			
			if(extraTemperature<0)
				extraTemperature=0;
			else if(extraTemperature>0)
				extraTemperature--;
			
			if(nowTemperature+extraTemperature>maxTemperature)
				isBad=true;

			if(time>saveTime+needTime)
				isBad=true;
			
			//sub time
			if(nowTemperature+extraTemperature>minTemperature && !isBad)
				this.time++;
			else return;
		}else{
			if(extraTemperature>0)
				extraTemperature=0;
			else if(extraTemperature<0)
				extraTemperature++;
			
			if(nowTemperature+extraTemperature>maxTemperature)
				isBad=true;
			
			if(time>saveTime+needTime)
				isBad=true;
			
			if(nowTemperature+extraTemperature<minTemperature && !isBad)
				this.time++;
			else return;
		}
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public double getExtraTemperature() {
		return extraTemperature;
	}

	public double getLastTemperature() {
		return lastTemperature;
	}

	public int getSaveTime() {
		return saveTime;
	}

	public boolean isBad() {
		return isBad;
	}
	
}
