package com.outlook.schooluniformsama.data;

import com.outlook.schooluniformsama.data.recipe.Recipe;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;
import com.outlook.schooluniformsama.utils.HashMap;

public class TempData {
	public static HashMap<String,Recipe> createRecipeTemp=new HashMap<>();
	public static HashMap<String,TableTimer> createTimerTemp=new HashMap<>();
	public static HashMap<String,String> openingWorkbench=new HashMap<>();
}
