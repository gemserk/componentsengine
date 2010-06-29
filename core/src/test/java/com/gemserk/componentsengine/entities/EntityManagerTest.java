package com.gemserk.componentsengine.entities;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.hamcrest.collection.IsCollectionContaining;
import org.hamcrest.collection.IsMapContaining;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.EntityManager.EntityRegistrator;
import com.gemserk.componentsengine.messages.MessageDispatcher;
import com.google.common.collect.Lists;

@RunWith(JMock.class)
public class EntityManagerTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private MessageDispatcher messageDispatcher;
	private EntityManager entityManager;
	private EntityRegistrator entityRegistrator;

	// si el where es null, lo agrego en

	// 

	@Before
	public void setup() {
		messageDispatcher = mockery.mock(MessageDispatcher.class);

		entityManager = new EntityManager();
		entityManager.messageDispatcher = messageDispatcher;

		entityRegistrator = mockery.mock(EntityRegistrator.class);
		entityManager.entityRegistrator = entityRegistrator;

	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowRuntimeExceptionIfEntityWithSameIdAlreadyRegistered() {
		EntityRegistrator entityRegistrator = entityManager.new EntityRegistrator();
		String entityId = "ID";
		entityRegistrator.entities.put(entityId, new Entity(entityId));
		entityRegistrator.registerEntities(Lists.newArrayList(new Entity(entityId)));
	}

	@Test
	public void shouldRegisterEntityComponentsWhenRegisterEntity() {
		EntityRegistrator entityRegistrator = entityManager.new EntityRegistrator();

		Entity entity = new Entity("ID");

		final Component component1 = new Component("a");
		final Component component2 = new Component("b");

		entity.addComponent(component1);
		entity.addComponent(component2);

		mockery.checking(new Expectations() {
			{
				oneOf(messageDispatcher).registerComponent(component1);
				oneOf(messageDispatcher).registerComponent(component2);
			}
		});

		entityRegistrator.registerEntities(Lists.newArrayList(entity));
		assertThat(entityRegistrator.getEntityById("ID"), sameInstance(entity));
	}
	

	@Test
	public void shouldUnregisterEntityComponentsWhenEntityRemoved() {
		EntityRegistrator entityRegistrator = entityManager.new EntityRegistrator();
		
		final Component component1 = new Component("a");
		final Component component2 = new Component("b");

		Entity entity = new Entity("entity");

		entity.addComponent(component1);
		entity.addComponent(component2);

		entityRegistrator.entities.put(entity.getId(), entity);

		mockery.checking(new Expectations() {
			{
				oneOf(messageDispatcher).unregisterComponent(component1);
				oneOf(messageDispatcher).unregisterComponent(component2);
			}
		});

		assertThat(entityRegistrator.getEntityById("entity"), notNullValue());
		entityRegistrator.unregisterEntities(Lists.newArrayList(entity));
		assertThat(entityRegistrator.getEntityById("entity"), nullValue());
	}

	@Test
	public void shouldRegisterEntityWhenAddEntity() {
		final Entity entity = new Entity("entity");

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(null));
				oneOf(entityRegistrator).registerEntities(Lists.newArrayList(entity));
			}
		});

		entityManager.addEntity(entity);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldFailIfAddEntityAsChildrenOfNullEntityWhenAddEntity() {
		final Entity entity = new Entity("entity");
		final Entity parentEntity = new Entity("parentEntity");

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(null));
				oneOf(entityRegistrator).getEntityById(parentEntity.getId());
				will(returnValue(null));
			}
		});
		entityManager.addEntity(entity, parentEntity.getId());
	}

	@Test
	public void shouldAddEntityAsChildrenOfAnotherEntityWhenAddEntity() {
		final Entity entity = new Entity("entity");
		final Entity parentEntity = new Entity("parentEntity");

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(null));
				oneOf(entityRegistrator).registerEntities(Lists.newArrayList(entity));
				oneOf(entityRegistrator).getEntityById(parentEntity.getId());
				will(returnValue(parentEntity));
			}
		});

		entityManager.addEntity(entity, parentEntity.getId());
		
		assertThat(parentEntity.children, IsMapContaining.hasEntry(entity.getId(), entity));
	}
	
	@Test
	public void shouldUnregisterEntityWhenAddEntityIfSameEntityAlreadyRegistered() {
		final Entity entity = new Entity("entity");

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(entity));
				oneOf(entityRegistrator).unregisterEntities(Lists.newArrayList(entity));
				oneOf(entityRegistrator).registerEntities(Lists.newArrayList(entity));
			}
		});

		entityManager.addEntity(entity);
	}

	@Test
	public void shouldUnregisterEntityWhenAddEntityIfEntityWithSameIdAlreadyRegistered() {
		final Entity entity = new Entity("entity");
		final Entity oldEntity = new Entity("entity");

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(oldEntity));
				oneOf(entityRegistrator).unregisterEntities(Lists.newArrayList(oldEntity));
				oneOf(entityRegistrator).registerEntities(Lists.newArrayList(entity));
			}
		});

		entityManager.addEntity(entity);
		
	}
	
	@Test
	public void shouldUnregisterEntityWhenAddEntityIfEntityWithSameIdAlreadyRegisteredWithParent() {
		final Entity entity = new Entity("entity");
		final Entity parent = new Entity("parent");
		final Entity oldEntity = new Entity("entity");
		parent.addEntity(oldEntity);
		

		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entity.getId());
				will(returnValue(oldEntity));
				oneOf(entityRegistrator).unregisterEntities(Lists.newArrayList(oldEntity));
				oneOf(entityRegistrator).getEntityById(parent.getId());
				will(returnValue(parent));
				oneOf(entityRegistrator).registerEntities(Lists.newArrayList(entity));
			}
		});

		entityManager.addEntity(entity,parent.getId());
		
		assertThat(parent.children, IsMapContaining.hasEntry(entity.getId(), entity));
		assertThat(parent.children, not(IsMapContaining.hasEntry(oldEntity.getId(), oldEntity)));
	}
	
	// remove de entidad que no existe
	
	@Test
	public void shouldDoNothingIfEntityIsNotRegistered() {
		final String entityId = "ID";
		
		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entityId);
				will(returnValue(null));
			}
		});

		entityManager.removeEntity(entityId);
	}
	
	@Test
	public void shouldUnregisterEntityWhenRemove() {
		final String entityId = "ID";
		final Entity entity = new Entity("ID");
		
		mockery.checking(new Expectations() {
			{
				oneOf(entityRegistrator).getEntityById(entityId);
				will(returnValue(entity));
				oneOf(entityRegistrator).unregisterEntities(Lists.newArrayList(entity));
			}
		});

		entityManager.removeEntity(entityId);
	}

	// si remuevo una entidad, desregistro los componentes del message dispatcher


}
