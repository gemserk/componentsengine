package com.gemserk.componentsengine.templates;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import com.gemserk.componentsengine.entities.Entity;
import com.gemserk.componentsengine.templates.EntityTemplate;
import com.gemserk.componentsengine.templates.GroovyTemplateProvider;

public class TemplatePerformanceExperiment {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GroovyTemplateProvider provider = new GroovyTemplateProvider(null, null,null);
		
		System.in.read();
		
		provider.getGroovyClassloader().parseClass("builder.entity('prueba'){100.times{property(\"pipote${it}\",'popote${it}')}}","pru");
		provider.getGroovyClassloader().setShouldRecompile(false);
		
		EntityTemplate template = provider.getTemplate("pru");
		Entity entity = template.instantiate("test", null);
		
		long ini = System.currentTimeMillis();
		for(int i=0;i<10000000;i++){
			entity = template.instantiate("test", null);
		}
		System.out.println("TIME: " + (System.currentTimeMillis()-ini));
		System.out.println(entity);
		
		assertThat(entity, notNullValue());
		System.out.println(entity.getId());
		
	}

}
