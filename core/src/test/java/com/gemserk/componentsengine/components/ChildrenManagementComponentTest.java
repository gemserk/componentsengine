package com.gemserk.componentsengine.components;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.AddEntityMessage;
import com.gemserk.componentsengine.messages.RemoveEntityMessage;

@RunWith(JMock.class)
public class ChildrenManagementComponentTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldRemoveEntityFromEntityWhenRemoveEntityMessage() {
		final Entity rootEntity = mockery.mock(Entity.class, "rootEntity");
		final Entity childEntity = mockery.mock(Entity.class, "childEntity");
		final String childName = "childName";

		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("");
		childrenManagementComponent.setEntity(rootEntity);

		mockery.checking(new Expectations() {
			{
				oneOf(rootEntity).getEntityById(childName);
				will(returnValue(childEntity));
				oneOf(childEntity).removeFromParent();
			}
		});

		childrenManagementComponent.handleMessage(new RemoveEntityMessage(childName));
	}

	@Test
	public void shouldNotToRemoveWhenChildNotFound() {
		final Entity rootEntity = mockery.mock(Entity.class, "rootEntity");
		final String childName = "childName";

		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("");
		childrenManagementComponent.setEntity(rootEntity);

		mockery.checking(new Expectations() {
			{
				oneOf(rootEntity).getEntityById(childName);
				will(returnValue(null));
			}
		});

		childrenManagementComponent.handleMessage(new RemoveEntityMessage(childName));
	}

	// test: remove rootEntity? root entity doesn't have root entity as child!!

	@Test
	public void shouldAddEntityToEntityWhenAddEntityMessage() {
		final Entity rootEntity = mockery.mock(Entity.class, "rootEntityMock");
		final Entity childEntity = mockery.mock(Entity.class, "childEntityMock");
		final Entity entity = new Entity("");
		final String whereEntityId = "targetEntity";

		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("");
		childrenManagementComponent.setEntity(rootEntity);

		mockery.checking(new Expectations() {
			{
				oneOf(rootEntity).getId();
				will(returnValue("root"));
				oneOf(rootEntity).getEntityById(whereEntityId);
				will(returnValue(childEntity));
				oneOf(childEntity).addEntity(entity);
			}
		});

		childrenManagementComponent.handleMessage(new AddEntityMessage(entity, whereEntityId));
	}

	@Test
	public void shouldReturnOwnerEntityWhenItIsTheExpectedWhereEntity() {
		final Entity rootEntity = mockery.mock(Entity.class, "rootEntityMock");
		final Entity entity = new Entity("");

		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("");
		childrenManagementComponent.setEntity(rootEntity);

		mockery.checking(new Expectations() {
			{
				oneOf(rootEntity).getId();
				will(returnValue("root"));
				oneOf(rootEntity).addEntity(entity);
			}
		});

		childrenManagementComponent.handleMessage(new AddEntityMessage(entity, "root"));
	}

	@Test
	public void shouldNotToAddEntityWhenTargetEntityIsNull() {
		final Entity rootEntity = mockery.mock(Entity.class, "rootEntityMock");
		final Entity entity = new Entity("");
		final String targetEntityId = "targetEntity";

		ChildrenManagementComponent childrenManagementComponent = new ChildrenManagementComponent("");
		childrenManagementComponent.setEntity(rootEntity);

		mockery.checking(new Expectations() {
			{
				oneOf(rootEntity).getId();
				will(returnValue("root"));
				oneOf(rootEntity).getEntityById(targetEntityId);
				will(returnValue(null));
			}
		});

		childrenManagementComponent.handleMessage(new AddEntityMessage(entity, targetEntityId));
	}

}
