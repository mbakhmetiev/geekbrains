package com.mygdx.game.game;

import com.badlogic.gdx.math.Circle;

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
        this.asteroidController = new AsteroidController(this );
        this.asteroidSpawn = new AsteroidSpawn(this);
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
        bulletController.update(dt);
        asteroidSpawn.update(dt);
        asteroidController.update(dt);
        checkHit(); checkCollisions();
    }

    public void checkHit() {
        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);
            for(int j = 0; j < asteroidController.getActiveList().size(); j++) {
                Asteroid a = asteroidController.getActiveList().get(j);
                if(a.getHitArea().contains(b.getPosition())) {
                    b.deactivate();
                    if(a.takeDamage(1)) {
                        hero.addScore(a.getHpMax() * 10);
                    }
                    break;
                }
            }
            
        }
    }

    public boolean overlapCheck (Circle hero, Circle astro) {
        float dx = hero.x - astro.x;
        float dy = hero.y - astro.y;
        float distance =  dx * dx + dy * dy;
        return (int) Math.sqrt(distance) <= (int) (hero.radius + astro.radius);
    }

    public void checkCollisions() {
            for(int i = 0; i < asteroidController.getActiveList().size(); i++) {
                Asteroid a = asteroidController.getActiveList().get(i);
                if(overlapCheck(this.hero.getHitArea(), a.getHitArea())) {
                    a.Bounce(150f, hero.getVelocity());
                    hero.Bounce( 0f, a.getVelocity());
                    hero.takeDamage( 1.0f * a.getScale());
                }
            }
    }
}

