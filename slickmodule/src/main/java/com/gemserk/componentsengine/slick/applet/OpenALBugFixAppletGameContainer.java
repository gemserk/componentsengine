package com.gemserk.componentsengine.slick.applet;

import org.lwjgl.openal.AL;
import org.newdawn.slick.AppletGameContainer;

/**
 * Provides a fix for the bug of not closing the OpenAL context when closing the applet. It should be removed when slick fix this bug on SVN.
 * @author acoppes
 *
 */
@Deprecated
public class OpenALBugFixAppletGameContainer extends AppletGameContainer {

	private static final long serialVersionUID = -5717088273559286792L;
	
	@Override
	public void stop() {
		super.stop();
		AL.destroy();
	}

}
