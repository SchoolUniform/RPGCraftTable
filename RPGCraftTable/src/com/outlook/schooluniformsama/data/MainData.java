package com.outlook.schooluniformsama.data;

import java.io.File;
import java.util.HashMap;

import com.outlook.schooluniformsama.data.wbtimer.TableTimer;

public class MainData {
	public static int L,W,H;
	public static double DISTANCEEFFECT;
	public static File DATAFOLDER;
	public static String LANG;
	public static HashMap<String,String> BLUEPRINTLABEL=new HashMap<>();
	public static HashMap<String,Double> HEATSOURCE = new HashMap<String, Double>();
	public static com.outlook.schooluniformsama.utils.HashMap<String,TableTimer> WORKBENCH = new com.outlook.schooluniformsama.utils.HashMap<>();
	public static String[] WORKBENCHITEMS=new String[8];
	public static String[] FURNACEITEMS=new String[8];
	public static String PREFIX;
}
