package com.outlook.schooluniformsama.data.wbtimer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.outlook.schooluniformsama.data.recipe.WorkbenchType;

public class TableTimer {
	
	protected String fileName;
	protected String worldName;
	protected String recipeName;
	protected String playerName;
	
	protected WorkbenchType type;
	
	protected int x,y,z;
	protected int time=0;
	protected int needTime;
	
	public TableTimer(String playerName,WorkbenchType type,String worldName,int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
		this.type=type;
		this.worldName=worldName;
		this.playerName=playerName;
	}
	
	public void subTime(){
		if(time<needTime)
			time++;
	}
	
	public boolean isOver(){
		if(time>=needTime)
			return true;
		return false;
	}
	
	public Location getLocation(){
		return new Location(Bukkit.getWorld(worldName), x, y, z);
	}
	
	public WorkbenchTimer toWorkbenchTimer(){
		return new WorkbenchTimer(playerName, worldName, x, y, z);
	}
	
	public FurnaceTimer toFurnaceTimer(){
		return new FurnaceTimer(playerName, worldName, x, y, z);
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(playerName);
	}
	
	public String getPlayerName(){
		return playerName;
	}

	public String getWorldName() {
		return worldName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getRecipeName(){
		return recipeName;
	}

	public WorkbenchType getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getTime() {
		return time;
	}

	public int getNeedTime() {
		return needTime;
	}
}
