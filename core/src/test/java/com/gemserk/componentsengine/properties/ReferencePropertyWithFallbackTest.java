package com.gemserk.componentsengine.properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.componentsengine.utils.RandomAccessMap;

public class ReferencePropertyWithFallbackTest {

	private static final String TESTVALUE1 = "testValue1";
	private static final String TESTVALUE2 = "testValue2";
	private static final String FALLBACKPROPNAME = "fallbackpropname";
	private static final String PROPNAME = "propname";
	ReferencePropertyWithFallback<String> property;

	PropertiesHolderImpl holder;

	@Before
	public void setUp() {
		property = new ReferencePropertyWithFallback<String>(PROPNAME, FALLBACKPROPNAME);
		holder = new PropertiesHolderImpl(new RandomAccessMap<String, Property<Object>>());
	}

	@Test
	public void ifthepropnameispresentitshouldreturnthatproperty() {
		holder.addProperty(PROPNAME, new SimpleProperty<Object>(TESTVALUE1));
		property.setPropertiesHolder(holder);

		Assert.assertEquals(TESTVALUE1, property.get());
	}

	@Test
	public void ifthepropnameispresentandthefallbackitshouldreturnthepropname() {
		holder.addProperty(PROPNAME, new SimpleProperty<Object>(TESTVALUE1));
		holder.addProperty(FALLBACKPROPNAME, new SimpleProperty<Object>(TESTVALUE2));
		property.setPropertiesHolder(holder);

		Assert.assertEquals(TESTVALUE1, property.get());
	}

	@Test(expected = NullPointerException.class)
	public void ifthepropnameisnotpresentandthereisnofallbackitshouldfail() {
		property.setPropertiesHolder(holder);

		Assert.assertEquals(TESTVALUE1, property.get());
	}
	
	@Test
	public void ifthepropnameisnotpresentandthereisAFallbackItShouldReturnTheFallback() {
		holder.addProperty(FALLBACKPROPNAME, new SimpleProperty<Object>(TESTVALUE2));
		property.setPropertiesHolder(holder);

		Assert.assertEquals(TESTVALUE2, property.get());
	}

}
