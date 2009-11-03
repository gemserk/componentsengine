package com.gemserk.componentsengine.messages;


public class UpdateMessage extends Message {

	int delta;

	public UpdateMessage(int delta) {
		this.delta = delta;
	}

	public int getDelta() {
		return delta;
	}

}
