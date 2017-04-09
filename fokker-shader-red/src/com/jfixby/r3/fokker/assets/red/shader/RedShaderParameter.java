
package com.jfixby.r3.fokker.assets.red.shader;

import com.jfixby.r3.fokker.api.ShaderParameter;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderParameterInfo;

public class RedShaderParameter implements ShaderParameter {

	private final String name;
	private final String type;

	public RedShaderParameter (final ShaderParameterInfo param) {
		this.name = param.name;
		this.type = param.type;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getType () {
		return this.type;
	}

}
