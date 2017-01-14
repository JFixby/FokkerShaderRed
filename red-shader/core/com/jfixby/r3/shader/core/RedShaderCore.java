
package com.jfixby.r3.shader.core;

import com.jfixby.r3.api.logic.BusinessLogicComponent;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class RedShaderCore implements BusinessLogicComponent {

	public static final ID unit_id = Names.newID("com.jfixby.r3.shader.ui.ShaderUI");

	@Override
	public void start () {

		UI.loadUnit(unit_id);

	}
}
