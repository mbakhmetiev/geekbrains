package com.mygdx.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game.helper.Poolable;
import com.mygdx.game.screen.ScreenManager;
import com.mygdx.game.screen.utils.Assets;

public class AmmoPack implements Poolable {

    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;
    private float scale;
    private float ammoPackProbo;
    private float timeToLive;
    private Circle hitArea;

    private final float BASE_SIZE = 130.0f;
    private final float BASE_RADIUS = BASE_SIZE / 2.0f;

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public GameController getGc() { return gc; }

    public Vector2 getPosition() { return position; }

    public float getScale() { return scale; }

    public Circle getHitArea() { return hitArea; }

    public float getAmmoPackProbo() { return ammoPackProbo; }

    public AmmoPack(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Circle(0, 0, 0);
        this.active = false;
        this.texture = Assets.getInstance().getAtlas().findRegion("bullet1");
        this.timeToLive = 100;
    }

    public void activate(float x, float y, float vx, float vy, float scale, float ammoPackProbo) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.angle = MathUtils.random(0.0f, 360.0f);
        this.hitArea.setPosition(position);
        this.active = true;
        this.scale = scale;
        this.hitArea.setRadius(BASE_RADIUS * scale * 0.9f);
        this.ammoPackProbo = ammoPackProbo;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 128, position.y - 128, 128, 128, 256, 256, scale, scale, angle);
    }

    public void update(float dt) {
        timeToLive --;
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);
        if (timeToLive == 0) deactivate();
        if (position.x < -BASE_RADIUS || position.y < -BASE_RADIUS || position.y > ScreenManager.SCREEN_HEIGTH + BASE_RADIUS) {
            deactivate();
        }
    }
}
