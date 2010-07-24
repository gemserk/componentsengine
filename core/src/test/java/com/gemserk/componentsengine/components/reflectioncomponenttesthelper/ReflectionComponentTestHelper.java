package com.gemserk.componentsengine.components.reflectioncomponenttesthelper;

import com.gemserk.componentsengine.components.Component;
import com.gemserk.componentsengine.components.ReflectionComponent;
import com.gemserk.componentsengine.components.ReflectionComponentTest.ElMock;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;

public class ReflectionComponentTestHelper {

	public Component getComponentAsAnnonimousInnerClass(String id, final ElMock elMock) {
		Component component = new ReflectionComponent(id) {

			@Handles
			public void update(Message message) {
				elMock.update();
			}

		};
		return component;

	}

}
