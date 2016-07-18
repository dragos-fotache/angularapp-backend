package com.heroes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Article {

	private Integer id;
	private String pzn;
	private String name;
	private String provider;
	private String dosage;
	private String packaging;
	private Double sellingPrice;
	private Double purchasingPrice;
	private Boolean narcotic;
	private Integer stock;
	
	public Article(int id, String pzn, String name, String provider, String dosage, String packaging,
			Double sellingPrice, Double purchasingPrice, Boolean narcotic, Integer stock) {
		this.id = id;
		this.pzn = pzn;
		this.name = name;
		this.provider = provider;
		this.dosage = dosage;
		this.packaging = packaging;
		this.sellingPrice = sellingPrice;
		this.purchasingPrice = purchasingPrice;
		this.narcotic = narcotic;
		this.stock = stock;
	}
	
	public Article() {
		
	}
	
	public String getPzn() {
		return pzn;
	}

	public void setPzn(String pzn) {
		this.pzn = pzn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(Double purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public Boolean getNarcotic() {
		return narcotic;
	}

	public void setNarcotic(Boolean narcotic) {
		this.narcotic = narcotic;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
