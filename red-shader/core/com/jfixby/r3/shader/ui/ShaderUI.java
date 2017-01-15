
package com.jfixby.r3.shader.ui;

import java.io.IOException;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.input.MouseScrolledEvent;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.shader.ShaderFactory;
import com.jfixby.r3.api.ui.unit.shader.ShaderSpecs;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.user.KeyboardInputEventListener;
import com.jfixby.r3.api.ui.unit.user.UpdateListener;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.shader.pack.PackConfig;
import com.jfixby.r3.shader.pack.ShaderRepacker;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.rana.api.pkg.ResourceRebuildIndexListener;
import com.jfixby.rana.api.pkg.ResourcesGroup;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.UserInput;
import com.jfixby.scarabei.api.log.L;

public class ShaderUI implements Unit, AssetsConsumer {
	private RootLayer root;
	private ComponentsFactory factory;
	public static final ID shader_id1 = Names.newID("com.jfixby.r3.shader.shader1");

	long timestamp = 0;
	private Scene game_scene;
// private AssetHandler assetHandler;
	GifRecorder recorder;
	long lastPSDCheckTimestamp = 0;
	double frame = -1;
	long DELTA = 1000;
	private ShaderFactory shadersFactory;
	private Camera camera;
	private ShaderComponent shader;
	private long startTime;

	@Override
	public void onCreate (final UnitManager unitManager) {
		L.d("CREATE " + this);
		this.root = unitManager.getRootLayer();
		this.factory = unitManager.getComponentsFactory();
		this.recorder = new GifRecorder(unitManager.getToolkit());

		this.root.attachComponent(this.onUpdate);

		this.root.attachComponent(this.onKeyboardInput);

		this.shadersFactory = this.factory.getShadersDepartment();

		final CameraSpecs camSpec = this.factory.getCameraDepartment().newCameraSpecs();
		camSpec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
		this.camera = this.factory.getCameraDepartment().newCamera(camSpec);
		this.camera.setOriginRelative(0.5, 0.5);
		this.camera.setPosition(0, 0);
		this.camera.setDebugFlag(true);
		this.root.setCamera(this.camera);

		final PackageReaderListener listener = PackageReaderListener.DEFAULT;
		AssetsManager.autoResolveAsset(shader_id1, listener);

		this.shader = createShader(shader_id1, this.shadersFactory);
		this.root.attachComponent(this.shader);
		this.root.attachComponent(this.shaderClock);
		this.startTime = System.currentTimeMillis();

	}

	final UpdateListener shaderClock = new UpdateListener() {
		@Override
		public void onUpdate (final UnitClocks unit_clock) {

			ShaderUI.this.frame++;
			if (ShaderUI.this.frame % 2 == 0) {
				ShaderUI.this.recorder.push();
			}
// final double time = 2 * (System.currentTimeMillis() - ShaderUI.this.startTime) / 1000d;
			final double time = ShaderUI.this.frame / 25d;

			ShaderUI.this.shader.setFloatParameterValue("time", time);
		}
	};

	static private ShaderComponent createShader (final ID shader_asset_id, final ShaderFactory shader_factory) {
		final ShaderSpecs shader_specs = shader_factory.newShaderSpecs();
		shader_specs.setShaderAssetID(shader_asset_id);
		final Rectangle shape = Geometry.newRectangle();
		shape.setOriginRelative(0.5, 0.5);
		shape.setPosition(0, 0);
		shape.setSize(512, 512);

		//
		shader_specs.setShape(shape);
		final ShaderComponent shader = shader_factory.newShader(shader_specs);
		shader.setName("shader");
// for (int i = 0; i < shader_settings.params.size(); i++) {
// final ShaderParameterValue parameter = shader_settings.params.get(i);
// if (parameter.type == ShaderParameterType.FLOAT) {
// shader.setFloatParameterValue(parameter.name, Double.parseDouble(parameter.value));
// }
// }
//
		return shader;
	}

	final UpdateListener onUpdate = new UpdateListener() {
		@Override
		public void onUpdate (final UnitClocks unit_clock) {

// ShaderUI.this.repack();
		}

	};
	public static final ID unit_id = Names.newID("com.jfixby.r3.shader.ui.ShaderUI");

	private void repack () {
		ShaderUI.this.recorder.stop();
		try {
			ShaderRepacker.repack();

		} catch (final IOException e) {
			e.printStackTrace();
		}
		UI.loadUnit(unit_id);

	}

	final KeyboardInputEventListener onKeyboardInput = new KeyboardInputEventListener() {

		@Override
		public boolean onKeyDown (final Key key) {
			if (UserInput.Keyboard().G() == key) {
				ShaderUI.this.recorder.start();
			}
			return true;
		}

		@Override
		public boolean onKeyUp (final Key key) {
			if (UserInput.Keyboard().R() == key) {
				ShaderUI.this.repack();
			}
			if (UserInput.Keyboard().G() == key) {
				ShaderUI.this.recorder.stop();
			}
			if (UserInput.Keyboard().A() == key) {
			}
			return true;
		}

		@Override
		public boolean onCharTyped (final char char_typed) {

			return false;
		}

		@Override
		public boolean onMouseScrolled (final MouseScrolledEvent event) {
			return false;
		}

	};

	@Override
	public void onDestroy () {
		AssetsManager.purge();
		final ResourceRebuildIndexListener listener = null;
		// AssetsManager.printAllLoadedAssets();
		final ResourcesGroup group = ResourcesManager.getResourcesGroup(Names.newID(PackConfig.BANK_NAME));
		if (group == null) {
			return;
		}

		group.rebuildAllIndexes(listener);
		group.printAllIndexes();
	}

	@Override
	public void onStart () {
	}

	@Override
	public void onResume () {
	}

	@Override
	public void onPause () {
	}

}
