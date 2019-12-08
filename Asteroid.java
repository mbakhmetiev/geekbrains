package com.mygdx.game.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game.helper.Poolable;
import com.mygdx.game.screen.ScreenManager;

public class Asteroid implements Poolable {
    private GameController gc;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;
    private float scale;
    private float rotation;
    private int hp;
    private int hpMax;
    private Circle hitArea;

    private final float BASE_SIZE = 256.0f;
    private final float BASE_RADIUS = BASE_SIZE / 2.0f;
    private final float SPLIT_SCALE = 0.4f;

    public Vector2 getPosition() {return position;}

    public float getAngle() {return angle;}

    public float getScale() {return scale;}

    public float getRotation() {
        return rotation;
    }

    public Circle getHitArea() { return hitArea; }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public int getHpMax() { return hpMax; }

    public float getBASE_SIZE() { return BASE_SIZE; }

    public Vector2 getVelocity() { return velocity; }

    public Asteroid(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.angle = 0.0f;
        this.active = false;
        this.rotation = 0.0f;
        this.scale = 0.0f;
        this.hitArea = new Circle(0,0,0);
        this.hp = 0;
    }

    public boolean takeDamage (int amount) {
        hp -= amount;
        if(hp <= 0) {
            deactivate();
            if (scale > SPLIT_SCALE) {
                gc.getAsteroidController().setup(position.x, position.y, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(0.0f, 360.0f), scale - 0.2f , rotation);
                gc.getAsteroidController().setup(position.x, position.y, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(0.0f, 360.0f), scale - 0.2f , rotation);
                gc.getAsteroidController().setup(position.x, position.y, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(-150.0f, 150.0f) + 100, MathUtils.random(0.0f, 360.0f), scale - 0.2f , rotation);
            }
            return true;
        }
        return false;
    }

    public void Bounce (float p, Vector2 velocity) {
            this.velocity.x = velocity.x + p;
            this.velocity.y = velocity.y + p;
        }


    public void activate(float x, float y, float vx, float vy, float angle, float scale, float rotation) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.angle = angle;
        this.scale = scale;
        this.rotation = rotation;
        this.active = true;
        this.hpMax = (int)(12 * scale);
        this.hp = this.hpMax;
        this.hitArea.setPosition(position);
        this.hitArea.setRadius(BASE_RADIUS * scale * 0.9f);
    }

    public void update(float dt) {
        rotation += 1;
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);
         if (position.x < -BASE_RADIUS || position.y < -BASE_RADIUS || position.y > ScreenManager.SCREEN_HEIGTH + BASE_RADIUS) {
            deactivate();
        }
    }
}