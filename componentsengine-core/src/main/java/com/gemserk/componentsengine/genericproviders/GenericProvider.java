package com.gemserk.componentsengine.genericproviders;

public interface GenericProvider {

	<T> T get();

	<T> T get(Object ...objects);


}