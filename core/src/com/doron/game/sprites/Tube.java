package com.doron.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130; // Maximum vertical gap between tubes
    private static final int TUBE_GAP = 100; // Gap between upper and lower tubes
    private static final int LOWEST_OPENING = 120; // Lowest allowed opening for the tubes
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube; // Positions of the top and bottom tubes
    private Rectangle boundsTop, boundsBot; // Collision bounding boxes
    private Random rand; // Random generator for tube positioning
    private boolean isScored;  // Variable to track if the tube has been scored


    public Tube(float x) {
        topTube = new Texture("toptube.png"); // Load texture for top tube
        bottomTube = new Texture("bottomtube.png"); // Load texture for bottom tube
        rand = new Random(); // Initialize random generator

        // Calculate positions for top and bottom tubes based on random values and constants
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        // Initialize collision bounding boxes
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        isScored = false;  // Initialize isScored as false
    }

    public Texture getTopTube() {
        return topTube; // Return the top tube texture
    }

    public Texture getBottomTube() {
        return bottomTube; // Return the bottom tube texture
    }

    public Vector2 getPosTopTube() {
        return posTopTube; // Return the position of the top tube
    }

    public Vector2 getPosBotTube() {
        return posBotTube; // Return the position of the bottom tube
    }

    public void reposition(float x) {
        // Reposition tubes when they go off-screen
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        // Update collision bounding box positions
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player) {
        // Check if the player's bounding box overlaps with either tube's bounding box
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose() {
        topTube.dispose(); // Dispose of top tube texture
        bottomTube.dispose(); // Dispose of bottom tube texture
    }

    public boolean isScored() {
        return isScored;  // Return the scored status of the tube
    }

    public void setScored(boolean scored) {
        isScored = scored;  // Set the scored status of the tube
    }
}
