package com.springreact.springreact;

public class User {
	private String name;
	private String info;


	public User() {
		
	}
	
	public User(String name, String info) {
		this.name = name;
		this.info = info;
		
	}

	public String getName() {
		return name;
	}

	
	public String getInfo() {
		return info;
	}

	@Override
	public String toString() {
		
		return "User(" + name + ", " + info + ")";
	}
	
	

	
}