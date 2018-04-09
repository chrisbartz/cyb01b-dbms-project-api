package com.cyb01b.dbmsprojectapi;

import java.util.ArrayList;

public class Item {
	private int itemId;
	private String name;
	private String description;
	private double cost;
	private ArrayList<Picture> pictures = new ArrayList<Picture>();
	
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public ArrayList<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(ArrayList<Picture> pictures) {
		this.pictures = pictures;
	}
	
}
