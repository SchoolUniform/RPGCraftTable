package com.outlook.schooluniformsama.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.outlook.schooluniformsama.gui.WorkbenchGUI;

public class WorkbenchRecipe extends PluginRPGCT{
	/*shape
	 * "aaaa",
	 * "bbbb",
	 * "dddd",
	 * "gggg"*/
	private int time;
	private String fileName;
	private String name;
	private List<String> shape=new ArrayList<>();
	private HashMap<Character,ItemStack> materials=new HashMap<>();
	private ItemStack[] product=new ItemStack[4];
	
	
	
	public WorkbenchRecipe(int time, String name,String fileName, List<String> shape, HashMap<Character, ItemStack> materials,
			ItemStack[] product) {
		super();
		this.time = time;
		this.name = name;
		this.fileName=fileName;
		this.shape = shape;
		this.materials = materials;
		this.product = product;
	}

	public boolean save(){
		YamlConfiguration recipe;
		File f=new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"workbench"+File.separator+fileName+".yml");
			if(!f.exists())
				try {f.createNewFile();} catch (IOException e1) {return false;}
		recipe=YamlConfiguration.loadConfiguration(f);
		recipe.set(name+".name", name);
		recipe.set(name+".fileName", fileName);
		recipe.set(name+".time", time);
		recipe.set(name+".shape", shape);
		for(Character c:materials.keySet())
			recipe.set(name+".material."+c,materials.get(c) );
		for(int i=0;i<4;i++)
			recipe.set(name+".product."+i, product[i]);
		try {recipe.save(f);} catch (IOException e) {return false;}
		return true;
	}
	
	public static WorkbenchRecipe load(String name,String fileName){
		 File f=new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"workbench"+File.separator+fileName+".yml");
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
		 for(int i=0;i<4;i++)
			 product[i]=recipe.getItemStack(name+".product."+i);
		 return new WorkbenchRecipe(recipe.getInt(name+".time"), name,fileName,
				 recipe.getStringList(name+".shape"), materials, product);
	}
	
	public static boolean createWorkbenchRecipe(Inventory inv){
		short index=0;
		char charArray[]="ABCDEFGHIJKLMNOP".toCharArray();
		int time=Integer.parseInt(inv.getItem(0).getItemMeta().getDisplayName().split(";")[1]);
		String s="";
		HashMap<ItemStack,Character> m=new HashMap<>();
		ItemStack is=inv.getItem(WorkbenchGUI.materials.get(0));
		String name=inv.getItem(0).getItemMeta().getDisplayName().split(";")[0];
		String fileName=inv.getItem(0).getItemMeta().getDisplayName().split(";")[2];
		if(new File(rpgct.getDataFolder()+File.separator+"recipe"+File.separator+"workbench"+File.separator+fileName+".yml").exists())
			return false;
		
		if(is!=null){
			is.setAmount(1);
			m.put(is,charArray[index]);
		}
		
		for(int i:WorkbenchGUI.materials){
			ItemStack temp=inv.getItem(i);
			if(temp==null){
				s+=" ";
				continue;
			}
			temp.setAmount(1);
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
		
		WorkbenchRecipe wr=new WorkbenchRecipe(time, name, Arrays.asList(
				s.substring(0,4),s.substring(4, 8),s.substring(8,12),s.substring(12,16)),
				m2, new ItemStack[]{inv.getItem(WorkbenchGUI.products.get(0)),
						inv.getItem(WorkbenchGUI.products.get(1)),
						inv.getItem(WorkbenchGUI.products.get(2)),
						inv.getItem(WorkbenchGUI.products.get(3))});
		
		return wr.save();
		
	}
	
	public boolean containsShape(Inventory inv){
		short index=0;
		for(String s:shape)
			for(char c:s.toCharArray()){
				if(c==' ')
					if(inv.getItem(WorkbenchGUI.materials.get(index++))==null)
						continue;
					else 
						return false;
				else{
					ItemStack is=inv.getItem(WorkbenchGUI.materials.get(index++));
					if(is==null)return false;
					if(is.equals(materials.get(c)))
						continue;
					else 
						return false;
				}
			}
		return true;
	}

	public ItemStack getBlueprint(){
		ItemStack is=new ItemStack(Material.getMaterial(rpgct.getConfig().getString("workbench.blueprint.type")),
				1,(short)rpgct.getConfig().getInt("workbench.blueprint.damage"));
		ItemMeta im=is.getItemMeta();
		im.setDisplayName(rpgct.getConfig().getString("workbench.blueprint.name"));
		
		List<String> l=rpgct.getConfig().getStringList("workbench.blueprint.lore");
		l.set(0, l.get(0).replaceAll("&name", rpgct.getBlueprintTable("name")).replaceAll("%NAME%", name));
		l.set(1, l.get(1).replaceAll("&time", rpgct.getBlueprintTable("time")).replaceAll("%TIME%", time+""));
		if(l.get(2).contains("&times"))
			l.set(2, l.get(2).replaceAll("&times", rpgct.getBlueprintTable("times")));
		im.setLore(l);
		is.setItemMeta(im);
		return is;
	}
	
	public Inventory setInv(Inventory inv){
		short index=0;
		for(String s:shape)
			for(char c:s.toCharArray()){
				if(c==' '){
					index++;
					continue;
				}
				inv.setItem(WorkbenchGUI.materials.get(index++), materials.get(c));
			}
		inv.setItem(16, Items.createPItem((short)0, "§7§l讨厌啦~不要总是看着人家啦~"));
		index=0;
		while(index!=4)
			inv.setItem(WorkbenchGUI.products.get(index), product[index++]);
		return inv;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
