package com.gemserk.componentsengine.commons.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.newdawn.slick.opengl.SlickCallable;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.effects.ExplosionEffect;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class ExplosionComponent extends FieldsReflectionComponent {

	@EntityProperty(required = false)
	Collection<ExplosionEffect> explosions = new ArrayList<ExplosionEffect>();

	public ExplosionComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer) Properties.getValue(message, "delta");

		Iterator<ExplosionEffect> iterator = explosions.iterator();
		while (iterator.hasNext()) {
			ExplosionEffect explosion = iterator.next();
			explosion.update(delta);

			if (explosion.isDone())
				iterator.remove();
		}

	}

	@Handles
	public void render(Message message) {
		SlickCallable.enterSafeBlock();
		for (ExplosionEffect explosionEffect : explosions) {
			explosionEffect.render();
		}
		SlickCallable.leaveSafeBlock();
	}

	@Handles
	public void explosion(Message message) {
		ExplosionEffect explosion = Properties.getValue(message, "explosion");
		explosions.add(explosion);
	}

}