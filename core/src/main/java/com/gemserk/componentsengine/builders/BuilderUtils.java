package com.gemserk.componentsengine.builders;

import groovy.lang.Closure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.entities.Root;
import com.gemserk.componentsengine.messages.GenericMessage;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.messages.MessageQueue;
import com.gemserk.componentsengine.properties.PropertiesMapBuilder;
import com.gemserk.componentsengine.resources.AnimationManager;
import com.gemserk.componentsengine.resources.ImageManager;
import com.gemserk.componentsengine.resources.SoundsManager;
import com.gemserk.componentsengine.sounds.Sound;
import com.gemserk.componentsengine.utils.Container;
import com.gemserk.componentsengine.utils.Interval;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;

public class BuilderUtils {

	Map<String, Object> custom = new HashMap<String, Object>();

	@Inject
	ImageManager imageManager;

	@Inject
	AnimationManager animationManager;

	@Inject
	SoundsManager soundsManager;

	Random random = new Random();

	ResourceUtils resourceUtils = new ResourceUtils();

	public void addCustomUtil(String key, Object value) {
		custom.put(key, value);
	}

	public Vector2f vector(float x, float y) {
		return new Vector2f(x, y);
	}

	public Vector2f randomVector(Rectangle rectangle) {
		return new Vector2f(rectangle.getMinX() + random.nextFloat() * rectangle.getWidth(), rectangle.getMinY() + random.nextFloat() * rectangle.getHeight());
	}

	public Interval interval(int min, int max) {
		return new Interval(min, max);
	}

	public Rectangle rectangle(float x, float y, float width, float height) {
		return new Rectangle(x, y, width, height);
	}

	public Color color(float r, float g, float b, float a) {
		return new Color(r, g, b, a);
	}

	public Color color(float r, float g, float b) {
		return new Color(r, g, b);
	}

	public Container container(float current, float total) {
		return new Container(current, total);
	}

	public GenericMessage genericMessage(String id, Closure closure) {
		GenericMessage genericMessage = new GenericMessage(id);
		closure.call(genericMessage);
		return genericMessage;
	}

	public GenericMessage delayedMessage(int delay, Message delayedMessage) {
		GenericMessage genericMessage = new GenericMessage("delayedMessage", new PropertiesMapBuilder().property("delay", delay).property("message", delayedMessage).build());
		return genericMessage;
	}

	public ResourceUtils getResources() {
		return resourceUtils;
	}

	public class ResourceUtils {

		public Image image(String key) {
			return imageManager.getImage(key);
		}

		public Animation animation(String key) {
			return animationManager.getAnimation(key);
		}

		FontUtils fontUtils = new FontUtils();

		public FontUtils getFonts() {
			return fontUtils;
		}

		public class FontUtils {

			private Map<String, Font> cachedFonts = new HashMap<String, Font>();

			public Font font(Map<String, Object> parameters) {

				Boolean italic = (Boolean) (parameters.get("italic") != null ? parameters.get("italic") : false);
				Boolean bold = (Boolean) (parameters.get("bold") != null ? parameters.get("bold") : false);
				Integer size = (Integer) (parameters.get("size") != null ? parameters.get("size") : 12);

				return font(italic, bold, size);
			}

			public Font font(boolean italic, boolean bold, int size) {

				String key = "i_" + italic + ",b_" + bold + ",s_" + size;

				Font cachedFont = cachedFonts.get(key);
				if (cachedFont != null)
					return cachedFont;

				boolean antiAlias = true;

				String defaultType = java.awt.Font.SANS_SERIF;
				int defaultStyle = java.awt.Font.PLAIN;

				if (italic)
					defaultStyle |= java.awt.Font.ITALIC;

				if (bold)
					defaultStyle |= java.awt.Font.BOLD;

				TrueTypeFont newFont = new TrueTypeFont(new java.awt.Font(defaultType, defaultStyle, size), antiAlias);
				cachedFonts.put(key, newFont);
				return newFont;
			}

		}

		SoundUtils soundUtils = new SoundUtils();

		public SoundUtils getSounds() {
			return soundUtils;
		}

		public class SoundUtils {

			public Sound sound(String key) {
				return soundsManager.getSound(key);
			}

		}

	}

	public ComponentUtils getComponents() {
		return new ComponentUtils();
	}

	public class ComponentUtils {

		public Component genericComponent(final Map<String, Object> parameters, final Closure closure) {
			Object messageIdsCandidates = parameters.get("messageId");
			final Collection messageIds;
			if (messageIdsCandidates == null)
				throw new RuntimeException("messageId cant be null");

			if (messageIdsCandidates instanceof Collection) {
				messageIds = (Collection) messageIdsCandidates;
			} else {
				messageIds = Lists.newArrayList(messageIdsCandidates.toString());
			}

			return new Component((String) parameters.get("id")) {

				@Inject
				@Root
				Entity rootEntity;

				@Inject
				MessageQueue messageQueue;

				@Override
				public void handleMessage(Message message) {

					if (!messageIds.contains(message.getId()))
						return;

					closure.setDelegate(this);
					closure.call(message);
				}

			};
		}
	}

}