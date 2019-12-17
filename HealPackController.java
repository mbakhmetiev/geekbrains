package com.mygdx.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.game.helper.ObjectPool;

public class HealPackController extends ObjectPool<HealPack> {
    private GameController gc;

    @Override
    protected HealPack newObject() {
        return new HealPack(gc);
    }

    public HealPackController(GameController gc) {
        this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            HealPack healPack = activeList.get(i);
            healPack.render(batch);
        }
    }

    public void setup(float x, float y, float vx, float vy, float scale, float healPackProbo) {
        getActiveElement().activate(x, y, vx, vy, scale, healPackProbo);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
