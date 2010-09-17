package com.gemserk.componentsengine.gamestates;

import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.render.Renderer;
import com.google.inject.Inject;

public class GameLoopImpl implements GameLoop {
	@Inject
	MessageQueue messageQueue;
	@Inject
	Renderer renderer;
	@Inject
	MonitorUpdater monitorUpdater;

	@Override
	public void render() {
		Message message = new Message("render");
		message.addProperty("renderer", new SimpleProperty<Object>(renderer));
		messageQueue.enqueue(message);
		messageQueue.processMessages();
		renderer.render();
	}

	@Override
	public void update(int delta) {
		monitorUpdater.update();
		Message message = new Message("update");
		message.addProperty("delta", new SimpleProperty<Object>(delta));
		messageQueue.enqueue(message);
		messageQueue.processMessages();
	}
}