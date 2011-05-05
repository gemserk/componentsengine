package com.gemserk.componentsengine.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.components.Component;
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
	public void shouldNotRemoveFromParentIfRootNode() {
		Entity entity = new Entity("");
		entity.parent = null;
		entity.removeFromParent();
	}

	@Test
	public void shouldRemoveFromParentIfNotRootNode() {
		final Entity parentEntity = context.mock(Entity.class);
		
		final Entity entity = new Entity("");
		entity.parent = parentEntity;
		
		context.checking(new Expectations() {
			{
				oneOf(parentEntity).removeEntity(entity);
			}
		});
		
		entity.removeFromParent();		
	}

}
