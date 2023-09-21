package com.doron.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.doron.game.FlappyDemo;
import com.doron.game.sprites.Bird;
import com.doron.game.sprites.Tube;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PlayState extends State {

    // Constants for tube placement and ground position
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    // Game objects and textures
    private Bird bird;
    private Texture bg;
    private Texture ground, gameOver;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;
    private boolean gameover;
    private int score;  // Variable to hold the player's score
    private BitmapFont font;  // Add this line to define the font


    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new BitmapFont();  // Initialize the font object
        // Initialize game objects and textures
        bird = new Bird(50, 200);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        gameOver = new Texture("gameover.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        // Create and position tubes
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        // Initialize game over state and collision flag
        gameover = false;

        score = 0;  // Initialize the score to 0

    }
    private void incrementScore() {
        score++;
    }

    @Override
    protected void handleInput() {
        // Handle player input (jump) when game is not over
        if (Gdx.input.justTouched()) {
            if (gameover) {
                // Restart the game if game over
                gsm.set(new PlayState(gsm));
            } else {
                bird.jump();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        // Update tube positions and check for collisions
        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);

            // Check for score increment when passing tubes
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));

                // Increase the score when a tube is passed
                score++;
            }

            // Reposition off-screen tubes to create scrolling effect
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            // Check for collision with bird
            if (tube.collides(bird.getBounds())) {
                bird.colliding = true;
                gameover = true;
            }

        }

        // Check if bird collided with the ground
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameover = true;
            bird.colliding = true;
        }

        // Update the camera position
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Set the projection matrix for rendering
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        // Draw game elements
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        // Draw the score on the screen
        String scoreText = "Score: " + score;
        font.draw(sb, scoreText, cam.position.x - cam.viewportWidth / 2 + 10, cam.viewportHeight - 10);

        // Draw game over screen if game is over
        if (gameover) {
            sb.draw(gameOver, cam.position.x - gameOver.getWidth() / 2, cam.position.y);
            font.draw(sb, scoreText, cam.position.x - cam.viewportWidth / 2 + 10, cam.viewportHeight - 10);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        // Dispose of resources
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        font.dispose();  // Dispose of the font when the state is disposed
        System.out.println("Play State Disposed");
    }

    private void updateGround() {
        // Update ground position for scrolling effect
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
