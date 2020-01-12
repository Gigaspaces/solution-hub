package com.gigaspaces;

import java.util.*;

public class TimeSeriesTypes {

    //key alias type name, value TimeSeriesType that describe internal type name and properties
    private Map<String, TimeSeriesType> types = new HashMap<>();

    public TimeSeriesTypes() {
    }



    protected Map<String, TimeSeriesType> getTypes() {
        return types;
    }

    protected void setTypes(Map<String, TimeSeriesType> types) {
        this.types = types;
    }

    public Set<String> getDisplayTypeNames(){
        return getTypes().keySet();

    }

    @Override
    public String toString() {
        return "TimeSeriesTypes{" +
                "types=" + getDisplayTypeNames() + " values=" + getTypes().values() +
                '}';
    }

    public void put(String name, TimeSeriesType type){
        getTypes().put(name,type );
    }

    public TimeSeriesType get(String name){
        TimeSeriesType type =  getTypes().get(name);
        if (type != null)
            return type;
        Iterator itr = types.keySet().iterator();
        while (itr.hasNext()){
            String typeName = (String)itr.next();
            if (typeName.contains(name))
                return types.get(typeName);
            if (name.contains(typeName))
                return types.get(typeName);
            if (name.equalsIgnoreCase(typeName))
                return types.get(typeName);
        }
        System.out.println("DID not find:" + name + " table has:" + getTypes().keySet().size());
        return null;
    }




}
