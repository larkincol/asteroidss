package com.example.asteroidss;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
public class Config {
    public enum SCREEN {

    }
    public static final int PLAYER_MAX_LIVES = 3;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    //    public static Polygon playerShip = new Polygon(-10, 30, 10, 30, 0, 0);
//    public static Point2D playerShipPosition = new Point2D(500, 400);
    public static Point2D playerShipUnitThrust = new Point2D(0, -0.1);
    //    public static Circle bullet = new Circle(0, 0, 5);
    public static double bulletSpeed = 5;
    public static double bulletMaxDistance = 800;
    //    public static final Polygon alienShip = new Polygon(-60, 0, -40, 20, 40, 20, 60, 0, 40, -20, 30, -20, 20, -35, -20, -35, -30, -20, -40, -20);
    public static double alienShipSpeed = 10;
    public static Point2D asteroidSpeed = new Point2D(0, 5);
    public static final int PLAYER_ROTATE_DELTA = 5;
    public static long HYPER_SPACE_JUMP_CD = 280_000_000;
    public static long FIRE_CD = 280_000_000;
    public static double BASE_POSSIBILITY = 0.05;
}

