package com.doron.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 position;  // Bird's position (x, y, z)
    private Vector3 velocity;  // Bird's velocity (x, y, z)
    private Rectangle bounds;  // Collision bounding box
    private Animation birdAnimation;  // Animation for bird's appearance
    private Texture texture;  // Bird's texture
    private Sound flap;  // Sound effect for bird's flap
    public boolean colliding;  // Flag for collision state

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);  // Initialize bird's position
        velocity = new Vector3(0, 0, 0);  // Initialize bird's velocity
        texture = new Texture("birdanimation.png");  // Load bird's texture
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);  // Create animation using texture
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());  // Initialize collision bounds
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));  // Load flap sound effect
        colliding = false;  // Initialize collision flag as false
    }

    public void update(float dt) {
        birdAnimation.update(dt);  // Update bird's animation frame
        velocity.add(0, GRAVITY, 0);  // Apply gravity to velocity
        velocity.scl(dt);  // Scale velocity by delta time
        if (!colliding)
            position.add(MOVEMENT * dt, velocity.y, 0);  // Move bird horizontally if not colliding
        if (position.y < 0)
            position.y = 0;  // Keep bird above ground

        velocity.scl(1 / dt);  // Scale velocity back
        bounds.setPosition(position.x, position.y);  // Update collision bounding box position
    }

    public Vector3 getPosition() {
        return position;  // Get bird's current position
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();  // Get current frame of bird's animation
    }

    public void jump() {
        velocity.y = 250;  // Apply upward velocity for jump
        flap.play(0.3f);  // Play flap sound effect
    }

    public Rectangle getBounds() {
        return bounds;  // Get collision bounding box
    }

    public void dispose() {
        texture.dispose();  // Dispose of bird's texture
        flap.dispose();  // Dispose of flap sound effect
    }
}
