package com.outlook.schooluniformsama.data.recipe;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.outlook.schooluniformsama.data.Items;
import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.gui.WorkbenchGUI;

public class WorkbenchRecipe extends Recipe{
	private HashMap<Character,ItemStack> materials;
	private ItemStack[] product;
	
	/**
	 * Create a Workbench Recipe
	 * @param name Recipe's Name
	 * @param fileName Recipe's File
	 * @param type	
	 * @param needTime
	 * @param shape
	 * @param materials
	 * @param product
	 */
	public WorkbenchRecipe(String name,String fileName,int needTime,
			List<String> shape, HashMap<Character, ItemStack> materials,ItemStack[] product){
		super(name, fileName, WorkbenchType.WORKBENCH,shape,needTime);
		this.materials = materials;
		this.product = product;
	}
	
	public WorkbenchRecipe(String name,String fileName,int needTime){
		super(name, fileName, WorkbenchType.WORKBENCH,needTime);
	}
	
	public boolean save(){
		YamlConfiguration recipe;
		File f=new File(MainData.DATAFOLDER+File.separator+"recipe"+File.separator+"workbench"+File.separator+fileName+".yml");
			if(!f.exists())
				try {f.createNewFile();} catch (IOException e1) {return false;}
		recipe=YamlConfiguration.loadConfiguration(f);
		recipe.set(name+".tpye", type.name());
		recipe.set(name+".name", name);
		recipe.set(name+".fileName", fileName);
		recipe.set(name+".needTime", needTime);
		recipe.set(name+".shape", shape);
		for(Character c:materials.keySet())
			recipe.set(name+".material."+c,materials.get(c) );
		for(int i=0;i<4;i++)
			recipe.set(name+".product."+i, product[i]);
		try {recipe.save(f);} catch (IOException e) {return false;}
		return true;
	}
	
	/**
	 * Load a recipe from a file
	 * @param name
	 * @param fileName
	 * @return
	 */
	public static WorkbenchRecipe load(String name,String fileName){
		 File f=new File(MainData.DATAFOLDER+File.separator+"recipe"+File.separator+"workbench"+File.separator+fileName+".yml");
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
		 return new WorkbenchRecipe(name, fileName, recipe.getInt(name+".needTime"), recipe.getStringList(name+".shape"), materials, product);
	}
	
	/**
	 * Create a Workbench's Recipe
	 * @param inv
	 * @return
	 */
	public static boolean createRecipe(Inventory inv,WorkbenchRecipe r){
		short index=0;
		char charArray[]="ABCDEFGHIJKLMNOP".toCharArray();
		String s="";
		HashMap<ItemStack,Character> m=new HashMap<>();
		ItemStack is=inv.getItem(WorkbenchGUI.materials.get(0));
		if(new File(MainData.DATAFOLDER+File.separator+"recipe"+File.separator+"workbench"+File.separator+r.fileName+".yml").exists())
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
		
		WorkbenchRecipe wr=new WorkbenchRecipe(r.name, r.fileName,r.needTime, 
				Arrays.asList(s.substring(0,4),s.substring(4, 8),s.substring(8,12),s.substring(12,16)),m2, 
				new ItemStack[]{inv.getItem(WorkbenchGUI.products.get(0)),inv.getItem(
						WorkbenchGUI.products.get(1)),inv.getItem(WorkbenchGUI.products.get(2)),
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
		inv.setItem(16, Items.createPItem((short)0, " "));
		index=0;
		while(index!=4)
			inv.setItem(WorkbenchGUI.products.get(index), product[index++]);
		return inv;
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
