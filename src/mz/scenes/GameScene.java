package mz.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mz.backgrounds.Level1;
import mz.game.Game;
import mz.periferals.GameConstants;
import mz.periferals.SoundManager;
import mz.sprites.*;

import java.util.*;

public class GameScene extends GeneralScene implements GameConstants {
    public static Player player;
    private Level1 bgL1;
    private ArrayList<Enemy> enemies;
    public static SoundManager soundManager;

    public static ArrayList<Weapon> playerWeapons;
    public static ArrayList<Weapon> enemyWeapons;
    public static ArrayList<FeatureDrop> featuresDrop;


    private double time = 0;
    private double enemyTime = 0;

    public GameScene() throws InterruptedException {
        player = new Player();
        bgL1 = new Level1("Space_bg.png");

        soundManager = new SoundManager(5);
        soundManager.loadSoundEffects("laser1", "accets/Sounds/weaponfire6.wav");
        soundManager.loadSoundEffects("laser2","accets/Sounds/laser6.wav");
        soundManager.loadSoundEffects("explosion","accets/Sounds/explosion4.wav");
        soundManager.loadSoundEffects("explosionFighter","accets/Sounds/explosion2.wav");


        enemies = new ArrayList<>();
        playerWeapons = new ArrayList<>();
        enemyWeapons = new ArrayList<>();
        featuresDrop = new ArrayList<>();

        setFill(Color.BLACK);

    }

    public void generateEnemies(){
        enemyTime += 0.02;
        if (enemyTime > 2 && enemies.size() < 6) {
            Enemy newEnemy = new Enemy(1);
            newEnemy.setY(0-(int)newEnemy.getSprite().getHeight());
            newEnemy.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getSprite().getWidth())));
            enemies.add(newEnemy);
            enemyTime=0;
        }
    }



    @Override
    public void draw() { 
        activeKeys.clear();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawUI();
                time += 0.016;
                bgL1.draw(gc);

                showDevInfo();

                player.draw(gc);
                generateEnemies();
                drawEnemies(gc);
                drawBullets(gc);
                drawFeaturesDrop();
                removeDead();
                keyPressHandler(this);
                collisionDetect();
            }
        }.start();
    }

    private void drawUI() {
        gcUi.drawImage(UI,0,0);
        for (int i = 0; i < player.getLives(); i++) {
            gcUi.drawImage(player.getSprite(),UI_WIDTH-player.getSprite().getWidth()*3+(i*player.getSprite().getWidth()),10,player.getSprite().getWidth()/2,player.getSprite().getHeight()/2);
        }
        Font font = Font.font("Arial", FontWeight.NORMAL,24);
        gcUi.setFont(font);
        gcUi.setFill(Color.WHITE);
        String score = "SCORE: " + player.getScore();
        gcUi.fillText(score, 0,24);

    }



    private void drawFeaturesDrop() {
        for (FeatureDrop drop: featuresDrop){
            drop.draw(gc);
            drop.collideWithPlayer(player);
        }
    }

    private void collisionDetect() {
        if(player.isAlive()) {
            for (Weapon weapon : playerWeapons) {
                for (GeneralSprite enemy : enemies) {
                    if (isCollide(weapon, enemy)) {
                        enemy.setHp(enemy.getHp() - weapon.getDamage());
                        weapon.setAlive(false);
                        if (enemy.getHp() <= 0) {
                            if (enemy.isAlive()) {
                                soundManager.playSound("explosion");
                                if (getRandom(2) == 1)
                                featuresDrop.add(new FeatureDrop(enemy.getX(), enemy.getY(), getRandom(4)));
                                enemy.setAlive(false);
                                player.setScore(player.getScore()+((Enemy) enemy).getPoints());
                            }
                        }

                    }
                }

            }

        for (Weapon weapon: enemyWeapons){
            if (isCollide(weapon,player)){
                player.setHp(player.getHp() - weapon.getDamage());
                weapon.setAlive(false);
                if (player.getHp() <= 0)
                {
                    player.setAlive(false);
                    soundManager.playSound("explosionFighter");
                }
            }
        }
        }

    }

    private boolean isCollide(GeneralSprite a, GeneralSprite b){
        return  a.getX() >= b.getX() &&
                a.getX() <= b.getX() + b.getSprite().getWidth() &&
                a.getY() >= b.getY() &&
                a.getY() <= b.getY() + b.getSprite().getHeight();
    }

    private void drawBullets(GraphicsContext gc) {
        for(Weapon weapon : playerWeapons){
            weapon.draw(gc);
            weapon.moveUp();
            if (weapon.getY() < 0- weapon.getSprite().getHeight()){
                weapon.setAlive(false);
            }
        }


        for(Weapon weapon : enemyWeapons){
            weapon.draw(gc);
            weapon.moveDown();
            if (weapon.getY() > GAME_HEIGHT){
                weapon.setAlive(false);
            }
        }
    }


    private void drawEnemies(GraphicsContext gc) {
        if (enemies.size() == 0) return;
        for(Enemy enemy: enemies){
            enemy.setShootTimer(enemy.getShootTimer() + Math.random()/10);
            if (enemy.getShootTimer() > 4) {
                enemy.shoot();
                enemy.setShootTimer(0);
            }
            enemy.draw(gc);
            enemy.move();
        }
    }

    public void showDevInfo(){
        Font font = Font.font("Arial", FontWeight.NORMAL,12);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        String info = "Player x: " + player.getX() + ", y: " + player.getY();
        gc.fillText(info, 0,12);
        String bulletList = "Bullet list size:  " + playerWeapons.size();
        gc.fillText(bulletList, 0,24);
        gc.fillText("Player HP: " + player.getHp(), 0,GAME_HEIGHT-12);
        gc.fillText("Feature list: " + featuresDrop.size(), 0,36);


        gc.setLineWidth(1.0);
        gc.strokeLine(0,GameScene.GAME_HEIGHT/2,GameScene.GAME_WIDTH,GameScene.GAME_HEIGHT/2);
    }

    public void keyPressHandler(AnimationTimer timer){
        if(activeKeys.contains(KeyCode.UP)){
            player.move(0,-5);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.DOWN)){
            player.move(0,5);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.LEFT)) {
            player.move(-5,0);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.RIGHT)){
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
            player.move(5,0);
        }
        if (activeKeys.contains(KeyCode.SPACE)){


                player.shoot();


        }
        if (activeKeys.contains(KeyCode.ESCAPE)){
            timer.stop();
            soundManager.shutdown();
            Game.exit();
        }
    }

    private void removeDead(){
        ListIterator<Enemy> aliveEnemies = enemies.listIterator();
        while (aliveEnemies.hasNext()){
            if (!aliveEnemies.next().getExplosion().isAlive()){
                aliveEnemies.remove();

            }
        }

        ListIterator<Weapon> aliveEnemyByllets = enemyWeapons.listIterator();
        while (aliveEnemyByllets.hasNext()){
            if (!aliveEnemyByllets.next().isAlive()){
                aliveEnemyByllets.remove();
            }
        }

        ListIterator<Weapon> aliveBullets = playerWeapons.listIterator();
        while (aliveBullets.hasNext()){
            if (!aliveBullets.next().isAlive()){
                aliveBullets.remove();
            }
        }

        ListIterator<FeatureDrop> drops = featuresDrop.listIterator();
        while (drops.hasNext()){
            if (!drops.next().isAlive()){
                drops.remove();
            }
        }
    }
    private int getRandom(int num) {
        int rnd = new Random().nextInt(num);
        return rnd;
    }
}
