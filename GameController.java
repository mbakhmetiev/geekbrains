package com.mygdx.game.game;

public class GameController {
    private Background background;
    private BulletController bulletController;
    private AsteroidController asteroidController;
    private Hero hero;
    private AsteroidSpawn asteroidSpawn;

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public GameController() {
        this.background = new Background(this);
        this.hero = new Hero(this);
        this.bulletController = new BulletController();
        this.asteroidController = new AsteroidController();
        this.asteroidSpawn = new AsteroidSpawn(this);
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
        bulletController.update(dt);
        asteroidSpawn.update(dt);
        asteroidController.update(dt);
        checkCollisions();
    }

    public void checkCollisions () {
        for (Bullet b : bulletController.getActiveList()) {
            for (Asteroid a : asteroidController.getActiveList()) {
                if (b.getPosition().dst(a.getPosition()) < (120.0f * a.getScale())) {
                    a.deactivate(); b.deactivate();
                }
            }
        }
    }
}
