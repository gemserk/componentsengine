package com.gemserk.componentsengine.components;

import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;

@RunWith(JMock.class)
public class ReflectionComponentTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private ElMock elmock;

	public static interface ElMock {
		void update();

		void hitdetected();

		void both();

	}

	@Before
	public void setup() {
		elmock = mockery.mock(ElMock.class);
	}

	public static class TestComponent extends ReflectionComponent {

		private final ElMock elmock;

		public TestComponent(ElMock elmock) {
			super("test");
			this.elmock = elmock;
		}

		public void shouldNotBeCalled() {
			fail("this method should not be called");
		}

		@Handles
		public void update(Message message) {
			elmock.update();
		}

		@Handles(ids = { "hitdetected" })
		public void handler1(Message message) {
			elmock.hitdetected();
		}

		@Handles(ids = { "both1", "both2" })
		public void both(Message message) {
			elmock.both();
		}

	}

	@Test(expected = RuntimeException.class)
	public void youtCantDefineAHandlerWithoutMessageParameter() {
		Entity entity = new Entity("hola");
		Component shouldFailComponent = new ReflectionComponent("hola") {
			@Handles
			public void update() {
				fail("Missing parameter");
			}
		};
		entity.addComponent(shouldFailComponent);
		shouldFailComponent.handleMessage(new Message("update"));
	}

	@Test(expected = RuntimeException.class)
	public void youtCantDefineAHandlerWithTwoParameters() {
		Entity entity = new Entity("hola");
		Component shouldFailComponent = new ReflectionComponent("hola") {
			@Handles
			public void updateTwoParameters(Message a, int b) {
				fail("Missing parameter");
			}
		};
		entity.addComponent(shouldFailComponent);
		shouldFailComponent.handleMessage(new Message("updateTwoParameters"));
	}

	@Test(expected = RuntimeException.class)
	public void youtCantDefineAHandlerWithParameterNotMessage() {
		Entity entity = new Entity("hola");
		Component shouldFailComponent = new ReflectionComponent("hola") {
			@Handles
			public void updateTwoParameters(int a) {
				fail("Missing parameter");
			}
		};
		entity.addComponent(shouldFailComponent);
		shouldFailComponent.handleMessage(new Message("updateWrongType"));
	}
	
	
	@Test(expected = RuntimeException.class)
	public void youCantDefineSameIdInTwoParametersOneIsMethodName() {
		Entity entity = new Entity("hola");
		Component shouldFailComponent = new ReflectionComponent("hola") {

			@Handles(ids = { "update" })
			public void handler1(Message message) {
				elmock.hitdetected();
			}
			@Handles
			public void update(Message message) {
				elmock.update();
			}
		};
		entity.addComponent(shouldFailComponent);
		shouldFailComponent.handleMessage(new Message("update"));
	}
	
	@Test(expected = RuntimeException.class)
	public void youCantDefineSameIdInTwoParametersBothInListOfIds() {
		Entity entity = new Entity("hola");
		Component shouldFailComponent = new ReflectionComponent("hola") {
			@Handles(ids = {"realupdate"})
			public void update(Message message) {
				elmock.update();
			}

			@Handles(ids = {"realupdate"})
			public void handler1(Message message) {
				elmock.hitdetected();
			}
		};
		entity.addComponent(shouldFailComponent);
		shouldFailComponent.handleMessage(new Message("realupdate"));
	}
	
	

	@Test
	public void ifTheMessageIdIsNotInTheComponentNoMethodIsCalled() {
		Entity entity = new Entity("hola");
		Component component = new TestComponent(elmock);
		entity.addComponent(component);
		component.handleMessage(new Message("isnotincomponetnt"));
	}

	@Test
	public void ifTheMessageIdIsNotInTheAnnotationTheMethodNamedAsTheIdIsCalled() {
		Entity entity = new Entity("hola");
		Component component = new TestComponent(elmock);

		mockery.checking(new Expectations() {
			{
				oneOf(elmock).update();
			}
		});

		entity.addComponent(component);
		component.handleMessage(new Message("update"));
	}

	@Test
	public void ifTheMessageIdIsInTheAnnotationTheMethodIsCalled() {
		Entity entity = new Entity("hola");
		Component component = new TestComponent(elmock);

		mockery.checking(new Expectations() {
			{
				oneOf(elmock).hitdetected();
			}
		});

		entity.addComponent(component);
		component.handleMessage(new Message("hitdetected"));
	}

	@Test
	public void ifAMethodHasTwoMEssageIdItIsCalledForEachOne() {
		Entity entity = new Entity("hola");
		Component component = new TestComponent(elmock);

		mockery.checking(new Expectations() {
			{
				exactly(2).of(elmock).both();
			}
		});

		entity.addComponent(component);
		component.handleMessage(new Message("both1"));
		component.handleMessage(new Message("both2"));
	}

}
