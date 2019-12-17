package com.mygdx.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game.helper.ObjectPool;

public class AmmoPackController extends ObjectPool<AmmoPack> {
    private GameController gc;

    @Override
    protected AmmoPack newObject() {
        return new AmmoPack(gc);
    }

    public AmmoPackController(GameController gc) {
        this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            AmmoPack ammoPack = activeList.get(i);
            ammoPack.render(batch);
        }
    }

    public void setup(float x, float y, float vx, float vy, float scale, float ammoPackProbo) {
        getActiveElement().activate(x, y, vx, vy, scale, ammoPackProbo);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
