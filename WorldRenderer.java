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
        gc.getBulletController().render(batch);
        gc.getAsteroidController().render(batch);
        stringBuilder.clear();
        stringBuilder.append("SCORE: ").append(gc.getHero().getScoreView());
        font32.draw(batch, stringBuilder, 20, 700);
        stringBuilder.clear();

        if (gc.getHero().getHealth() <= 100.0f && gc.getHero().getHealth() > 30) {
            stringBuilder.append("HEALTH: ").append(Math.round(gc.getHero().getHealth()));
        fontGreen32.draw(batch, stringBuilder, 1000, 700);
        stringBuilder.clear();
        }
        if(gc.getHero().getHealth() <= 30.0f && gc.getHero().getHealth() > 15 ) {
            stringBuilder.append("HEALTH: ").append(Math.round(gc.getHero().getHealth()));
            fontYellow32.draw(batch, stringBuilder, 1000 , 700);
            stringBuilder.clear();
        }
        if(gc.getHero().getHealth() <= 15.0f && gc.getHero().getHealth() > 0 ) {
            stringBuilder.append("HEALTH: ").append(Math.round(gc.getHero().getHealth()));
            fontRed32.draw(batch, stringBuilder, 1000 , 700);
            stringBuilder.clear();
        }
        if(gc.getHero().getHealth() <= 0f) {
            stringBuilder.append("HEALTH: 0");
            fontRed32.draw(batch, stringBuilder, 1000 , 700);
            stringBuilder.clear();
        }
        batch.end();
    }
}
