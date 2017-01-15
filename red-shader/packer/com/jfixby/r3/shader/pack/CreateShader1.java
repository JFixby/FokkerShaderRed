
package com.jfixby.r3.shader.pack;

import java.io.IOException;

import com.jfixby.r3.api.shader.srlz.R3_SHADER_SETTINGS;
import com.jfixby.r3.api.shader.srlz.ShaderInfo;
import com.jfixby.r3.api.shader.srlz.ShadersContainer;
import com.jfixby.r3.shader.ui.ShaderUI;
import com.jfixby.scarabei.adopted.gdx.json.RedJson;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.json.JsonString;
import com.jfixby.scarabei.api.log.L;

public class CreateShader1 {

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();
		Json.installComponent(new RedJson());

		final ID shaderID = ShaderUI.shader_id1;

		final File output_folder = LocalFileSystem.ApplicationHome().child("shaders").child("" + shaderID.parent());
		output_folder.makeFolder();
		final File content = output_folder;
		content.makeFolder();

		final String name = shaderID.getLastStep();

		final File pack = content.child(name);
		pack.makeFolder();
		final File root_file = output_folder.child(R3_SHADER_SETTINGS.ROOT_FILE_NAME);
		final ShadersContainer container = new ShadersContainer();

		add(name, container, shaderID);

		final ByteArray params_data = IO.serialize(container);
		root_file.writeBytes(params_data);
// L.d("shader writing done", params_data);
		final JsonString debugString = Json.serializeToString(container);
		L.d(debugString);

	}

	private static void add (final String name, final ShadersContainer container, final ID shader_id) {
		final ShaderInfo info = new ShaderInfo();
		info.shader_id = shader_id.toString();
		info.shader_folder_name = name;
		info.isOverlay = false;
		container.shaders.add(info);
	}

}
