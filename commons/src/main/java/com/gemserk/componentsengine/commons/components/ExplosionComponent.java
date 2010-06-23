package com.gemserk.componentsengine.commons.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.opengl.SlickCallable;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.effects.ExplosionEffect;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.render.Renderer;
import com.gemserk.componentsengine.render.SlickCallableRenderObject;

public class ExplosionComponent extends FieldsReflectionComponent {

	@EntityProperty(required = false)
	Collection<Effect> explosions = new ArrayList<Effect>();

	class Effect {

		private final int layer;

		private final ExplosionEffect explosionEffect;

		public Effect(int layer, ExplosionEffect explosionEffect) {
			this.layer = layer;
			this.explosionEffect = explosionEffect;
		}

	}

	public ExplosionComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		int delta = (Integer) Properties.getValue(message, "delta");

		Iterator<Effect> iterator = explosions.iterator();
		while (iterator.hasNext()) {
			Effect explosion = iterator.next();
			explosion.explosionEffect.update(delta);

			if (explosion.explosionEffect.isDone())
				iterator.remove();
		}

	}

	@Handles
	public void render(Message message) {
		Renderer renderer = Properties.getValue(message, "renderer");

		for (Effect effect : explosions) {
			final ExplosionEffect explosionEffect = effect.explosionEffect;
			renderer.enqueue(new SlickCallableRenderObject(effect.layer) {

				@Override
				public void execute(Graphics g) {
					SlickCallable.enterSafeBlock();
					explosionEffect.render();
					SlickCallable.leaveSafeBlock();
				}
			});
		}

	}

	@Handles
	public void explosion(Message message) {
		ExplosionEffect explosion = Properties.getValue(message, "explosion");
		int layer = (Integer) Properties.property("layer").getValue(message, 0);
		explosions.add(new Effect(layer, explosion));
	}

}