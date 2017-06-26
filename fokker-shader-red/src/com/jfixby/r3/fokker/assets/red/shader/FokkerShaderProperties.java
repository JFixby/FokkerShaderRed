
package com.jfixby.r3.fokker.assets.red.shader;

import com.jfixby.r3.fokker.api.ShaderProperties;
import com.jfixby.r3.fokker.assets.api.shader.ShaderAsset;
import com.jfixby.r3.fokker.assets.api.shader.ShaderParameter;
import com.jfixby.scarabei.api.collections.Mapping;

public class FokkerShaderProperties implements ShaderProperties {
	private Mapping<String, ShaderParameter> parameters;

	public FokkerShaderProperties (final RedFokkerShader redFokkerShader) {
	}

	public void set (final ShaderAsset asset) {
		this.parameters = asset.listParameters();
	}

	public Mapping<String, ShaderParameter> listParameters () {
		return this.parameters;
	}

}
