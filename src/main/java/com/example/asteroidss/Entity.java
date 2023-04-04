package com.example.asteroidss;


import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The basic entity in the game
 */

public class Entity {
    //abstract class?
    // the shape of the entity (including the position)
    private Shape component;
    // the movement in a unit time
    // e.g. if speedX = 1, speedY = 1, after a unit time, the entity move 1 to the right and 1 to the bottom (Cartesian coordinate system)
    private Point2D speed;
    // the status of the entity
    private boolean alive = true;
    // the distance travelled since born
//    public double distanceTravelled = 0;
    //Constructor
    public Entity() {
    }

    public Entity(Shape component, Point2D speed, boolean alive, double distanceTravelled) {
        this.component = component;
        this.speed = speed;
        this.alive = alive;
    }

//methods
    //1.getters and setters

    public Shape getComponent() {
        return component;
    }

    public void setComponent(Shape component) {
        this.component = component;
    }

    public Point2D getSpeed() {
        return speed;
    }

    public void setSpeed(Point2D speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //get the position
    public Point2D getPosition() {
        return new Point2D(this.component.getTranslateX(), this.component.getTranslateY());
    }

    //2.move
    // default move
    public void move() {
        this.move(this.speed.getX(), this.speed.getY());
    }
    // specified move
    public void move(double x, double y) {
        this.component.setTranslateX(this.component.getTranslateX() + x);
        this.component.setTranslateY(this.component.getTranslateY() + y);

//        this.distanceTravelled += (new Point2D(x, y)).distance(new Point2D(0, 0));

        //consider the situation that entity move out of the screen....
        if (this.component.getTranslateX() > Config.WIDTH) {
            this.component.setTranslateX(this.component.getTranslateX() % Config.WIDTH);
        }
        // e.g x = -1, width = 3, x should be 3 - (1 % 3) = 2
        if (this.component.getTranslateX() < 0) {
            this.component.setTranslateX(Config.WIDTH - ((-this.component.getTranslateX()) % Config.WIDTH));
        }
        if (this.component.getTranslateY() > Config.HEIGHT) {
            this.component.setTranslateY(this.component.getTranslateY() % Config.HEIGHT);
        }
        if (this.component.getTranslateY() < 0) {
            this.component.setTranslateY(Config.HEIGHT - ((-this.component.getTranslateY()) % Config.HEIGHT));
        }
    }
    //3.rotate
    public void rotateRight(int delta) {
        this.component.setRotate(this.component.getRotate() + delta);
    }
    public void rotateLeft(int delta) {
        this.component.setRotate(this.component.getRotate() - delta);
    }
    //    public void rotate(int delta) {
//        this.component.setRotate(this.component.getRotate() + delta);
//    }
    //4.change the speed
    public void changeSpeed(Point2D variation) {
        this.speed = this.speed.add(variation);
    }
    //5.check collision
    public  boolean collided(Entity other) {
        return this.component.getBoundsInParent().intersects(other.component.getBoundsInParent());
    }
}


