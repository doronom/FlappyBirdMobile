package com.doron.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected OrthographicCamera cam;  // Camera to manage the view
    protected Vector3 mouse;  // Vector to store mouse/touch input coordinates
    protected GameStateManager gsm;  // Game state manager to handle game states

    // Constructor for the State class
    protected State(GameStateManager gsm) {
        this.gsm = gsm;  // Initialize the game state manager
        cam = new OrthographicCamera();  // Initialize the camera
        mouse = new Vector3();  // Initialize the mouse vector for input handling
    }

    // Abstract method to handle user input
    protected abstract void handleInput();

    // Abstract method to update the state
    public abstract void update(float dt);

    // Abstract method to render the state
    public abstract void render(SpriteBatch sb);

    // Abstract method to dispose of resources when the state is no longer needed
    public abstract void dispose();
}
