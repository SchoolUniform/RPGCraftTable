package com.outlook.schooluniformsama.utils;

import org.bukkit.block.Block;

import com.outlook.schooluniformsama.data.wbtimer.TableTimer;

public class Util{
	public static String getWorkbenchID(TableTimer tt){
		return tt.getWorldName()+tt.getX()+tt.getY()+tt.getZ();
	}
	
	public static String getWorkbenchID(Block b){
		return b.getWorld().getName()+b.getX()+b.getY()+b.getZ();
	}
	
	public static String keepXDecimalPlaces(int x,double d){
		return String.format("%."+x+"f", d);
	}
	
	public static String randomColor(){
		int a=(int) (Math.random()*15);
		switch(a){
			case 10:return "a";
			case 11:return "b";
			case 12:return "c";
			case 13:return "d";
			case 14:return "e";
			case 15:return "f";
			default: return a+"";
		}
	}

    public static String removeColour(String input){
        return input.replaceAll("&r", "")
					        .replaceAll("&o", "")
					        .replaceAll("&n", "")
					        .replaceAll("&m", "")
					        .replaceAll("&l", "")
					        .replaceAll("&k", "")
					        .replaceAll("&f", "")
					        .replaceAll("&e", "")
					        .replaceAll("&d", "")
					        .replaceAll("&c", "")
					        .replaceAll("&b", "")
					        .replaceAll("&a", "")
					        .replaceAll("&9", "")
					        .replaceAll("&8", "")
					        .replaceAll("&7", "")
					        .replaceAll("&6", "")
					        .replaceAll("&5", "")
					        .replaceAll("&4", "")
					        .replaceAll("&3", "")
					        .replaceAll("&2", "")
					        .replaceAll("&1", "")
					        .replaceAll("&0", "")
					        .replaceAll("\247r", "")
					        .replaceAll("\247o", "")
					        .replaceAll("\247n", "")
					        .replaceAll("\247m", "")
					        .replaceAll("\247l", "")
					        .replaceAll("\247k", "")
					        .replaceAll("\247f", "")
					        .replaceAll("\247e", "")
					        .replaceAll("\247d", "")
					        .replaceAll("\247c", "")
					        .replaceAll("\247b", "")
					        .replaceAll("\247a", "")
					        .replaceAll("\2479", "")
					        .replaceAll("\2478", "")
					        .replaceAll("\2477", "")
					        .replaceAll("\2476", "")
					        .replaceAll("\2475", "")
					        .replaceAll("\2474", "")
					        .replaceAll("\2473", "")
					        .replaceAll("\2472", "")
					        .replaceAll("\2471", "")
					        .replaceAll("\2470", "");
    }
    
    
}
