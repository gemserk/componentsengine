package com.gemserk.componentsengine.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.ReferenceProperty;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapper;
import com.gemserk.componentsengine.reflection.wrapper.ComponentPropertiesWrapperImpl;

public class ReflectionTest {

	protected static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	public static class MyComponent extends Component {

		@EntityProperty
		public String name;

		@EntityProperty(required = false)
		public Integer otherProperty;

		@EntityProperty
		public Float delta;

		public String internalProperty;

		public MyComponent(String id) {
			super(id);
		}

	}

	public class AnotherComponent extends Component {

		@EntityProperty
		private Integer intValue;

		public void setIntValue(Integer intValue) {
			this.intValue = intValue;
		}

		public Integer getIntValue() {
			return intValue;
		}

		public AnotherComponent(String id) {
			super(id);
		}

	}
	
	@Before
	public void configure() {
//		ComponentPropertiesWrapperImpl.useFastClassIfPossible = false;
	}

	@Test
	public void shouldSetEntityPropertiesFromComponentFields() {
		AnotherComponent component = new AnotherComponent("another");
		component.intValue = 200;

		Entity entity = new Entity("entity");
		entity.addProperty("another.intValue", new SimpleProperty<Object>(100));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(AnotherComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);
		assertEquals(Integer.valueOf(100), component.intValue);

		component.intValue = 200;
		componentPropertyWrapperImpl.exportTo(component, entity);
		assertEquals(200, entity.getProperty("another.intValue").get());
	}

	@Test(expected = RequiredPropertyNotFoundException.class)
	public void shouldFailIfRequiredPropertyNotFound() {
		MyComponent component = new MyComponent("myComp");

		Entity entity = new Entity("entity");
		entity.addProperty("name", new SimpleProperty<Object>("myName"));
		entity.addProperty("myComp.name", new ReferenceProperty<Object>("name", entity));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(MyComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);

		assertEquals("myName", component.name);
	}

	@Test(expected = RuntimeException.class)
	public void shouldFailIfPropertyTypeNotMatch() {
		MyComponent component = new MyComponent("myComp");

		Entity entity = new Entity("entity");
		entity.addProperty("name", new SimpleProperty<Object>("myName"));
		entity.addProperty("myComp.name", new ReferenceProperty<Object>("name", entity));
		entity.addProperty("myComp.delta", new ReferenceProperty<Object>("name", entity));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(MyComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);

		assertEquals("myName", component.name);
	}

	@Test
	public void shouldSetComponentFieldsFromEntityProperties() {
		MyComponent component = new MyComponent("myComp");

		Entity entity = new Entity("entity");
		entity.addProperty("name", new SimpleProperty<Object>("myName"));
		entity.addProperty("delta", new SimpleProperty<Object>(100.0f));
		entity.addProperty("myComp.name", new ReferenceProperty<Object>("name", entity));
		entity.addProperty("myComp.delta", new ReferenceProperty<Object>("delta", entity));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(MyComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);

		assertEquals("myName", component.name);
		assertEquals(100.0f, component.delta, 0.01f);
	}

	@Test
	public void shouldNotSetNotAnnotatedComponentField() {
		MyComponent component = new MyComponent("myComp");
		component.internalProperty = "hola";

		Entity entity = new Entity("entity");
		entity.addProperty("name", new SimpleProperty<Object>("myName"));
		entity.addProperty("delta", new SimpleProperty<Object>(100.0f));
		entity.addProperty("myComp.name", new ReferenceProperty<Object>("name", entity));
		entity.addProperty("myComp.delta", new ReferenceProperty<Object>("delta", entity));
		entity.addProperty("myComp.internalProperty", new SimpleProperty<Object>("chau"));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(MyComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);

		assertEquals("hola", component.internalProperty);
	}
	
	public class ReadOnlyComponent extends Component {

		@EntityProperty(readOnly=true)
		private Integer intValue;

		public void setIntValue(Integer intValue) {
			this.intValue = intValue;
		}

		public Integer getIntValue() {
			return intValue;
		}

		public ReadOnlyComponent(String id) {
			super(id);
		}

	}
	
