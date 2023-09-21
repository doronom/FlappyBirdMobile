package com.doron.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doron.game.FlappyDemo;

public class MenuState extends State {
    private Texture background;  // Texture for the background
    private Texture playBtn;  // Texture for the play button

    public MenuState(GameStateManager gsm) {
        super(gsm);  // Call the constructor of the parent State class
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);  // Set camera's orthographic projection
        background = new Texture("bg.png");  // Load the background texture
        playBtn = new Texture("Playbutton.png");  // Load the play button texture
    }

    @Override
    public void handleInput() {
        // Check for user touch input
        if (Gdx.input.justTouched()) {
            // Set the game state to PlayState when play button is touched
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();  // Update the state by handling user input
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);  // Set projection matrix for rendering
        sb.begin();  // Begin rendering with SpriteBatch
        sb.draw(background, 0, 0);  // Draw the background texture at (0, 0)
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);  // Draw play button centered on screen
        sb.end();  // End rendering
    }

    @Override
    public void dispose() {
        background.dispose();  // Dispose of background texture
        playBtn.dispose();  // Dispose of play button texture
        System.out.println("Menu State Disposed");  // Print message to indicate state disposal
    }
}
