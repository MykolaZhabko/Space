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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

public class GameScene extends GeneralScene implements GameConstants {
    private Player player;
    private Level1 bgL1;
    private ArrayList<Enemy> enemies;
    public static SoundManager soundManager;

    public static ArrayList<Weapon> playerWeapons;
    public static ArrayList<Weapon> enemyWeapons;

    private Queue<Enemy> allEnemies;

    private double time = 0;
    private double enemyTime = 0;

    public GameScene() throws InterruptedException {
        player = new Player(playerLevel1,50,73,true,true);
        bgL1 = new Level1("Space_bg.png");

        soundManager = new SoundManager(10);
        soundManager.loadSoundEffects("laser1", "accets/Sounds/weaponfire6.wav");
        soundManager.loadSoundEffects("laser2","accets/Sounds/laser6.wav");
        soundManager.loadSoundEffects("explosion","accets/Sounds/explosion4.wav");
        soundManager.loadSoundEffects("explosionFighter","accets/Sounds/explosion2.wav");

        allEnemies = new LinkedList<>();
        for (int i = 0; i<140;i++){

            Enemy newEnemy = new Enemy("Enemies/Spaceship-Drakir1.png",1);
            newEnemy.setY(0-(int)newEnemy.getHeight());
            newEnemy.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getWidth())));
            allEnemies.add(newEnemy);
        }

        enemies = new ArrayList<>();
        playerWeapons = new ArrayList<>();
        enemyWeapons = new ArrayList<>();
    }

    public void generateEnemies(){
        enemyTime += 0.02;
        if (enemyTime > 2 && enemies.size() < 6 && allEnemies.size() > 0) {
            enemies.add(allEnemies.poll());
            enemyTime = 0;
        }
    }



    @Override
    public void draw() { 
        activeKeys.clear();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.016;
                bgL1.draw(gc);

                //showDevInfo();

                player.draw(gc);
                generateEnemies();
                drawEnemies(gc);
                drawBullets(gc);
                removeDead();
                keyPressHandler(this);
                collisionDetect();
            }
        }.start();
    }

    private void collisionDetect() {
        if(player.isAlive()) {
            for (Weapon weapon : playerWeapons) {
                for (GeneralSprite enemy : enemies) {
                    if (isColide(weapon, enemy)) {
                        enemy.setHp(enemy.getHp() - weapon.getDamage());
                        weapon.setAlive(false);
                        if (enemy.getHp() <= 0) {
                            enemy.setAlive(false);
                            soundManager.playSound("explosion");
                        }

                    }
                }

            }

        for (Weapon weapon: enemyWeapons){
            if (isColide(weapon,player)){
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

    private boolean isColide(GeneralSprite a, GeneralSprite b){
        return  a.getX() >= b.getX() &&
                a.getX() <= b.getX() + b.getWidth() &&
                a.getY() >= b.getY() &&
                a.getY() <= b.getY() + b.getHeight();
    }

    private void drawBullets(GraphicsContext gc) {
        for(Weapon weapon : playerWeapons){
            weapon.draw(gc);
            weapon.moveUp();
            if (weapon.getY() < 0- weapon.getHeight()){
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

            if(time > 0.3 && player.isAlive()) {
                    soundManager.playSound("laser1");
                Weapon weapon = new Weapon("Player.weapon/laserGreen1.png",6,32,true,true,10);


                weapon.setX(player.getX() + (int)player.getWidth() / 2);
                weapon.setY(player.getY());
                playerWeapons.add(weapon);
                time = 0;
            }
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
    }

}
