/**
 * 
 */
package com.gemserk.componentsengine.input;

class InputKey {
	String controller;
	String button;

	public InputKey(String controller, String button) {
		this.controller = controller;
		this.button = button;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((button == null) ? 0 : button.hashCode());
		result = prime * result + ((controller == null) ? 0 : controller.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputKey other = (InputKey) obj;
		if (button == null) {
			if (other.button != null)
				return false;
		} else if (!button.equals(other.button))
			return false;
		if (controller == null) {
			if (other.controller != null)
				return false;
		} else if (!controller.equals(other.controller))
			return false;
		return true;
	}

	
}