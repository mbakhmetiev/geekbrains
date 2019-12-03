package com.mygdx.game.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screen.ScreenManager;

public class AsteroidSpawn {
    private GameController gc;
    private Vector2 position;
    private Vector2 velocity;
    private float azimuth;
    private float scale;
    private float rotation;
    private float spawnTimer;
    private int baseSpeed;

    public AsteroidSpawn(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.azimuth = 0.0f;
        this.scale = 0.0f;
        this.rotation = 0.0f;
        this.baseSpeed = 200;
    }

    public void update(float dt) {
        spawnTimer += dt;
        if (spawnTimer > 1.0f) {
            spawnTimer = 0.0f;

            // Астероиды вылетают из-за правого края экрана
            position.x = MathUtils.random(ScreenManager.SCREEN_WIDTH + 200, ScreenManager.SCREEN_WIDTH + 300);
            position.y = MathUtils.random(50, ScreenManager.SCREEN_HEIGTH - 50);

            // Летят под рандомными углами в диапазоне 140 - 220
            azimuth = MathUtils.random(140.0f, 220.0f);

            // Астероиды имеют разный размер 0.3 - 1 от текстуры
            scale = MathUtils.random(0.3f, 1.0f);

            // Скорсть вращения зависит от размера
            // Скрость тоже зависит от размера - baseSpeed умножается на коэффициент (2 - scale)
            rotation = 1 / scale;
            gc.getAsteroidController().setup(position.x, position.y, (float) Math.cos(Math.toRadians(azimuth)) * (baseSpeed * (2 - scale)) + velocity.x,
                    (float) Math.sin(Math.toRadians(azimuth)) * (baseSpeed * (2 - scale)) + velocity.y, 0.0f, scale, rotation);
        }
    }
}
