package com.cooksys.assessment;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemList {
	//list
	ArrayList<String> list;
	
	//initialize empty list
	public ItemList(){
		list = new ArrayList<String>();
	}
	
	//initialize with list passed
	public ItemList(ArrayList<String> list){
		this.list = list;
	}
	
	//adds and item to the list
	public void addItem(String s){
		list.add(s);
	}
	
	//removes the element at specified index and returns it
	public String removeItem(int index){
		if(index < 0 || index >= list.size())
			return null;
		return list.remove(index);
	}
	
	//list to array
	public String[] toArray(){
		String[] tmp = new String[list.size()];
		return list.toArray(tmp);
	}
	
	//get item at specified index
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
