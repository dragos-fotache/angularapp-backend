package com.heroes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Article {

	private int id;
	private String name;
	private String supplier;
	private String pzn;
	private String packing;

	public Article(int id, String pzn, String name, String supplier, String packing) {
		this.id = id;
		this.pzn = pzn;
		this.name = name;
		this.supplier = supplier;
		this.packing = packing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPzn() {
		return pzn;
	}
	
	public void setPzn(String pzn) {
		this.pzn = pzn;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}
