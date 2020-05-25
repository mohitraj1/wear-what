package com.mohit.wearwhat.dressservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"pics"})
@Document(collection = "garments")
public class Garment {
	@Id
    private String id;
	private String name;
	private String brand;
	private String color;
	private String description;
	private String placeOfPurchase;
	private Double price;
	private String purchaseDate;
	private GarmentType type;
	private List<MultipartFile> pics;
	private List<String> images = new ArrayList<String>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlaceOfPurchase() {
		return placeOfPurchase;
	}
	public void setPlaceOfPurchase(String placeOfPurchase) {
		this.placeOfPurchase = placeOfPurchase;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public GarmentType getType() {
		return type;
	}
	public void setType(GarmentType type) {
		this.type = type;
	}
	public List<MultipartFile> getPics() {
		return pics;
	}
	public void setPics(List<MultipartFile> pics) {
		this.pics = pics;
	}
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	
}
