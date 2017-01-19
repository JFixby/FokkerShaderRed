
package com.jfixby.r3.shader.ui;

import java.io.IOException;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;

public class JavaFileConsumer {

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();

		final FilesList files = LocalFileSystem.ApplicationHome().child("assets").child("com.jfixby.r3.shader.assets.local")
			.child("tank-0").child("com.jfixby.r3.shader").child("content").child("shader1").listAllChildren();

		files.print("files");

		for (final File f : files) {
			String data = f.readToString();
			data = data.replaceAll("\\n", "\\\\n\"+\"");
			data = "\"" + data + "\"";
			L.d("String " + f.nameWithoutExtension() + " = " + data + ";");
		}
	}

}
