package com.outlook.schooluniformsama.data.recipe;

public enum WorkbenchType {
	WORKBENCH("WORKBENCH"),FURNACE("FURNACE");

    private String tableID ;

    private WorkbenchType(String tableID) {
       this.tableID=tableID;
    }

    @Override
    public String toString() {
        return tableID;
    }
}
