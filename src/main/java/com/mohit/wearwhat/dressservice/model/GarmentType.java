package com.mohit.wearwhat.dressservice.model;

public enum GarmentType {

	Shirt(1),
	Trouser(2);

	private int type;

	GarmentType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
