package com.gemserk.componentsengine.properties;

public class Property<T extends Object> {

	T value;
	
	public Property() {
	}
	
	public Property(T value){
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "PROP: " + value.toString();
	}
}
