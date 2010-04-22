package com.gemserk.componentsengine.commons.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.newdawn.slick.opengl.SlickCallable;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.commons.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.effects.ExplosionEffect;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.SlickRenderMessage;
import com.gemserk.componentsengine.messages.UpdateMessage;
import com.gemserk.componentsengine.properties.Properties;

public class ExplosionComponent extends FieldsReflectionComponent {

	@EntityProperty(required = false)
	Collection<ExplosionEffect> explosions = new ArrayList<ExplosionEffect>();
	
	@EntityProperty(required = false, readOnly=true)
	String eventId = "explosion";

	public ExplosionComponent(String id) {
		super(id);
	}

	public void handleMessage(UpdateMessage message) {

		int delta = message.getDelta();

		Iterator<ExplosionEffect> iterator = explosions.iterator();
		while (iterator.hasNext()) {
			ExplosionEffect explosion = iterator.next();
			explosion.update(delta);

			if (explosion.isDone())
				iterator.remove();
		}

	}

	public void handleMessage(SlickRenderMessage message) {
		SlickCallable.enterSafeBlock();
		for (ExplosionEffect explosionEffect : explosions) {
			explosionEffect.render();
		}
		SlickCallable.leaveSafeBlock();
	}

	public void handleMessage(GenericMessage message) {

		if (message.getId().equals(eventId)) {
			ExplosionEffect explosion = Properties.getValue(message, eventId);
			explosions.add(explosion);
		}
	}

}