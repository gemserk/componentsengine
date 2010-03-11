package com.gemserk.componentsengine.instantiationtemplates;

import com.gemserk.componentsengine.entities.Entity;

public interface InstantiationTemplate {

	Entity get();

	Entity get(Object... parameters);

}