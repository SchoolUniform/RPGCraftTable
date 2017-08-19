package com.outlook.schooluniformsama.data;

import java.io.File;
import java.util.HashMap;

import com.outlook.schooluniformsama.data.wbtimer.TableTimer;

public class MainData {
	public static File DATAFOLDER;
	public static HashMap<String,Double> HEATSOURCE = new HashMap<String, Double>();
	public static HashMap<String,TableTimer> WORKBENCH = new HashMap<>();
}
