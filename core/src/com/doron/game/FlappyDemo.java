package com.doron.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.doron.game.states.GameStateManager;
import com.doron.game.states.MenuState;

public class FlappyDemo extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Flappy Bird";
	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;

	@Override
	public void create () {
		// Initialize SpriteBatch for rendering graphics
		batch = new SpriteBatch();

		// Initialize GameStateManager for managing game states
		gsm = new GameStateManager();

		// Load and play background music
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true); // Set the music to loop
		music.setVolume(0.1f); // Set the volume of the music
		music.play(); // Start playing the music

		// Set the clear color for the OpenGL rendering context
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// Push the initial game state (MenuState) onto the GameStateManager
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		// Clear the screen with the specified clear color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the game state manager with the time passed since the last frame
		gsm.update(Gdx.graphics.getDeltaTime());

		// Render the current game state using the SpriteBatch
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		// Dispose of resources and clean up
		batch.dispose();
		super.dispose();
		music.dispose();
	}
}
