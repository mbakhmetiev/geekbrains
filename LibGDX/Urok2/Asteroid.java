package com.mygdx.game.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game.helper.Poolable;
import com.mygdx.game.screen.ScreenManager;

public class Asteroid implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float azimuth;
    private float scale;
    private float rotation;

    public Vector2 getPosition() {
        return position;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public float getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Asteroid() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.azimuth = 0.0f;
        this.active = false;
        this.rotation = 0.0f;
        this.scale = 0.0f;
    }

    public void activate(float x, float y, float vx, float vy, float azimuth, float scale, float rotation) {
        position.set(x, y);
        velocity.set(vx, vy);
        this.azimuth = azimuth;
        this.scale = scale;
        this.rotation = rotation;
        active = true;
    }

    public void update(float dt) {
        rotation += 1;
        position.mulAdd(velocity, dt);
        if (position.x < -200.0f ||  position.y < -200.0f || position.y > ScreenManager.SCREEN_HEIGTH + 200) {
            deactivate();
        }
    }
}


