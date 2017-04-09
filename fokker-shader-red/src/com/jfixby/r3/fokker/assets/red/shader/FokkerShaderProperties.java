
package com.jfixby.r3.fokker.assets.red.shader;

import com.jfixby.r3.fokker.api.ShaderParameter;
import com.jfixby.r3.fokker.api.ShaderProperties;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderInfo;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderParameterInfo;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;

public class FokkerShaderProperties implements ShaderProperties {
	private final Map<String, ShaderParameter> parameters_by_name = Collections.newMap();
	private final Map<String, ShaderParameter> parameters_list = Collections.newMap();

	public FokkerShaderProperties (final ShaderEntry redFokkerShader) {
	}

	public void set (final ShaderInfo info) {
		for (final ShaderParameterInfo param : info.parameters_list) {
			final String key = param.name;
			final ShaderParameter value = new RedShaderParameter(param);
			this.parameters_by_name.put(key, value);
		}
	}

	@Override
	public Mapping<String, ShaderParameter> listParameters () {
		return this.parameters_by_name;
	}

}
