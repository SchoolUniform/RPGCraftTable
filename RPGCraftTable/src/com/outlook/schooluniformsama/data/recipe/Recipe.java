package com.outlook.schooluniformsama.data.recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	protected int needTime;
	protected String name;
	protected String fileName;
	protected List<String> shape=new ArrayList<>();
	protected WorkbenchType type;
	
	Recipe(String name,String fileName,WorkbenchType type,List<String> shape,int needTime){
		this.name=name;
		this.fileName=fileName;
		this.type=type;
		this.shape=shape;
		this.needTime=needTime;
	}
	
	Recipe(String name,String fileName,WorkbenchType type,int needTime){
		this.name=name;
		this.fileName=fileName;
		this.type=type;
		this.needTime=needTime;
	}

	public int getNeedTime() {
		return needTime;
	}

	public void setNeedTime(int needTime) {
		this.needTime = needTime;
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
	
}
