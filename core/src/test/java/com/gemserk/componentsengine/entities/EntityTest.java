package com.gemserk.componentsengine.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ComponentsHolderImpl;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;

@RunWith(JMock.class)
public class EntityTest {
	Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void theEntityHasAnId() {
		String entityId = "entidad";
		Entity entity = new Entity(entityId);
		assertThat(entity.getId(), equalTo(entityId));
	}

	@Test
	public void canAddAComponent() {
		Entity entity = new Entity("id");

		String nombreComponente = "componente1";
		Component component1 = new Component(nombreComponente) {

			@Override
			public void handleMessage(Message message) {
				throw new UnsupportedOperationException("Not implemented");
			}

		};

		entity.addComponent(component1);

		Component componenteObtenido = entity.findComponentByName(nombreComponente);

		assertThat(componenteObtenido, sameInstance(component1));

	}

	@Test
	public void testCallOnAddWhenAddComponent() {
		final Entity entity = new Entity("id");

		final String component1name = "component1";
		final Component component1 = context.mock(Component.class, "component1");

		context.checking(new Expectations() {
			{
				oneOf(component1).getId();
				will(returnValue(component1name));
				oneOf(component1).onAdd(entity);
			}
		});

		entity.addComponent(component1);
	}

	@Test
	public void whenReceivingAMessageItIsPassedToTheComponentsForHandling() {

		final Entity entity = new Entity("id");

		final String component1name = "component1";
		final String component2name = "component2";
		final Component component1 = context.mock(Component.class, "component1");
		final Component component2 = context.mock(Component.class, "component2");

		final Message message = context.mock(Message.class);

		ComponentsHolderImpl componentsHolderImpl = new ComponentsHolderImpl();
		componentsHolderImpl.getComponents().put(component1name, component1);
		componentsHolderImpl.getComponents().put(component2name, component2);

		entity.componentsHolder = componentsHolderImpl;

		context.checking(new Expectations() {
			{
				ignoring(component1).getId();
				ignoring(component2).getId();
				oneOf(component1).handleMessage(message);
				oneOf(component2).handleMessage(message);
			}
		});

		entity.handleMessage(message);
	}

}
