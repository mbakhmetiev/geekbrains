package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.screen.utils.Assets;

public class WorldRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private BitmapFont font32;
    private BitmapFont fontGreen32;
    private BitmapFont fontYellow32;
    private BitmapFont fontRed32;
    private StringBuilder stringBuilder;

    public WorldRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf", BitmapFont.class);
        this.fontGreen32 = Assets.getInstance().getAssetManager().get("fonts/fontGreen32.ttf", BitmapFont.class);
        this.fontYellow32 = Assets.getInstance().getAssetManager().get("fonts/fontYellow32.ttf", BitmapFont.class);
        this.fontRed32 = Assets.getInstance().getAssetManager().get("fonts/fontRed32.ttf", BitmapFont.class);
        this.stringBuilder = new StringBuilder();
    }

    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.2f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gc.getBackground().render(batch);
        gc.getHero().render(batch);
        gc.getAsteroidController().render(batch);
        gc.getBulletController().render(batch);
        gc.getParticleController().render(batch);
        gc.getHero().renderGui(batch, font32);
        gc.getHealPackController().render(batch);
        gc.getAmmoPackController().render(batch);
        gc.getCoinPackController().render(batch);
        batch.end();
    }
}
