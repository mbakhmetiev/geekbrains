package com.mygdx.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game.helper.ObjectPool;

public class CoinPackController extends ObjectPool<CoinPack> {
    private GameController gc;

    @Override
    protected CoinPack newObject() {
        return new CoinPack(gc);
    }

    public CoinPackController(GameController gc) {
        this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            CoinPack coinPack = activeList.get(i);
            coinPack.render(batch);
        }
    }

    public void setup(float x, float y, float vx, float vy, float scale, float coinPackProbo) {
        getActiveElement().activate(x, y, vx, vy, scale, coinPackProbo);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
