package com.mygdx.game.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game.helper.ObjectPool;

public class AsteroidController extends ObjectPool<Asteroid> {

    private Texture asteroidTexture;

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public AsteroidController() {
        this.asteroidTexture = new Texture("asteroid.png");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Asteroid a = activeList.get(i);
            batch.draw(asteroidTexture, a.getPosition().x - 128, a.getPosition().y - 128, 128, 128,
                    256, 256, a.getScale(),a.getScale(), a.getRotation() , 0,0, 256, 256, false, false);
        }
    }

    public void setup(float x, float y, float  vx, float vy, float azimuth, float scale, float rotation) {
        getActiveElement().activate(x, y, vx, vy, azimuth, scale, rotation);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
