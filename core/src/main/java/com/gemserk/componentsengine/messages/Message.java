package com.gemserk.componentsengine.messages;


public class Message {
	
	boolean propagate = true;
	boolean process = true;
	
	public void suspendPropagation(){
		propagate = false;
	}
	
	public void enablePropagation(){
		propagate = true;
	}
	public boolean shouldPropagate() {
		return process && propagate;
	}
	
	public void suspendProcessing(){
		process = false;
	}
	
	public void enableProcessing(){
		process = true;
	}
	public boolean shouldProcess() {
		return process;
	}
	
}
