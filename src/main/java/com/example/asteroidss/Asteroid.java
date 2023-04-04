package com.example.asteroidss;



import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.Random;

public class Asteroid extends Entity{
    /**
     * -	Three types (large, medium, small)
     * -	large : setsize(large), move(slow) , separate(),
     * -	Medium : setsize(medium), move(medium)
     * -	small : setsize(small), move(fast)
     */
    private Size size;
    private double scaler;
    public Asteroid(double x, double y, Size size) {
        super();
        this.setComponent(PolygonGenerator.generate());
        this.getComponent().setTranslateX(x);
        this.getComponent().setTranslateY(y);
        this.size = size;
        setSize();
        this.getComponent().setFill(Color.BLACK);
        setRotate();
        setSpeed();

    }
    private void setSize() {
        switch (this.size) {
            case SMALL:
                this.scaler = 0.2;
                break;
            case MEDIUM:
                this.scaler = 0.5;
                break;
            case LARGE:
                this.scaler = 1;
                break;
            default:
                this.scaler = 0;
        }
        Polygon polygon = (Polygon) this.getComponent();
        for (int i = 0; i < polygon.getPoints().size(); i++) {
            polygon.getPoints().set(i, polygon.getPoints().get(i) * this.scaler);
        }
    }
// returns the size of the
    public Size getSize() {
        return this.size;
    }

  /*
   public List<Asteroid> split() {
        List<Asteroid> newAsteroids = new ArrayList<>();
        if (this.getSize() == Size.LARGE) {
            double x = this.getComponent().getTranslateX();
            double y = this.getComponent().getTranslateY();
            newAsteroids.add(new Asteroid(x, y, Size.MEDIUM));
            newAsteroids.add(new Asteroid(x, y, Size.MEDIUM));
        }
        return newAsteroids;
    }  */


        private void setRotate () {
            Random random = new Random();
            this.getComponent().setRotate(random.nextInt(360));
        }
        private void setSpeed () {
            Random random = new Random();
            double speedScale;
            switch (this.size) {
                case SMALL:
                    speedScale = 1.5; // small asteroids move faster
                    break;
                case MEDIUM:
                    speedScale = 1.0; // medium asteroids move at base speed
                    break;
                case LARGE:
                    speedScale = 0.5; // large asteroids move slower
                    break;
                default:
                    speedScale = 1.0; // default to base speed
            }
            Point2D scaledSpeed = Config.asteroidSpeed.multiply(speedScale);
            Rotate rotate = new Rotate(random.nextInt(360));
            this.setSpeed(rotate.transform(scaledSpeed));
        }
    }





