package mz.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mz.backgrounds.Level1;
import mz.game.Game;
import mz.periferals.EnemyGroup;
import mz.sprites.Bullet;
import mz.sprites.Enemy;
import mz.sprites.GeneralSprite;
import mz.sprites.Player;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameScene extends GeneralScene{
    private Player player;
    private Level1 bgL1;
    private ArrayList<String> enemyArmy;
    private ArrayList<Enemy> enemies;

    private EnemyGroup enemyGroup;
    private ArrayList<Enemy> enemies2;

    private ArrayList<Bullet> bullets;

    private double time = 0;
    private double enemyTime = 0;

    public GameScene(){
        player = new Player("SpaceShip.png",50,73,true,true);
        bgL1 = new Level1("Space_bg.png");

        enemyGroup = new EnemyGroup();
        enemies2 = enemyGroup.groupL1_1();

        enemyArmy = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        enemyArmy.add("Enemies/Spaceship-Drakir1.png");
        enemyArmy.add("Enemies/Spaceship-Drakir2.png");
        enemyArmy.add("Enemies/Spaceship-Drakir3.png");
        enemyArmy.add("Enemies/Spaceship-Drakir4.png");
        enemyArmy.add("Enemies/Spaceship-Drakir5.png");
        enemyArmy.add("Enemies/Spaceship-Drakir6.png");
        enemyArmy.add("Enemies/Spaceship-Drakir7.png");
    }

    public void generateEnemies(){
        enemyTime += 0.02;
        if (enemyTime > 1) {



            int enemyRandom = (int) (Math.round(Math.random() * 6));
            Enemy newEnemy = new Enemy(enemyArmy.get(enemyRandom));
            double enemyWidth = newEnemy.getWidth();
            double enemyheight = newEnemy.getHeight();


            newEnemy.setY(0-(int)newEnemy.getHeight());
            newEnemy.setX((int) Math.round(Math.random()*(GameScene.GAME_WIDTH - (int)newEnemy.getWidth())));
            enemies.add(newEnemy);

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
                showDevInfo();
                player.draw(gc);
               // generateEnemies();
                drawEnemies(gc);
                drawBullets(gc);

                keyPressHandler(this);
                collisionDetect();
            }
        }.start();
    }

    private void collisionDetect() {
        for(Bullet bullet: bullets){
            for (GeneralSprite enemy: enemies){
                if (isColide(bullet,enemy)){
                    System.out.println("BOOOOOM!");
                    enemy.setAlive(false);
                    bullet.setAlive(false);
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
        for(Bullet bullet: bullets){
            bullet.draw(gc);
            bullet.moveUp();
            if (bullet.getY() < 0-bullet.getHeight()){
                bullet.setAlive(false);
            }
        }

        ListIterator<Bullet> aliveBullets = bullets.listIterator();
        while (aliveBullets.hasNext()){
            if (!aliveBullets.next().isAlive()){
                aliveBullets.remove();
            }
        }
    }

    private void drawEnemies(GraphicsContext gc) {
        if (enemies.size() == 0) return;
        for(Enemy enemy: enemies2){
            enemy.draw(gc);
            enemy.moveDown();
        }

        ListIterator<Enemy> aliveEnemies = enemies2.listIterator();
        while (aliveEnemies.hasNext()){
            if (!aliveEnemies.next().isAlive()){
                aliveEnemies.remove();

            }
        }
    }

    public void showDevInfo(){
        Font font = Font.font("Arial", FontWeight.NORMAL,12);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        String info = "Player x: " + player.getX() + ", y: " + player.getY();
        gc.fillText(info, 0,12);
        String bulletList = "Bullet list size:  " + bullets.size();
        gc.fillText(bulletList, 0,24);
    }

    public void keyPressHandler(AnimationTimer timer){
        if(activeKeys.contains(KeyCode.W)){
            player.move(0,-5);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.S)){
            player.move(0,5);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.A)) {
            player.move(-5,0);
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
        }
        if(activeKeys.contains(KeyCode.D)){
            System.out.println("Player position: X = " + player.getX() + ", Y = " + player.getY());
            player.move(5,0);
        }
        if (activeKeys.contains(KeyCode.SPACE)){
            if(time > 0.3) {
                Bullet bullet = new Bullet("weapon/laserGreen1.png");

                bullet.setX(player.getX() + (int)player.getWidth() / 2);
                bullet.setY(player.getY());
                bullets.add(bullet);
                time = 0;
            }
        }
        if (activeKeys.contains(KeyCode.ESCAPE)){
            timer.stop();
            Game.exit();
        }
    }
}
