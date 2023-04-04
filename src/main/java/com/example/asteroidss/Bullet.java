package com.example.asteroidss;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Bullet extends Entity {
    /**
     *
     */
    private double distanceTravelled = 0;
    public Bullet(Point2D position, Point2D speed) {
        super();
        this.setComponent(new Circle(0, 0, 3));
        this.getComponent().setTranslateX(position.getX());
        this.getComponent().setTranslateY(position.getY());
        this.getComponent().setFill(Color.BLACK);
        this.setSpeed(speed);
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    @Override
    public void move(double x, double y) {
        this.distanceTravelled += (new Point2D(x, y)).distance(new Point2D(0, 0));
        super.move(x, y);
        if (this.distanceTravelled > Config.bulletMaxDistance) {
            this.setAlive(false);
        }
    }

}
