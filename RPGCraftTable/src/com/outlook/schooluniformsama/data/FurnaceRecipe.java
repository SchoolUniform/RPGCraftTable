package com.outlook.schooluniformsama.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.gui.FurnaceGUI;


public class FurnaceRecipe extends PluginRPGCT{
	private int time;
	private double temperature;
	private String name;
	private List<String> shape=new ArrayList<>();
	private HashMap<Character,ItemStack> materials=new HashMap<>();
	private ItemStack[] product=new ItemStack[3];
	
	public FurnaceRecipe(int time, double temperature, String name, List<String> shape,
			HashMap<Character, ItemStack> materials, ItemStack[] product) {
		super();
		this.time = time;
		this.temperature = temperature;
		this.name = name;
		this.shape = shape;
		this.materials = materials;
		this.product = product;
	}

	public boolean save(String fileName){
		YamlConfiguration recipe;
		 File f=new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"furnace"+File.separator+fileName+".yml");
		if(!f.exists())try {f.createNewFile();} catch (IOException e1) {return false;}
		
		recipe=YamlConfiguration.loadConfiguration(f);
		recipe.set(name+".name", name);
		recipe.set(name+".time", time);
		recipe.set(name+".temperature", temperature);
		recipe.set(name+".shape", shape);
		for(Character c:materials.keySet())
			recipe.set(name+".material."+c,materials.get(c) );
		for(int i=0;i<3;i++)
			recipe.set(name+".product."+i, product[i]);
		try {recipe.save(f);} catch (IOException e) {return false;}
		return true;
	}
	
	public static FurnaceRecipe load(String name,String fileName){
		 File f=new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"furnace"+File.separator+fileName+".yml");
		 if(!f.exists())return null;
		 YamlConfiguration recipe=YamlConfiguration.loadConfiguration(f);
		 HashMap<Character,ItemStack> materials=new HashMap<>();
		 char c=' ';
		 for(String s:recipe.getStringList(name+".shape"))
			 for(char temp:s.toCharArray())
				 if(temp!=' '&&temp!=c){
					 c=temp;
					 materials.put(c, recipe.getItemStack(name+".material."+c));
				 }
		 ItemStack[] product=new ItemStack[4];
		 for(int i=0;i<3;i++)
			 product[i]=recipe.getItemStack(name+".product."+i);
		 
		 return new FurnaceRecipe(recipe.getInt(name+".time"),recipe.getDouble(name+".temperature"), name,
				 recipe.getStringList(name+".shape"), materials, product);
	}
	
	public static boolean createFireCraftTableRecipe(Inventory inv){
		short index=0;
		char charArray[]="ABCDEFGHIJKLMNOP".toCharArray();
		int time=Integer.parseInt(inv.getItem(0).getItemMeta().getDisplayName().split(":")[1]);
		String s="";
		HashMap<ItemStack,Character> m=new HashMap<>();
		ItemStack is=inv.getItem(FurnaceGUI.mSlot.get(0));
		String name=inv.getItem(0).getItemMeta().getDisplayName().split(":")[0];
		String value=inv.getItem(0).getItemMeta().getDisplayName();//name;time;maxtem;tem
		String fileName=inv.getItem(0).getItemMeta().getDisplayName().split(":")[2];
		if(new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"furnace"+File.separator+fileName+".yml").exists())
			return false;
		
		if(is!=null)
			m.put(is,charArray[index]);

		for(int i:FurnaceGUI.mSlot){
			ItemStack temp=inv.getItem(i);
			if(temp==null){
				s+=" ";
				continue;
			}
			if(!temp.equals(is)){
				if(m.containsKey(temp))
					s+=m.get(temp);
				else{
					is=temp;
					index++;
					s+=charArray[index];
					m.put(is,charArray[index]);
				}
			}else{
				s+=charArray[index];
			}
		}
		
		HashMap<Character,ItemStack> m2=new HashMap<>();
		for(Entry<ItemStack, Character> entity:m.entrySet())
			m2.put(entity.getValue(), entity.getKey());
		
		FurnaceRecipe wr=new FurnaceRecipe(time,Double.parseDouble(value.split(":")[3]),
				name, Arrays.asList(s.substring(0,3),s.substring(3, 6)),m2, new ItemStack[]{inv.getItem(FurnaceGUI.pSlot.get(0)),
						inv.getItem(FurnaceGUI.pSlot.get(1)),inv.getItem(FurnaceGUI.pSlot.get(2))});
		return wr.save(fileName);
		
	}
	
	public FurnaceRecipe containsShape(Inventory inv){
		short index=0;
		char charArray[]="ABCDEFGHIJKLMNOP".toCharArray();
		String s="";
		HashMap<ItemStack,Character> m=new HashMap<>();
		ItemStack is=inv.getItem(FurnaceGUI.mSlot.get(0));
		if(is!=null)
			m.put(is,charArray[index]);

		for(int i:FurnaceGUI.mSlot){
			ItemStack temp=inv.getItem(i);
			if(temp==null){
				s+=" ";
				continue;
			}
			if(!temp.equals(is)){
				if(m.containsKey(temp))
					s+=m.get(temp);
				else{
					is=temp;
					index++;
					s+=charArray[index];
					m.put(is,charArray[index]);
				}
			}else{
				s+=charArray[index];
			}
		}
		
		if(!shape.equals(Arrays.asList(s.substring(0,3),s.substring(3, 6))))return null;
		if(materials.size()!=m.size())return null;
		HashMap<Character,ItemStack> m2=new HashMap<>();
		for(Entry<ItemStack, Character> entity:m.entrySet())
			m2.put(entity.getValue(), entity.getKey());
		if(!materials.equals(m2))return null;
		return this;
	}
	
	public boolean isTrue(Inventory inv){
		short index=0;
		for(String s:shape)
			for(char c:s.toCharArray()){
				if(c==' ')
					if(inv.getItem(FurnaceGUI.mSlot.get(index++))==null)
						continue;
					else 
						return false;
				else{
					ItemStack is=inv.getItem(FurnaceGUI.mSlot.get(index++));
					if(is==null)return false;
					if(is.equals(materials.get(c)))
						continue;
					else 
						return false;
				}
			}
		return true;
	}
	
	public Inventory setInv(Inventory inv){
		short index=0;
		for(String s:shape)
			for(char c:s.toCharArray()){
				if(c==' '){
					index++;
					continue;
				}
				inv.setItem(FurnaceGUI.mSlot.get(index++), materials.get(c));
			}
		index=0;
		while(index!=3)
			inv.setItem(FurnaceGUI.pSlot.get(index), product[index++]);
		return inv;
	}
	
	public static double getBlocks(Location l){
		double _tem=0;
		int x=l.getBlockX()+2;
		int y=l.getBlockY()+2;
		int z=l.getBlockZ()+2;
		
		for(int sx=x-4;sx<x;sx++)
			for(int sy=y-4;sy<y;sy++)
				for(int sz=z-4;sz<z;sz++){
					Block temp=l.getWorld().getBlockAt(sx, sy, sz);
					if(rpgct.getHeatSource().containsKey(temp.getType().name()))
						_tem+=rpgct.getHeatSource().get(temp.getType().name())*Math.pow(
								1-rpgct.getDistanceEffect(), Math.sqrt(Math.pow(sx-x, 2)+Math.pow(
										sy-y, 2)+Math.pow(sz-z, 2)));
				}
		return _tem;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getShape() {
		return shape;
	}

	public void setShape(List<String> shape) {
		this.shape = shape;
	}

	public HashMap<Character, ItemStack> getMaterials() {
		return materials;
	}

	public void setMaterials(HashMap<Character, ItemStack> materials) {
		this.materials = materials;
	}

	public ItemStack[] getProduct() {
		return product;
	}

	public void setProduct(ItemStack[] product) {
		this.product = product;
	}
}
