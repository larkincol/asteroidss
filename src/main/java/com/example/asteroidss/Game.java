package com.example.asteroidss;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.stream.Collectors;

public class Game {
    private Stage stage;
    private int playerLives;
    private int currentLevel;

    private Generator generator;
    private PlayerShip playerShip;

    public Game(Stage stage) {
        this.stage = stage;
        this.playerLives = Config.PLAYER_MAX_LIVES;
        this.currentLevel = 1;
        this.generator = new Generator();
        this.playerShip = this.generator.generatePlayerShip();
    }

    public void start() {
        //1.welcome scene and Menu
        //2.Level scene
//        levelScene();
        //3.Game scene
        gameScene();
        //4.Score scene and High scores
    }
    public void levelScene() {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, Config.WIDTH, Config.HEIGHT);
        Text text = new Text("Level" + currentLevel);
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 100));
        text.setTranslateX(Config.WIDTH/2);
        text.setTranslateY(Config.HEIGHT/2);
        root.getChildren().add(text);
        scene.setOnKeyPressed((event) -> gameScene());
        stage.setScene(scene);
        stage.show();
    }
    public void gameOverScene() {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, Config.WIDTH, Config.HEIGHT);
        Text text = new Text("Game Over");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 100));
        text.setTranslateX(Config.WIDTH/2);
        text.setTranslateY(Config.HEIGHT/2);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.show();
    }
    public void gameScene() {
        //1.Create a new scene
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, Config.WIDTH, Config.HEIGHT);

        //2.Create Arraylist of keys that are currently being pressed
        ArrayList<KeyCode> pressedKeys = new ArrayList<>();
        //Event Listener attached to the main scene, adds a key to an arraylist when pressed
        scene.setOnKeyPressed((KeyEvent key) -> {
            // if condition to prevent duplicates
            if (!pressedKeys.contains(key.getCode())) {pressedKeys.add(key.getCode());}
        });
        // Event Listener, removes a key from arraylist when released
        scene.setOnKeyReleased((KeyEvent key) -> pressedKeys.remove(key.getCode()));

        //3.create entities
        //3.1 create PlayerShip
        root.getChildren().add(playerShip.getComponent());
        //3.2 create AlienShips list
        List<AlienShip> alienShips = new ArrayList<>();
        //3.3 create Asteroids list (level 1 - 1 Asteroid, level 2- 2 Asteroids....)
        List<Asteroid> asteroids = generator.generateAsteroids(currentLevel);
        for (Asteroid asteroid: asteroids) {
            root.getChildren().add(asteroid.getComponent());
        }
        //create Player Bullets
        List<Bullet> playerBullets = new ArrayList<>();
        //create Alien Bullets
        List<Bullet> alienBullets = new ArrayList<>();
        //create enemy list to hold all the enemies. Helpful to calculate safe location
        List<Entity> enemies = getEnemies(asteroids, alienShips, alienBullets);

        //4.Start AnimationTimer
        new AnimationTimer() {
            private long lastHyperSpaceJump = 0;
            private long lastFire = 0;
            private long lastGenerateAlienShip = 0;
            private long lastAlienShipFire = 0;
            @Override
            public void handle(long now) {
                //1. Reaction to keys
                if (pressedKeys.contains(KeyCode.LEFT)) {
                    playerShip.rotateLeft(Config.PLAYER_ROTATE_DELTA);
                }
                if (pressedKeys.contains(KeyCode.RIGHT)) {
                    playerShip.rotateRight(Config.PLAYER_ROTATE_DELTA);
                }
                if (pressedKeys.contains(KeyCode.SPACE ) && ((now - lastFire) > Config.FIRE_CD)) {
                    Bullet bullet = playerShip.fire();
                    playerBullets.add(bullet);
                    root.getChildren().add(bullet.getComponent());
                    lastFire = now;
                }
                if (pressedKeys.contains(KeyCode.UP)) {
                    playerShip.applyThrust();
                }
                if (pressedKeys.contains(KeyCode.SHIFT) && ((now - lastHyperSpaceJump) > Config.HYPER_SPACE_JUMP_CD)) {
                    Point2D safePoint = generator.generateSafePoint(enemies);
                    playerShip.hyperSpaceJump(safePoint);
                    lastHyperSpaceJump = now;
                }
                //2.Automatic Events
                // AlienShip appears randomly according to possibility
                if (alienShips.size() == 0) {
                    double possibility = Config.BASE_POSSIBILITY * currentLevel;
                    AlienShip newAlienShip = generator.generateAlienShip(possibility);
                    if ((newAlienShip != null) && (now - lastGenerateAlienShip) > 300_000_000) {
                        alienShips.add(newAlienShip);
                        enemies.add(newAlienShip);
                        root.getChildren().add(newAlienShip.getComponent());
                        lastGenerateAlienShip = now;
                    }
                }
                //AlienShips fire
                for (AlienShip alienShip: alienShips) {
                    if ((now - lastAlienShipFire) > 800_000_000) {
                        Bullet bullet = alienShip.fire(playerShip);
                        alienBullets.add(bullet);
                        enemies.add(bullet);
                        root.getChildren().add(bullet.getComponent());
                        lastAlienShipFire = now;
                    }
                }
                //3. Automatic Move
                playerShip.move();
                for (AlienShip alienShip: alienShips) {alienShip.move();}
                for (Asteroid asteroid: asteroids) {asteroid.move();}
                for (Bullet bullet: playerBullets) {bullet.move();}
                for (Bullet bullet: alienBullets) {bullet.move();}
                //4. Check Collision
                //4.1 bullets shot
                //4.1.1 player bullets
                for (Bullet bullet: playerBullets) {
                    //asteroids




                    //

                      for (Asteroid existingAsteroid: asteroids) {
                        if (bullet.collided(existingAsteroid)) {
                            bullet.setAlive(false);
                            existingAsteroid.setAlive(false);//need to optimize: Large asteroids become 2 medium ones....
                            //need to add scores
                            //List<Asteroid> asteroids = new ArrayList<>();
                            //asteroids.add(new Asteroid(Config.WIDTH, Config.HEIGHT, Size.MEDIUM));

                            if (existingAsteroid.getSize() == Size.LARGE) {
                                // get the coordinates of the destroyed Asteroid
                                double x = existingAsteroid.getComponent().getTranslateX();
                                double y = existingAsteroid.getComponent().getTranslateY();


                                // Add two medium asteroids at the same location
                                List<Asteroid> newAsteroids = new ArrayList<>();
                                newAsteroids.add(new Asteroid(x, y, Size.MEDIUM));
                                newAsteroids.add(new Asteroid(x, y, Size.MEDIUM));

                                for (Asteroid asteroid : newAsteroids) {
                                    asteroids.add(asteroid);
                                    root.getChildren().add(asteroid.getComponent());
                                }
                            }
                                else if (existingAsteroid.getSize() == Size.MEDIUM)  {

                                    double x = existingAsteroid.getComponent().getTranslateX();
                                    double y = existingAsteroid.getComponent().getTranslateY();

                                // Add two medium asteroids at the same location
                                List<Asteroid> newAsteroids = new ArrayList<>();
                                newAsteroids.add(new Asteroid(x, y, Size.SMALL));
                                newAsteroids.add(new Asteroid(x, y, Size.SMALL));

                                for (Asteroid asteroid : newAsteroids) {
                                    asteroids.add(asteroid);
                                    root.getChildren().add(asteroid.getComponent());
                                }







                                }



                            break;






                            // split the large asteroid into two medium ones
                           // asteroid.split();
                        }
                          // add the new asteroids to the game
                      /*    for (Asteroid newAsteroid : newAsteroids) {
                              asteroids.add(newAsteroid);
                              root.getChildren().add(newAsteroid.getComponent());
                          }  */
                    }
                    //alienShips
                    for (AlienShip alienShip: alienShips) {
                        if (bullet.collided(alienShip)) {
                            bullet.setAlive(false);
                            alienShip.setAlive(false);
                            //need to add scores
                        }
                    }
                }
                //4.1.2 alien bullets
                for (Bullet bullet: alienBullets) {
                    if (bullet.collided(playerShip)) {
                        playerShip.setAlive(false);
                        bullet.setAlive(false);
                    }
                }
                //4.2 player and asteroids
                for (Asteroid asteroid: asteroids) {
                    if (playerShip.collided(asteroid)) {
                        playerShip.setAlive(false);
//                        asteroid.setAlive(false);
                    }
                }
                //4.3 player and alienShips
                for (AlienShip alienShip: alienShips) {
                    if (playerShip.collided(alienShip)) {
                        playerShip.setAlive(false);
                        alienShip.setAlive(false);
                    }
                }
                //5. Remove dead entities
                removeDeadEntites(asteroids, root);
                removeDeadEntites(alienShips, root);
                removeDeadEntites(playerBullets, root);
                removeDeadEntites(alienBullets, root);
                //6. Check the status of player
                if (!playerShip.isAlive()) {
                    root.getChildren().remove(playerShip.getComponent());
                    playerLives -= 1;
                    if (playerLives > 0) {
                        Point2D safePoint = generator.generateSafePoint(enemies);
                        playerShip = new PlayerShip(safePoint.getX(), safePoint.getY());
                        root.getChildren().add(playerShip.getComponent());
                    } else {
                        gameOverScene();
                        stop();
                    }
                }
                //7. Check if player go to next level
                if (asteroids.size() == 0) {
                    currentLevel += 1;//?
                    levelScene();
                    stop();
                }
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }
    private List<Entity> getEnemies(List<Asteroid> asteroids, List<AlienShip> alienShips, List<Bullet> alienBullets) {
        List<Entity> enemies = new ArrayList<>();
        enemies.addAll(asteroids);
        enemies.addAll(alienShips);
        enemies.addAll(alienBullets);
        return enemies;
    }
    private void removeDeadEntites(List<? extends Entity> deadEntityList, Pane root) {
        deadEntityList.stream()
                .filter(entity -> !entity.isAlive())
                .forEach(entity -> {root.getChildren().remove(entity.getComponent());});
        deadEntityList.removeAll(
                deadEntityList.stream()
                        .filter(entity -> !entity.isAlive())
                        .collect(Collectors.toList()));
    }

}