	@Test
	public void shouldNotExportReadOnlyProperty() {
		ReadOnlyComponent component = new ReadOnlyComponent("readOnlyComponent");
		component.setIntValue(100);

		Entity entity = new Entity("entity");
		entity.addProperty("readOnlyComponent.intValue", new SimpleProperty<Object>(200));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(ReadOnlyComponent.class);
		componentPropertyWrapperImpl.exportTo(component, entity);

		assertEquals(Integer.valueOf(200), entity.getProperty("readOnlyComponent.intValue").get());
	}

	public class CheckWrapperTimeComponent extends Component {

		@EntityProperty
		private String value;

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public CheckWrapperTimeComponent(String id) {
			super(id);
		}

	}

	@Test
	public void testTimesPrivateFieldWithGetterSetterMethods() {
		CheckWrapperTimeComponent component = new CheckWrapperTimeComponent("another");
		component.value = "internal";

		Entity entity = new Entity("entity");
		entity.addProperty("another.value", new SimpleProperty<Object>("otherValue"));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(CheckWrapperTimeComponent.class);

		// TODO: make an average of 100 excecutions
		
		long time = System.nanoTime();
		componentPropertyWrapperImpl.importFrom(component, entity);
		componentPropertyWrapperImpl.exportTo(component, entity);
		long wrapperTime = System.nanoTime() - time;		

		time = System.nanoTime();
		Property<Object> property = entity.getProperty("another.value");
		component.value = ((String) property.get());
		property.set(component.value);		
		long directAccessTime = System.nanoTime() - time;		

		float proporcion = (float)wrapperTime / (float)directAccessTime;
		
		logger.info("wrapper time: {}, direct access time: {}", new Object[] {wrapperTime, directAccessTime});
		logger.info("wrapper time =(aprox) {} x direct access time", new Object[] {proporcion});
	}
	
	public class CheckWrapperTimeComponent2 extends Component {

		@EntityProperty
		private String value;

		public CheckWrapperTimeComponent2(String id) {
			super(id);
		}

	}

	@Test
	public void testTimesPrivateFieldWithoutGetterSetterMethods() {
		
		CheckWrapperTimeComponent2 component = new CheckWrapperTimeComponent2("another");
		component.value = "internal";

		Entity entity = new Entity("entity");
		entity.addProperty("another.value", new SimpleProperty<Object>("otherValue"));

		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(CheckWrapperTimeComponent2.class);

		// TODO: make an average of 100 excecutions
		
		long time = System.nanoTime();
		componentPropertyWrapperImpl.importFrom(component, entity);
		componentPropertyWrapperImpl.exportTo(component, entity);
		long wrapperTime = System.nanoTime() - time;		

		time = System.nanoTime();
		Property<Object> property = entity.getProperty("another.value");
		component.value = ((String) property.get());
		property.set(component.value);		
		long directAccessTime = System.nanoTime() - time;		

		float proporcion = (float)wrapperTime / (float)directAccessTime;
		
		logger.info("wrapper time: {}, direct access time: {}", new Object[] {wrapperTime, directAccessTime});
		logger.info("wrapper time =(aprox) {} x direct access time", new Object[] {proporcion});
	}
	
	public class TestPropertyWithoutPrefixComponent extends FieldsReflectionComponent {

		@EntityProperty
		private String value;
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public TestPropertyWithoutPrefixComponent(String id) {
			super(id);
		}
		
	}

	@Test
	public void shouldSetPropertyToComponentUsingDefaultNameIfPropertyWithPrefixDoesNotExists() {
		TestPropertyWithoutPrefixComponent component = new TestPropertyWithoutPrefixComponent("testComponent");
		Entity entity = new Entity("entity");
		entity.addProperty("value", new SimpleProperty<Object>("hello"));
		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(TestPropertyWithoutPrefixComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);
		assertEquals("hello", component.getValue());
	}
	
	@Test(expected=RequiredPropertyNotFoundException.class)
	public void shouldFailIfRequiredPropertyNotFoundWithOrWithoutComponentIdPrefix() {
		TestPropertyWithoutPrefixComponent component = new TestPropertyWithoutPrefixComponent("testComponent");
		Entity entity = new Entity("entity");
		ComponentPropertiesWrapper componentPropertyWrapperImpl = new ComponentPropertiesWrapperImpl(TestPropertyWithoutPrefixComponent.class);
		componentPropertyWrapperImpl.importFrom(component, entity);
		assertEquals("hello", component.getValue());
	}
	
	// test should fail if required property does not exist with or without prefix
	
}
