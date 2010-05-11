package gemserk.utils

import com.gemserk.componentsengine.entities.Entity 
import com.gemserk.componentsengine.messages.Message 
import com.gemserk.componentsengine.properties.Properties;
import com.google.inject.internal.Strings 

class GroovyBootstrapper {
	
	static boolean initialized = false;
	public GroovyBootstrapper() {
		if(!initialized){
			initialize();
			initialized=true;
		}
	}
	
	static def initialize(){
		println "Bootstrapping groovy"
		
		def toEnhanceList = [Entity,Message]
		
		toEnhanceList.each { toEnhance ->
			toEnhance.metaClass {
				propertyMissing << {String name ->
					println "Metaresolving: GET $name"
					def getterMethod = { delegate.properties[name]?.get()}
					
					def capitalName = Strings.capitalize(name)
					toEnhance.metaClass."get$capitalName" = getterMethod
					
					def setterMethod ={param -> Properties.setValue(delegate,name, param)}
					toEnhance.metaClass."set$capitalName" = setterMethod
					
					
					
					getterMethod.delegate = delegate
					return getterMethod()
				}
				
				propertyMissing << {String name, Object value ->
					println "Metaresolving: SET $name"
					Properties.setValue(delegate,name, value)
					
					def getterMethod = { delegate.properties[name]?.get()}
					
					def capitalName = Strings.capitalize(name)
					toEnhance.metaClass."get$capitalName" = getterMethod
					
					def setterMethod ={param -> Properties.setValue(delegate,name, param)}
					toEnhance.metaClass."set$capitalName" = setterMethod
				}
			}
		}
	}
}
