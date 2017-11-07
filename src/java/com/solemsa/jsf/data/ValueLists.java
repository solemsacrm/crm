package com.solemsa.jsf.data;

import java.util.ArrayList;
import java.util.List;

public class ValueLists {
    private List<ListValues> list;
    private boolean changes;
    private int length;
    private int display;
    private String lastEvent;
    
    public ValueLists() {
        this.list=new ArrayList();
        this.changes=true;
        this.length=0;
        this.display=0;
        this.lastEvent="";
    }

    public List<ListValues> getList() {
        return list;
    }

    public void setList(List<ListValues> list) {
        this.list = list;
    }

    public boolean isChanges() {
        return changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }
    
    public void add(Long id, String label)
    {
        this.list.add(new ListValues(id,label));
        length++;
    }
    
    public boolean disableFocusEvent(){
        return lastEvent.equals("click")||lastEvent.equals("focus");
    }
    
    public boolean disableBlurEvent(){
        return lastEvent.equals("focus");
    }
    
}
