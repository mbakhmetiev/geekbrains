package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.screen.ScreenManager;
import com.mygdx.game.screen.utils.Assets;

import java.util.WeakHashMap;

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
    private int maxBullets;
    private int coins;
    private int scoreView;
    private float hp;
//    private boolean rightOrLeftSocket = true;
    private StringBuilder strBuilder;
    private Circle hitArea;
    private Weapon currentWeapon;

    private final float BASE_SIZE = 64.0f;

    public void addScore(int amount) {
        score += amount;
    }

//    public int getScore() { return score; }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() { return position; }

    public Circle getHitArea() { return hitArea; }

//    public float getBASE_SIZE() { return BASE_SIZE; }

    public float getAngle() { return angle; }

    public float getHp() { return hp; }

    public Hero (GameController gc) {
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
        this.position = new Vector2(640, 360);
        this.velocity = new Vector2(0,0);
        this.angle = 0.0f;
        this.enginePower = 800.0f;
        this.reversePower = 500.0f;
        this.hitArea = new Circle(position, 28.0f);
        this.hitArea.setPosition(this.position);
        this.hitArea.setRadius(BASE_SIZE / 2 * 0.9f);
        this.hp = 100f;
        this.strBuilder = new StringBuilder();
        this.maxBullets = 1000;
        this.coins = 0;
        this.currentWeapon = new Weapon( gc, this, "Laser", 0.2f, 1, 900.0f, maxBullets,
                        new Vector3[]{
                            new Vector3(28, 0, 0),
                            new Vector3(28, 90, 0),
                            new Vector3(28, -90, 0)
                    }
                 );
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x -32, position.y - 32,32,32,64,64, 1,1,angle);
    }

    public void renderGui(SpriteBatch batch, BitmapFont font) {
        strBuilder.clear();
        strBuilder.append("SCORE: ").append(scoreView).append("\n");
        strBuilder.append("HP : ").append(Math.floor(hp * 100) / 100).append("\n");
        strBuilder.append("BULLETS: ").append(currentWeapon.getCurrBullets()).append(" / ").append(currentWeapon.getMaxBullets()).append("\n");
        strBuilder.append("COINS : ").append(coins).append("\n");
        font.draw(batch, strBuilder, 20, 700);
    }

//    public void Bounce (float p, Vector2 velocity) {
//        this.velocity.x = -velocity.x + p;
//        this.velocity.y = -velocity.y + p;
//    }

    public void takeDamage (float amount) {
        this.hp -= amount;
    }

    public void healUp (float getHealPackProbo) {
        this.hp += getHealPackProbo / 50.0f;
        if (this.hp > 100) {
            this.hp = 100;
        }
    }

    public void ammoUp (float getAmmoPackProbo) {
        currentWeapon.setCurrBullets(getAmmoPackProbo / 2.0f);
    }

    public void coinUp (float coins) {
        this.coins += coins;
    }

    public void update (float dt) {
        fireTimer += dt;
        updateScore(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            tryToFire();
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

//                emitParticleFwd(); emitParticleFwd(); emitParticleFwd();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocity.x += -(float) Math.cos(Math.toRadians(angle)) * reversePower * dt;
                velocity.y += -(float) Math.sin(Math.toRadians(angle)) * reversePower * dt;

//                emitParticleBwd(); emitParticleBwd(); emitParticleBwd();
            }

            position.mulAdd(velocity, dt);
            hitArea.setPosition(position);
            float stopKoef = 1.0f - 2.0f * dt;
            if (stopKoef < 0.0f) {
                stopKoef = 0.0f;
            }
            velocity.scl(stopKoef);

            checkSpaceBorders();
        }

    public void tryToFire() {
        if (fireTimer > currentWeapon.getFirePeriod()) {
            fireTimer = 0.0f;
            currentWeapon.fire();
        }
    }

    public void emitParticleFwd() {
        float bx, by;
        bx = position.x - 28.0f * (float) Math.cos(Math.toRadians(angle));
        by = position.y - 28.0f * (float) Math.sin(Math.toRadians(angle));
        gc.getParticleController().setup(
                bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                velocity.x + MathUtils.random(-20, 20), velocity.y + MathUtils.random(-20, 20) ,
                0.2f,
                1.4f, 0.4f,
                1.0f, 0.5f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f
        );
    }

    public void emitParticleBwd() {
        float bx, by;
        bx = position.x - 28.0f * (float) Math.cos(Math.toRadians(angle));
        by = position.y - 28.0f * (float) Math.sin(Math.toRadians(angle));
        gc.getParticleController().setup(
            bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
            velocity.x  + MathUtils.random(-20, 20), velocity.y  + MathUtils.random(-20, 20),
            0.1f,
            1.4f, 0.4f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 0.0f
        );
    }

    public void checkSpaceBorders() {
        if (position.x < hitArea.radius) {
            position.x = hitArea.radius;
            velocity.x *= -1;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH - hitArea.radius) {
            position.x = ScreenManager.SCREEN_WIDTH - hitArea.radius;
            velocity.x *= -1;
        }
        if (position.y < hitArea.radius) {
            position.y = hitArea.radius;
            velocity.y *= -1;
        }
        if (position.y > ScreenManager.SCREEN_HEIGTH - hitArea.radius) {
            position.y = ScreenManager.SCREEN_HEIGTH - hitArea.radius;
            velocity.y *= -1;
        }
    }

    public void updateScore(float dt) {
        if(scoreView < score) {
            float scoreSpeed = (score - scoreView) / 2.0f;
            if (scoreSpeed < 2000.0f) {
                scoreSpeed = 2000.0f;
            }
            scoreView += scoreSpeed * dt;
            if (scoreView > score) {
                scoreView = score;
            }
        }
    }
}
