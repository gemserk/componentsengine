package com.gemserk.componentsengine.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.componentsengine.entities.Entity;


public class CaptureCorrectPointerWithPropertyTest {
	
	interface Builder {
		
		Entity build(String id);
		
	}
	
	class EntityBuilder1 implements Builder {
		
		Entity entity;

		@Override
		public Entity build(String id) {
			entity = new Entity(id);
			
			final Entity entity2 = entity;
			
			entity.addProperty("identifier", new SimpleProperty<Object>(id));
			
			entity.addProperty("property", new SimpleProperty<Object>() {
				
				@Override
				public Object get() {
					return Properties.getValue(entity2, "identifier");
				}
				
			});
			
			return entity;
		}
		
	}

	@Test
	public void test() {
		
		EntityBuilder1 builder = new EntityBuilder1();
		
		Entity entity1 = builder.build("entity1");
		Entity entity2 = builder.build("entity2");
		Entity entity3 = builder.build("entity3");
		
		assertEquals("entity1", entity1.getProperty("property").get());
		assertEquals("entity2", entity2.getProperty("property").get());
		assertEquals("entity3", entity3.getProperty("property").get());
		
		
	}
	
}
