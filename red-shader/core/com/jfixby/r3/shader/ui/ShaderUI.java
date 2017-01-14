
package com.jfixby.r3.shader.ui;

import java.io.IOException;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.RootLayer;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitManager;
import com.jfixby.r3.api.ui.unit.input.MouseScrolledEvent;
import com.jfixby.r3.api.ui.unit.update.UnitClocks;
import com.jfixby.r3.api.ui.unit.user.KeyboardInputEventListener;
import com.jfixby.r3.api.ui.unit.user.UpdateListener;
import com.jfixby.r3.ext.api.scene2d.Scene;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.scene2d.Scene2DSpawningConfig;
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
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.UserInput;
import com.jfixby.scarabei.api.log.L;

public class ShaderUI implements Unit, AssetsConsumer {

	private RootLayer root;
	private ComponentsFactory factory;
	public static final ID scene_id = Names.newID("com.jfixby.r3.shader.ui.scene.psd");

	long timestamp = 0;
	private Scene game_scene;
// private AssetHandler assetHandler;
	GifRecorder recorder;
	long lastPSDCheckTimestamp = 0;
	double frame = -1;
	long DELTA = 1000;

	@Override
	public void onCreate (final UnitManager unitManager) {
		L.d("CREATE " + this);
		this.root = unitManager.getRootLayer();
		this.factory = unitManager.getComponentsFactory();
		this.recorder = new GifRecorder(unitManager.getToolkit());
		this.root.attachComponent(this.onUpdate);

		this.root.attachComponent(this.onKeyboardInput);

// v.intValue();// simulate crash

		this.deployScene();
// this.root.attachComponent(this.console);
	}

	private void deployScene () {

		final Scene2DSpawningConfig config = Scene2D.newSceneSpawningConfig();
		config.setStructureID(this.scene_id);
		config.setPackageListener(PackageReaderListener.DEFAULT);

		this.game_scene = Scene2D.spawnScene(this.factory, config);

		this.root.attachComponent(this.game_scene);
	}

	final UpdateListener onUpdate = new UpdateListener() {
		@Override
		public void onUpdate (final UnitClocks unit_clock) {

			ShaderUI.this.repack();
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
