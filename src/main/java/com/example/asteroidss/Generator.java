package com.example.asteroidss;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Generator {
    private Random rdm = new Random();
    public PlayerShip generatePlayerShip() {
        PlayerShip playerShip = new PlayerShip(Config.WIDTH/2, Config.HEIGHT/2);
        return playerShip;
    }

    public List<Asteroid> generateAsteroids(int currentLevel) {
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < currentLevel; i++) {
            Boolean collided = false;
            Asteroid newAsteroid = null;
            do {
                collided = false;
                newAsteroid = new Asteroid(this.rdm.nextInt(Config.WIDTH), this.rdm.nextInt(Config.HEIGHT), Size.LARGE);

                for (int j = 0; j < i; j++) {
                    Asteroid oldAsteroid = asteroids.get(j);
                    if (oldAsteroid.collided(newAsteroid)) {
                        collided = true;
                        break;
                    }
                }
            } while (collided);
            asteroids.add(newAsteroid);
        }
        return asteroids;
    }
    public Point2D generateSafePoint(List<Entity> enemies) {
        Boolean collided;
        double x;
        double y;
        do {
            collided = false;
            x = this.rdm.nextDouble() * Config.WIDTH;
            y = this.rdm.nextDouble() * Config.HEIGHT;
            PlayerShip playerShip = new PlayerShip(x, y);
            for (Entity enemy : enemies) {
                if (playerShip.collided(enemy)) {
                    collided = true;
                    break;
                }
            }
        } while (collided);
        return new Point2D(x, y);
    }
    public AlienShip generateAlienShip() {
        ObservableList<Double> points = new Polygon(-60, 0, -40, 20, 40, 20, 60, 0, 40, -20, 30, -20, 20, -35, -20, -35, -30, -20, -40, -20).getPoints();
        //even points which means x
        double max_X = 0;
        for (int i = 0; i < points.size(); i += 2) {
            if (points.get(i) > max_X) {
                max_X = points.get(i);
            }
        }
        double max_Y = 0;
        for (int i = 1; i < points.size(); i += 2) {
            if (points.get(i) > max_Y) {
                max_Y = points.get(i);
            }
        }
        double leftX = -max_X;
        double rightX = Config.WIDTH + max_X;
        double x = this.rdm.nextBoolean() ? leftX : rightX;
        double y = this.rdm.nextDouble() * Config.HEIGHT;//need to optimize to let it on screen....
        Point2D speed = new Point2D(((x > 0) ? -10 : 10), 0);
        return new AlienShip(x, y, speed);
    }
    public AlienShip generateAlienShip(double possibility) {
        double rdmDouble = this.rdm.nextDouble();
        if (rdmDouble < (possibility/60)) {
            return generateAlienShip();
        }
        return null;
    }
}

