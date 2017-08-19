package com.outlook.schooluniformsama;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.outlook.schooluniformsama.data.PluginRPGCT;
import com.outlook.schooluniformsama.events.CreateCarftTable;
import com.outlook.schooluniformsama.events.UseCraftTables;
import com.outlook.schooluniformsama.events.Watcher;
import com.outlook.schooluniformsama.task.CraftTablesTimer;
import com.outlook.schooluniformsama.task.SaveFile;


public class RPGCraftTable extends JavaPlugin{
	private String[] workbenchItems=new String[8];
	private String[] furnaceItems=new String[8];
	private HashMap<String,String> blueprintTable=new HashMap<>();
	private HashMap<String,String> msg=new HashMap<>();
	private HashMap<String,String> workbench=new HashMap<>();
	private HashMap<String,String> furnace = new HashMap<String,String>();
	private HashMap<String,Double> heatSource = new HashMap<String, Double>();
	//private LinkedList<String> wRecipe =new LinkedList<>();
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
		return Commands.cmd(sender, command, label, args);
	}
	
	private void firstRun(){
		PluginRPGCT.rpgct=this;
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
		if(!new File(getDataFolder()+File.separator+"messages.yml").exists())
			this.saveResource("messages.yml", true);
		try{reloadConfig();}catch (Exception e){}
	}
	
	private void readConfigs(){
		//workbench
		workbenchItems[0]=getConfig().getString("workbench.main");//主方块
		workbenchItems[1]=getConfig().getString("workbench.left");//左
		workbenchItems[2]=getConfig().getString("workbench.right");//右
		workbenchItems[3]=getConfig().getString("workbench.up");//上
		workbenchItems[4]=getConfig().getString("workbench.down");//下
		workbenchItems[5]=getConfig().getString("workbench.front");//前
		workbenchItems[6]=getConfig().getString("workbench.behind");//后
		workbenchItems[7]=getConfig().getString("workbench.title");
		
		//furnace
		furnaceItems[0]=getConfig().getString("furnace.main");//主方块
		furnaceItems[1]=getConfig().getString("furnace.left");//左
		furnaceItems[2]=getConfig().getString("furnace.right");//右
		furnaceItems[3]=getConfig().getString("furnace.up");//上
		furnaceItems[4]=getConfig().getString("furnace.down");//下
		furnaceItems[5]=getConfig().getString("furnace.front");//前
		furnaceItems[6]=getConfig().getString("furnace.behind");//后
		furnaceItems[7]=getConfig().getString("furnace.title");
		
		for(String temp:getConfig().getStringList("blueprintTable"))
			blueprintTable.put(temp.split(":")[0],temp.split(":")[1]);
		for(String temp:YamlConfiguration.loadConfiguration(
				new File(getDataFolder()+File.separator+"messages.yml")).getStringList("messages"))
			msg.put(temp.split(":")[0], temp.split(":")[1]);
		for(String temp:YamlConfiguration.loadConfiguration(new File(
				getDataFolder()+File.separator+"workbench.yml")).getStringList("workbench"))
			workbench.put(temp.split(":")[0], temp.split(":")[1]);
		//添加热源
		for(String temp:getConfig().getStringList("HeatSource"))
			heatSource.put(temp.split(":")[0], Double.parseDouble(temp.split(":")[1]));
	}

	private void registerListeners(){
		getServer().getPluginManager().registerEvents(new CreateCarftTable(), this);
		getServer().getPluginManager().registerEvents(new UseCraftTables(), this);
		getServer().getPluginManager().registerEvents(new Watcher(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CraftTablesTimer(), 20L, 20L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SaveFile(), 20L, 60*20L);
	}

	public String[] getWorkbenchItems() {
		return workbenchItems;
	}
	
	public String[] getFurnaceItems() {
		return furnaceItems;
	}
	
	public String getBlueprintTable(String key){
		return blueprintTable.get(key);
	}
	
	public String getMsg(String key){
		return msg.get("Prefix")+msg.get(key).split(";")[(int)Math.random()*msg.get(key).split(";").length];
	}
	
	public void addWorkbench(String key,String value){
		workbench.put(key, value);
	}
	
	public Object[] getWorkbench(String key){
		String[] temp =workbench.get(key).split(",");
		return new Object[]{temp[0],temp[1],Integer.parseInt(temp[2]),temp[3],
				Integer.parseInt(temp[4]),Integer.parseInt(temp[5]),Integer.parseInt(temp[6])};
	}
	
	public String getWorkbench(String key,int index){
		return workbench.get(key).split(",")[index];
	}
	
	public void removeWorkbench(String key){
		Iterator<String> keys=workbench.keySet().iterator();
		while(keys.hasNext()){
			String k=(String) keys.next();
			if(k.equals(key)){
				keys.remove();
				break;
			}
		}
		workbench.remove(key);
	}
	
	public void resetWorkbench(String key,String value){
		workbench.replace(key, value);
	}
	
	public HashMap<String, String> getWorkbench(){
		return workbench;
	}
	
	public HashMap<String, Double> getHeatSource() {
		return heatSource;
	}
	
	public double getDistanceEffect(){
		return heatSource.get("DistanceEffect");
	}
	
	public void addFCT(String key,String value){
		furnace.put(key, value);
	}
	
	public String[] getFCT(String key){
		return furnace.get(key).split(",");
	}
	
	public void removeFCT(String key){
		Iterator<String> keys=furnace.keySet().iterator();
		while(keys.hasNext()){
			String k=(String) keys.next();
			if(k.equals(key)){
				keys.remove();
				break;
			}
		}
		furnace.remove(key);
	}
	
	public void resetFCT(String key,String value){
		furnace.replace(key, value);
	}
	
	public HashMap<String, String> getFCT(){
		return furnace;
	}
}
