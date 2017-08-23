package com.outlook.schooluniformsama.task;


import com.outlook.schooluniformsama.data.MainData;
import com.outlook.schooluniformsama.data.wbtimer.FurnaceTimer;
import com.outlook.schooluniformsama.data.wbtimer.TableTimer;
import com.outlook.schooluniformsama.data.wbtimer.WorkbenchTimer;

public class CraftTablesTimer implements Runnable{

	@Override
	public void run() {
		
		for(TableTimer tt:MainData.WORKBENCH.values()){
			switch(tt.getType()){
				case FURNACE:
					((FurnaceTimer)tt).subTime();
					break;
				case WORKBENCH:
					((WorkbenchTimer)tt).subTime();
					break;
			}
		}
	}
}
