package com.gemserk.componentsengine.game;

import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.google.inject.Inject;

public class MessageReusingGameLoop implements GameLoop {
	
	@Inject
	MessageQueue messageQueue;
	
	@Inject
	RenderQueueImpl renderQueueImpl;
	
	@Inject
	MonitorUpdater monitorUpdater;

	Message renderMessage = new Message("render");
	
	SimpleProperty<Object> renderQueueProperty = new SimpleProperty<Object>();
	
	Message updateMessage = new Message("update");
	
	SimpleProperty<Object> deltaProperty = new SimpleProperty<Object>();
	
	public MessageReusingGameLoop() {
		renderMessage.addProperty("renderer", renderQueueProperty);
		updateMessage.addProperty("delta", deltaProperty);
	}
	
	@Override
	public void render() {
		renderQueueProperty.set(renderQueueImpl);
		messageQueue.enqueue(renderMessage);
		messageQueue.processMessages();
		renderQueueImpl.render();
	}

	@Override
	public void update(int delta) {
		monitorUpdater.update();
		deltaProperty.set(delta);
		messageQueue.enqueue(updateMessage);
		messageQueue.processMessages();
	}
}
