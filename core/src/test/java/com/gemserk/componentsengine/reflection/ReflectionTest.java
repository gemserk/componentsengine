package com.gemserk.componentsengine.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.componentsengine.annotations.EntityProperty;
import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.entities.Entity;
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

	
}
