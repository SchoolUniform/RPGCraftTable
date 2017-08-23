package com.outlook.schooluniformsama;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.schooluniformsama.command.Commands;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.recipe.FurnaceRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchRecipe;
import com.outlook.schooluniformsama.data.recipe.WorkbenchType;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;
import com.outlook.schooluniformsama.events.CreateCarftTable;
import com.outlook.schooluniformsama.events.UseCraftTables;
import com.outlook.schooluniformsama.events.Watcher;
import com.outlook.schooluniformsama.task.CraftTablesTimer;
import com.outlook.schooluniformsama.task.SaveFile;
import com.outlook.schooluniformsama.utils.Msg;
import com.outlook.schooluniformsama.utils.Util;


public class RPGCraftTable extends JavaPlugin{
	private Commands cmds=new Commands();
	@Override
	public void onEnable() {
		firstRun();
		readConfigs();
		registerListeners();
		getLogger().info("载入成功,欢迎使用 RPGCraftTable.");
		getLogger().info("此插件由School_Uniform制作");
	}
	@Override
	public void onDisable() {
		SaveFile.saveWorkbench();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("rpgct")){
			if(!(sender instanceof Player)){
				sender.sendMessage(Msg.getMsg("IsNotPlayer", true));
				return true;
			}
			Player p = (Player)sender;
			if(args.length==0){
				for(Method method:Commands.class.getDeclaredMethods()){
					if(!method.isAnnotationPresent(com.outlook.schooluniformsama.command.Command.class))
						continue;
					com.outlook.schooluniformsama.command.Command cmd=method.getAnnotation(com.outlook.schooluniformsama.command.Command.class);
					String arg="";
					if(cmd.args()!=null||cmd.args().length!=0)
						for(String temp:cmd.args())
							arg+="&"+Util.randomColor()+temp+" ";
					 p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&"+Util.randomColor()+"/rpgct &"+Util.randomColor()+cmd.cmd()+
							 " &"+Util.randomColor()+arg+"&"+Util.randomColor()+"-&"+Util.randomColor()+Msg.getMsg(cmd.des(),false)));
				}
				return true;
			}
			for(Method method:Commands.class.getDeclaredMethods()){
				if(!method.isAnnotationPresent(com.outlook.schooluniformsama.command.Command.class))
					continue;
				com.outlook.schooluniformsama.command.Command cmd=method.getAnnotation(com.outlook.schooluniformsama.command.Command.class);
				if(!cmd.cmd().equalsIgnoreCase(args[0]))
                    continue;
				 try {method.invoke(cmds, p,args);}catch (Exception e) { e.printStackTrace();}
				return true;
			}
			p.sendMessage(Msg.getMsg("SubCommandNotFind", true));
			return true;
		}
		return false;
	}
	
	private void firstRun(){
		if(!getDataFolder().exists()) 
	        getDataFolder().mkdir();
		if(!new File(getDataFolder()+File.separator+"recipe").exists())
			new File(getDataFolder()+File.separator+"recipe").mkdir();
		if(!new File(getDataFolder()+File.separator+"recipe"+File.separator+"workbench").exists())
			new File(getDataFolder()+File.separator+"recipe"+File.separator+"workbench").mkdir();
		if(!new File(getDataFolder()+File.separator+"recipe"+File.separator+"furnace").exists())
			new File(getDataFolder()+File.separator+"recipe"+File.separator+"furnace").mkdir();
		if(!new File(getDataFolder()+File.separator+"config.yml").exists())
			saveDefaultConfig();
		if(!new File(getDataFolder()+File.separator+"workbench.yml").exists())
			try {new File(getDataFolder()+File.separator+"workbench.yml").createNewFile();}
			catch (IOException e1) {}
		try{reloadConfig();}catch (Exception e){}
	}
	
	private void readConfigs(){
		MainData.DATAFOLDER=getDataFolder();
		MainData.LANG=getConfig().getString("Language","zh_CN");
		MainData.PREFIX=getConfig().getString("Prefix");
		Msg.init();
		
		//workbench
		MainData.WORKBENCHITEMS[0]=getConfig().getString("workbench.main");
		MainData.WORKBENCHITEMS[1]=getConfig().getString("workbench.left");
		MainData.WORKBENCHITEMS[2]=getConfig().getString("workbench.right");
		MainData.WORKBENCHITEMS[3]=getConfig().getString("workbench.up");
		MainData.WORKBENCHITEMS[4]=getConfig().getString("workbench.down");
		MainData.WORKBENCHITEMS[5]=getConfig().getString("workbench.front");
		MainData.WORKBENCHITEMS[6]=getConfig().getString("workbench.behind");
		MainData.WORKBENCHITEMS[7]=getConfig().getString("workbench.title");
		
		//furnace
		MainData.FURNACEITEMS[0]=getConfig().getString("furnace.main");
		MainData.FURNACEITEMS[1]=getConfig().getString("furnace.left");
		MainData.FURNACEITEMS[2]=getConfig().getString("furnace.right");
		MainData.FURNACEITEMS[3]=getConfig().getString("furnace.up");
		MainData.FURNACEITEMS[4]=getConfig().getString("furnace.down");
		MainData.FURNACEITEMS[5]=getConfig().getString("furnace.front");
		MainData.FURNACEITEMS[6]=getConfig().getString("furnace.behind");
		MainData.FURNACEITEMS[7]=getConfig().getString("furnace.title");
		
		MainData.L=getConfig().getInt("HeatSource.L");
		MainData.W=getConfig().getInt("HeatSource.W");
		MainData.H=getConfig().getInt("HeatSource.H");
		MainData.DISTANCEEFFECT=getConfig().getDouble("HeatSource.DistanceEffect");
		for(String temp:getConfig().getStringList("HeatSource.Blocks"))
			MainData.HEATSOURCE.put(temp.split(":")[0], Double.parseDouble(temp.split(":")[1]));
		
		for(String temp:getConfig().getStringList("BlueprintLabel"))
			MainData.BLUEPRINTLABEL.put(temp.split(":")[0],temp.split(":")[1]);
		
		YamlConfiguration workbench = YamlConfiguration.loadConfiguration(new File(getDataFolder()+File.separator+"workbench.yml"));
		for(String key:workbench.getStringList("list")){
			TableTimer tt = new TableTimer(workbench.getString(key+".playerName"), WorkbenchType.valueOf(workbench.getString(key+".type")), 
					workbench.getString(key+".worldName"), workbench.getInt(key+".x"),  workbench.getInt(key+".y"),  workbench.getInt(key+".z"));
			switch(tt.getType()){
				case FURNACE:
					FurnaceTimer ft = tt.toFurnaceTimer();
					FurnaceRecipe fr = FurnaceRecipe.load(workbench.getString(key+".recipeName"), workbench.getString(key+".fileName"));
					ft.start(fr, FurnaceRecipe.getBlocks(ft.getLocation()));
					ft.loadData(workbench.getDouble(key+".extra"), workbench.getBoolean("isBad"),workbench.getInt(key+".time"));
					MainData.WORKBENCH.put(key, ft);
					break;
				case WORKBENCH:
					WorkbenchTimer wt = tt.toWorkbenchTimer();
					wt.continueStart(WorkbenchRecipe.load(workbench.getString(key+".recipeName"), workbench.getString(key+".fileName")),workbench.getInt(key+".time"));
					MainData.WORKBENCH.put(key, wt);
					break;
			}
		}
		
	}

	private void registerListeners(){
		getServer().getPluginManager().registerEvents(new CreateCarftTable(), this);
		getServer().getPluginManager().registerEvents(new UseCraftTables(), this);
		getServer().getPluginManager().registerEvents(new Watcher(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CraftTablesTimer(), 20L, 20L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SaveFile(), 20L, 60*20L);
	}
}
