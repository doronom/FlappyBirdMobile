package com.doron.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;  // Array to store individual frames of the animation
    private float maxFrameTime;  // Maximum time for each frame
    private float currentFrameTime;  // Time elapsed for the current frame
    private int frameCount;  // Total number of frames in the animation
    private int frame;  // Index of the current frame

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();  // Initialize the array to store frames
        int frameWidth = region.getRegionWidth() / frameCount;  // Calculate width of each frame
        for (int i = 0; i < frameCount; i++) {
            // Extract individual frames from the texture region and add them to the frames array
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;  // Store the total frame count
        maxFrameTime = cycleTime / frameCount;  // Calculate the maximum time per frame
        frame = 0;  // Initialize the frame index
    }

    public void update(float dt) {
        currentFrameTime += dt;  // Increment the time elapsed for the current frame
        if (currentFrameTime > maxFrameTime) {
            frame++;  // Move to the next frame
            currentFrameTime = 0;  // Reset the frame time
        }
        if (frame >= frameCount)
            frame = 0;  // Loop back to the first frame when reaching the end
    }

    public TextureRegion getFrame() {
        return frames.get(frame);  // Return the current frame's texture region
    }
}
