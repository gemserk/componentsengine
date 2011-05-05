package com.gemserk.componentsengine.templates;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.componentsengine.entities.Entity;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class EntityBuilderTest {

	@Inject
	Provider<JavaEntityTemplate> javaEntityTemplateProvider;

	@Before
	public void setup() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(TemplateProvider.class).to(RegistrableTemplateProvider.class).in(Singleton.class);
			}
		});
		
		injector.injectMembers(this);
	}

	@Test
	public void shouldBuildAnEntityWithCorrectId() {

		Entity entity = javaEntityTemplateProvider.get().with(new EntityBuilder() {
			@Override
			public void build() {

			}
		}).instantiate("entity");

		assertThat(entity.getId(), IsEqual.equalTo("entity"));
	}

	@Test
	public void shouldBuildAnEntityWithCorrectTags() {

		Entity entity = javaEntityTemplateProvider.get().with(new EntityBuilder() {
			@Override
			public void build() {
				tags("taga", "tagb");
			}
		}).instantiate("entity");

		assertThat(entity.hasTag("taga"), IsEqual.equalTo(true));
		assertThat(entity.hasTag("tagb"), IsEqual.equalTo(true));
	}

	@Test
	public void shouldBuildAnEntityWithCorrectProperties() {

		Entity entity = javaEntityTemplateProvider.get().with(new EntityBuilder() {
			@Override
			public void build() {
				property("property1", "value1");
			}
		}).instantiate("entity");

		assertThat(entity.getProperty("property1"), IsNull.notNullValue());
		assertThat((String) entity.getProperty("property1").get(), IsEqual.equalTo("value1"));
		assertThat(entity.getProperty("property2"), IsNull.nullValue());
	}

	@Test
	public void shouldBuildAnEntityChildOfAnotherEntity() {

		Entity entity = javaEntityTemplateProvider.get().with(new EntityBuilder() {
			@Override
			public void build() {
				
				child(javaEntityTemplateProvider.get().with(new EntityBuilder() {
					
					@Override
					public void build() {
						property("propertychild", "childvalue");
					}
				}).instantiate("child"));
				
				property("propertyparent", "parentvalue");
				
			}
		}).instantiate("entity");

		assertThat(entity.getChildren().size(), IsEqual.equalTo(1));

		assertThat(entity.getProperty("propertyparent"), IsNull.notNullValue());
		assertThat((String) entity.getProperty("propertyparent").get(), IsEqual.equalTo("parentvalue"));
		assertThat(entity.getProperty("propertychild"), IsNull.nullValue());
		
		Entity child = entity.getEntityById("child");
		
		assertThat(child, IsNull.notNullValue());
		assertThat(child.getId(), IsEqual.equalTo("child"));
		
		assertThat(child.getProperty("propertychild"), IsNull.notNullValue());
		assertThat((String) child.getProperty("propertychild").get(), IsEqual.equalTo("childvalue"));
		assertThat(child.getProperty("propertyparent"), IsNull.nullValue());

	}
}
