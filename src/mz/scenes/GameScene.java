package mz.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mz.game.Game;
import mz.sprites.Bullet;
import mz.sprites.Enemy;
import mz.sprites.GeneralSprite;
import mz.sprites.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;

public class GameScene extends GeneralScene{
    private Player player;
    private ArrayList<GeneralSprite> enemies;
    private ArrayList<Bullet> bullets;

    private double time = 0;

    public GameScene(){
        player = new Player("SpaceShip.png",50,73,false,true);
        initEnemies();
        bullets = new ArrayList<>();
    }

    public void initEnemies(){
        this.enemies = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            enemies.add(new Enemy("SpaceShip.png",50,73,false,false));
        }

        for (int i = 0; i < 5; i++) {
            enemies.get(i).setX(i*100);
            enemies.get(i).setY(100);
        }
    }



    @Override
    public void draw() {
        activeKeys.clear();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.016;
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH, GAME_HEIGHT);

                showDevInfo();
                player.draw(gc);
                drawEnemyes(gc);
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
            bullet.moveDown();
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

    private void drawEnemyes(GraphicsContext gc) {
        for(GeneralSprite enemy: enemies){
            enemy.draw(gc);
        }

        ListIterator<GeneralSprite> aliveEnemies = enemies.listIterator();
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
            //bullet.draw(gc);
        }
        if (activeKeys.contains(KeyCode.ESCAPE)){
            timer.stop();
            Game.exit();
        }
    }
}
