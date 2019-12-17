package com.mygdx.game.game;

import com.badlogic.gdx.math.Vector2;

public class GameController {
    private Background background;
    private BulletController bulletController;
    private AsteroidController asteroidController;
    private ParticleController particleController;
    private HealPackController healPackController;
    private AmmoPackController ammoPackController;
    private CoinPackController coinPackController;
    private Hero hero;
    private AsteroidSpawn asteroidSpawn;
    private Vector2 tmpVec;

    public Background getBackground() {
        return background;
    }

    public Hero getHero() { return hero; }

    public BulletController getBulletController() {
        return bulletController;
    }

    public AsteroidController getAsteroidController() { return asteroidController; }

    public HealPackController getHealPackController() { return healPackController; }

    public AmmoPackController getAmmoPackController() { return ammoPackController; }

    public CoinPackController getCoinPackController() { return coinPackController; }

    public ParticleController getParticleController() { return particleController; }

    public GameController() {
        this.background = new Background(this);
        this.hero = new Hero(this);
        this.bulletController = new BulletController();
        this.asteroidController = new AsteroidController(this );
        this.healPackController = new HealPackController(this );
        this.ammoPackController = new AmmoPackController(this );
        this.coinPackController = new CoinPackController(this );
        this.asteroidSpawn = new AsteroidSpawn(this);
        this.particleController = new ParticleController();
        this.tmpVec = new Vector2(0.0f, 0.0f);
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
        bulletController.update(dt);
        asteroidSpawn.update(dt);
        asteroidController.update(dt);
        particleController.update(dt);
        healPackController.update(dt);
        ammoPackController.update(dt);
        coinPackController.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for(int i = 0; i < asteroidController.getActiveList().size(); i++) {
            Asteroid a = asteroidController.getActiveList().get(i);
            if(a.getHitArea().overlaps(hero.getHitArea())) {
                float dst = a.getPosition().dst(hero.getPosition());
                float halfOverLen = (a.getHitArea().radius + hero.getHitArea().radius - dst) / 2.0f;
                tmpVec.set(hero.getPosition()).sub(a.getPosition()).nor();
                hero.getPosition().mulAdd(tmpVec, halfOverLen);
                a.getPosition().mulAdd(tmpVec, -halfOverLen);

                float sumScl = hero.getHitArea().radius *2 + a.getHitArea().radius;

                hero.getVelocity().mulAdd(tmpVec, 400.0f * halfOverLen * a.getHitArea().radius / sumScl);
                a.getVelocity().mulAdd(tmpVec, 400.0f * -halfOverLen * hero.getHitArea().radius / sumScl);

                if(a.takeDamage(1)) {
                    hero.addScore(a.getHpMax() * 10);
                }
                hero.takeDamage( a.getScale());

            }
        }

        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);
            for(int j = 0; j < asteroidController.getActiveList().size(); j++) {
                Asteroid a = asteroidController.getActiveList().get(j);
                if(a.getHitArea().contains(b.getPosition())) {
                    b.deactivate();
                    if(a.takeDamage(1)) {
                        hero.addScore(a.getHpMax() * 100);
                    }
                    break;
                }
            }

        }

        for (int i = 0; i < healPackController.getActiveList().size(); i++) {
            HealPack healPack = healPackController.getActiveList().get(i);
            if(healPack.getHitArea().overlaps(hero.getHitArea())) {
                hero.healUp(healPack.getHealPackProbo());
                healPack.deactivate();
            }
        }

        for (int i = 0; i < ammoPackController.getActiveList().size(); i++) {
            AmmoPack ammoPack = ammoPackController.getActiveList().get(i);
            if(ammoPack.getHitArea().overlaps(hero.getHitArea())) {
                hero.ammoUp(ammoPack.getAmmoPackProbo());
                ammoPack.deactivate();
            }
        }

        for (int i = 0; i < coinPackController.getActiveList().size(); i++) {
            CoinPack coinPack = coinPackController.getActiveList().get(i);
            if(coinPack.getHitArea().overlaps(hero.getHitArea())) {
                hero.coinUp(1.0f);
                coinPack.deactivate();
            }
        }
    }
}
