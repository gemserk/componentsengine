package com.gemserk.componentsengine.components;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class ReferencePropertyComponentTest {

	private static final String COMPONENT_NAME = "component";
	private static final String TESTVALUE1 = "testValue1";
	private static final String TESTVALUE2 = "testValue2";
	private static final String PROPNAME = "property";
	private static final String SCOPED_PROPNAME = COMPONENT_NAME + "." + PROPNAME;
	
	class TestComponent extends ReferencePropertyComponent {

		public String obtainedValue = null;
		
		
		@EntityProperty
		Property<String> property;
		
		
		public TestComponent(String id) {
			super(id);
		}
		
		
		@Handles public void update(Message message){
			obtainedValue = property.get();
		}
		
	}
	
	
	TestComponent component;
	Message message;
	Entity entity = new Entity("entity");
	

	@Before
	public void setUp() {
		component = new TestComponent(COMPONENT_NAME);
		message = new Message("update");
		entity.addComponent(component);
	}

	@Test
	public void ifthescopednameispresentitshouldresolvethescopedproperty() {
		
		entity.addProperty(SCOPED_PROPNAME, new SimpleProperty<Object>(TESTVALUE1));
		component.handleMessage(message);
		Assert.assertEquals(TESTVALUE1, component.obtainedValue);
	}

	@Test
	public void ifBothTheScopedPropertyAndThePlainPropertyArePresentItShouldReturnTheScopedOne() {
		entity.addProperty(SCOPED_PROPNAME, new SimpleProperty<Object>(TESTVALUE1));
		entity.addProperty(PROPNAME, new SimpleProperty<Object>(TESTVALUE2));
		component.handleMessage(message);
		Assert.assertEquals(TESTVALUE1, component.obtainedValue);
	}

	@Test(expected = RuntimeException.class)
	public void ifNeitherTheScopedPropertyOrThePlainOneArePresentItShouldFail() {
		component.handleMessage(message);
	}
	
	@Test
	public void ifTheScopedPropertyIsNotPresentButThePlainOneIsItShouldReturnThePlainOne() {
		entity.addProperty(PROPNAME, new SimpleProperty<Object>(TESTVALUE2));
		component.handleMessage(message);
		Assert.assertEquals(TESTVALUE2, component.obtainedValue);
	}

}
