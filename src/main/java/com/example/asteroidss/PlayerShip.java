package com.example.asteroidss;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class PlayerShip extends Entity {

public PlayerShip(double x, double y) {
    super();
    this.setComponent(new Polygon(-10, 30, 10, 30, 0, 0));
    this.getComponent().setTranslateX(x);
    this.getComponent().setTranslateY(y);
    this.getComponent().setFill(Color.BLACK);
    this.setSpeed(new Point2D(0, 0));
}
    //    public PlayerShip(Point2D point2D) {
//        PlayerShip();
//    }
    public Bullet fire() {
        Rotate rotate = new Rotate(this.getComponent().getRotate());
        Point2D speed = rotate.transform(new Point2D(0, -Config.bulletSpeed));
        speed = speed.add(this.getSpeed());
        return new Bullet(this.getPosition(), speed);
    }
    public void applyThrust() {
        Rotate rotate = new Rotate(this.getComponent().getRotate());
        Point2D thrust = rotate.transform(Config.playerShipUnitThrust);

        this.changeSpeed(thrust);
    }
    // Hyperspace Jump Disappear from current location and reappear in a new location on the screen
    // (The new location should not be in contact with another object) ----checked in game
    public void hyperSpaceJump(Point2D newPosition) {//is it contained in travel distance?
        this.getComponent().setTranslateX(newPosition.getX());
        this.getComponent().setTranslateY(newPosition.getY());
    }

}

