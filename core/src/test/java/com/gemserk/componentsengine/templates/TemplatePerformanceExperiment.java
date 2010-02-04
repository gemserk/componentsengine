package com.gemserk.componentsengine.templates;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import groovy.lang.GroovyClassLoader;

import java.io.IOException;

import com.gemserk.componentsengine.entities.Entity;

public class TemplatePerformanceExperiment {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// TODO: configurar injectors

		GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
		groovyClassLoader.setShouldRecompile(true);

		GroovyScriptProvider groovyScriptProvider = new GroovyScriptProviderImpl(groovyClassLoader);

		GroovyTemplateProvider provider = new GroovyTemplateProvider();
		provider.setGroovyScriptProvider(groovyScriptProvider);

		System.in.read();

		groovyClassLoader.parseClass("builder.entity('prueba'){100.times{property(\"pipote${it}\",'popote${it}')}}", "pru");
		groovyClassLoader.setShouldRecompile(false);

		EntityTemplate template = provider.getTemplate("pru");
		Entity entity = template.instantiate("test", null);

		long ini = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			entity = template.instantiate("test", null);
		}
		System.out.println("TIME: " + (System.currentTimeMillis() - ini));
		System.out.println(entity);

		assertThat(entity, notNullValue());
		System.out.println(entity.getId());

	}

}
