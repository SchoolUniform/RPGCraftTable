package com.outlook.schooluniformsama.data;

import java.util.HashMap;

import com.outlook.schooluniformsama.data.recipe.Recipe;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;

public class TempData {
	public static HashMap<String,Recipe> createRecipeTemp=new HashMap<>();
	public static HashMap<String,TableTimer> createTimerTemp=new HashMap<>();
	public static HashMap<String,TableTimer> openingWorkbench=new HashMap<>();
}
