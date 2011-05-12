package com.gemserk.componentsengine.game;

import com.gemserk.componentsengine.input.MonitorUpdater;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.messages.messagebuilder.MessageBuilder;
import com.gemserk.componentsengine.render.RenderQueueImpl;
import com.google.inject.Inject;

public class GameLoopImpl implements GameLoop {
	@Inject
	MessageQueue messageQueue;
	@Inject
	RenderQueueImpl renderQueueImpl;
	@Inject
	MonitorUpdater monitorUpdater;

	@Inject MessageBuilder messageBuilder;
	
	@Override
	public void render() {
		Message message = messageBuilder.newMessage("render").property("renderer", renderQueueImpl).get();
		messageQueue.enqueue(message);
		messageQueue.processMessages();
		renderQueueImpl.render();
	}

	@Override
	public void update(int delta) {
		monitorUpdater.update();
		Message message = messageBuilder.newMessage("update").property("delta", delta).get();
		messageQueue.enqueue(message);
		messageQueue.processMessages();
	}
}