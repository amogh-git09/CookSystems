package com.cooksys.assessment;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemList {
	ArrayList<String> list;
	
	public ItemList(){
		list = new ArrayList<String>();
	}
	
	public ItemList(ArrayList<String> list){
		this.list = list;
	}
	
	public void addItem(String s){
		list.add(s);
	}
	
	public String removeItem(int index){
		if(index < 0 || index >= list.size())
			return null;
		return list.remove(index);
	}
	
	public Object[] toArray(){
		return list.toArray();
	}
	
	public String getItem(int index){
		if(index < 0 || index >= list.size())
			return null;
		return list.get(index);
	}
	
	@XmlElement(name = "Item")
	public ArrayList<String> getList(){
		return list;
	}
} 
