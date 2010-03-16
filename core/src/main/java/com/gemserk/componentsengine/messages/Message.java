package com.gemserk.componentsengine.messages;


public class Message {
	
	boolean propagate = true;
	
	public void suspendPropagation(){
		propagate = false;
	}
	
	public void enablePropagation(){
		propagate = true;
	}
	public boolean shouldPropagate() {
		return propagate;
	}
}
