
package com.jfixby.r3.fokker.assets.red.shader;

import java.io.IOException;

import com.jfixby.r3.fokker.assets.api.shader.FokkerShaderPackageReader;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedFokkerShaderPackageReader implements PackageReader, FokkerShaderPackageReader {

	final List<PackageFormat> acceptablePackageFormats;
	private final RedFokkerShaders redFokkerShaders;

	public RedFokkerShaderPackageReader (final RedFokkerShaders redFokkerShaders) {
		this.redFokkerShaders = redFokkerShaders;
		this.acceptablePackageFormats = Collections.newList(new PackageFormat(FokkerShaderPackageReader.PACKAGE_FORMAT));
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
		final ShadersGroup group = new ShadersGroup(input, this.redFokkerShaders);
	}

	@Override
	public PackageReader reader () {
		return this;
	}

}
