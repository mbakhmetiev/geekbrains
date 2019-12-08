package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screen.ScreenManager;
import com.mygdx.game.screen.utils.Assets;

public class Hero {
    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private float enginePower;
    private float reversePower;
    private float fireTimer;
    private int score;
    private int scoreView;
    private float health;
    private boolean rightOrLeftSocket = true;

    private Circle hitArea = new Circle(0,0,0);

    private final float BASE_SIZE = 64.0f;

    public int getScoreView() { return scoreView; }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() { return score; }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Circle getHitArea() { return hitArea; }

    public float getBASE_SIZE() { return BASE_SIZE; }

    public float getAngle() { return angle; }

    public float getHealth() { return health; }

    public Hero (GameController gc) {
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
        this.position = new Vector2(640, 360);
        this.velocity = new Vector2(0,0);
        this.angle = 0.0f;
        this.enginePower = 800.0f;
        this.reversePower = 500.0f;
        this.hitArea.setPosition(this.position);
        this.hitArea.setRadius(BASE_SIZE / 2 * 0.9f);
        this.health = 100f;
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x -32, position.y - 32,32,32,64,64, 1,1,angle);
    }

    public void Bounce (float p, Vector2 velocity) {
        this.velocity.x = -velocity.x + p;
        this.velocity.y = -velocity.y + p;
    }

    public void takeDamage (float amount) {
        this.health -= amount;
    }

    public void update (float dt) {
        fireTimer += dt;
        if(scoreView < score) {
            float scoreSpeed = (score - scoreView) / 2.0f;
            if (scoreSpeed < 200.0f) {
                scoreSpeed = 200.0f;
            }
            scoreView += scoreSpeed * dt;
            if (scoreView > score) {
                scoreView = score;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            if (fireTimer > 0.04f) {
                fireTimer = 0.0f;
                float wx = 0.0f, wy = 0.0f;
                rightOrLeftSocket = !rightOrLeftSocket;
                if (rightOrLeftSocket) {
                    wx = position.x + (float) Math.cos(Math.toRadians(angle + 90)) * 28;
                    wy = position.y + (float) Math.sin(Math.toRadians(angle + 90)) * 28;
                    gc.getBulletController().setup(wx, wy, (float) Math.cos(Math.toRadians(angle)) * 900, (float) Math.sin(Math.toRadians(angle)) * 900, angle);
//                    gc.getBulletController().setup(wx, wy, (float) Math.cos(Math.toRadians(angle)) * 700 + velocity.x, (float) Math.sin(Math.toRadians(angle)) * 700 + velocity.y, angle);
                } else {
                    wx = position.x + (float) Math.cos(Math.toRadians(angle - 90)) * 28;
                    wy = position.y + (float) Math.sin(Math.toRadians(angle - 90)) * 28;
                    gc.getBulletController().setup(wx, wy, (float) Math.cos(Math.toRadians(angle)) * 900, (float) Math.sin(Math.toRadians(angle)) * 900, angle);
//                    gc.getBulletController().setup(wx, wy, (float) Math.cos(Math.toRadians(angle)) * 700 + velocity.x, (float) Math.sin(Math.toRadians(angle)) * 700 + velocity.y, angle);
                }
            }
        }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                angle += 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                angle -= 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocity.x += (float) Math.cos(Math.toRadians(angle)) * enginePower * dt;
                velocity.y += (float) Math.sin(Math.toRadians(angle)) * enginePower * dt;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocity.x += -(float) Math.cos(Math.toRadians(angle)) * reversePower * dt;
                velocity.y += -(float) Math.sin(Math.toRadians(angle)) * reversePower * dt;
            }

            position.mulAdd(velocity, dt);

            float stopKoef = 1.0f - 2.0f * dt;
            if (stopKoef < 0.0f) {
                stopKoef = 0.0f;
            } else velocity.scl(stopKoef);

            if (position.x < 0) {
                position.x = 0.0f;
                velocity.x *= -1;
            }
            if (position.x > ScreenManager.SCREEN_WIDTH) {
                position.x = ScreenManager.SCREEN_WIDTH;
                velocity.x *= -1;
            }
            if (position.y < 0) {
                position.y = 0.0f;
                velocity.y *= -1;
            }
            if (position.y > ScreenManager.SCREEN_HEIGTH) {
                position.y = ScreenManager.SCREEN_HEIGTH;
                velocity.y *= -1;
            }
        hitArea.setPosition(position);
        }
    }
