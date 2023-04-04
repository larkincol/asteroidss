package com.example.asteroidss;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class AlienShip extends Entity {

    private double distanceTravelled_X = 0;
    public AlienShip(double x, double y, Point2D speed) {
        super();
        this.setComponent(new Polygon(-60, 0, -40, 20, 40, 20, 60, 0, 40, -20, 30, -20, 20, -35, -20, -35, -30, -20, -40, -20));
        this.getComponent().setTranslateX(x);
        this.getComponent().setTranslateY(y);
        this.getComponent().setFill(Color.BLACK);
        //speed
        this.setSpeed(speed);
    }
    public Bullet fire(PlayerShip playerShip) {
        Point2D playerShipPosition = playerShip.getPosition();
        Point2D alienShipPosition = this.getPosition();
        Point2D bulletDirection = playerShipPosition.subtract(alienShipPosition).normalize();
        Point2D speed = bulletDirection.multiply(Config.bulletSpeed);
        return new Bullet(alienShipPosition, speed);
    }

    @Override
    public void move(double x, double y) {
        this.distanceTravelled_X += x;
        super.move(x, y);
        if (Math.abs(this.distanceTravelled_X) > Config.WIDTH) {
            this.setAlive(false);
        }
    }
}



