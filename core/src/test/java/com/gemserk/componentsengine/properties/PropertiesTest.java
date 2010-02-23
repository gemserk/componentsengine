package com.gemserk.componentsengine.properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.gemserk.componentsengine.entities.Entity;

public class PropertiesTest {

	
	@Test
	public void genericFailureTest(){
		Entity entity = new Entity("id");
		
		float value = 123f;
		String key = "test";
		entity.addProperty(key, new SimpleProperty<Object>(value));
		float valueTest = Properties.getValue(entity, key);
		assertThat(value, equalTo(valueTest));
		
	}
}

