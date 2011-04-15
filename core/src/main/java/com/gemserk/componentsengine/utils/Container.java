package com.gemserk.componentsengine.utils;

public class Container {

	private float current;

	private float total;

	public void setCurrent(float current) {
		if (current > total)
			current = total;
		if (current < 0f)
			current = 0f;
		this.current = current;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getCurrent() {
		return current;
	}

	public float getTotal() {
		return total;
	}

	public float getPercentage() {
		return current / total;
	}

	public Container() {
		current = total = 0;
	}

	public Container(float current, float total) {
		super();
		this.current = current;
		this.total = total;
	}

	public boolean isFull() {
		return current == total;
	}

	public boolean isEmpty() {
		return current == 0;
	}

	public void remove(float value) {
		current -= value;
		if (current < 0)
			current = 0;
	}

	public void add(float value) {
		current += value;
		if (current > total)
			current = total;
	}

	@Override
	public String toString() {
		return "[" + getCurrent() + "/" + getTotal() + "]";
	}

}
