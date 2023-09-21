package com.doron.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create a configuration object for the Android application
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Initialize the game with the main application class (FlappyDemo) and the configuration
		initialize(new FlappyDemo(), config);
	}
}
