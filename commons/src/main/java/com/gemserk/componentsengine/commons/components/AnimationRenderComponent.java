package com.gemserk.componentsengine.commons.components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.ReferencePropertyComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.render.RenderQueue;
import com.gemserk.componentsengine.slick.render.SlickImageRenderObject;
import com.gemserk.resources.Resource;

public class AnimationRenderComponent extends ReferencePropertyComponent {
	
	@EntityProperty
	Property<Vector2f> position;
	
	@EntityProperty
	Property<Vector2f> direction;
	
	@EntityProperty
	Property<Color> color;
	
	@EntityProperty
	Property<Vector2f> size;
	
	@EntityProperty
	Property<Integer> layer;
	
	@EntityProperty
	Property<Resource<Animation>> animation;
	
	public AnimationRenderComponent(String id) {
		super(id);
	}
	
	@Handles
	public void render(Message message) {
		RenderQueue renderQueue = Properties.getValue(message, "renderer");
		Image image = animation.get().get().getCurrentFrame();
		renderQueue.enqueue(new SlickImageRenderObject(layer.get(), image, position.get().x, position.get().y, size.get().x, size.get().y, (float) direction.get().getTheta(), color.get()));
	}
}