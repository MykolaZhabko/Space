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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class GameScene extends GeneralScene implements GameConstants, Serializable {
    private Player player;
    private Level1 bgL1;
    private boolean gameOver;
    private boolean pause;


    public static long start;
    public static long finish;
    public static long timeElapsed;

    private ArrayList<Enemy> enemies;
    public static SoundManager soundManager;

    public static ArrayList<Weapon> playerWeapons;
    public static ArrayList<Weapon> enemyWeapons;
    public static ArrayList<FeatureDrop> featuresDrop;



    private double enemyTime = 0;

    public GameScene() {
        player = new Player();
        bgL1 = new Level1("Space_bg.png");

        soundManager = new SoundManager(2);

        setGameOver(false);
        setPause(false);
        enemies = new ArrayList<>();
        playerWeapons = new ArrayList<>();
        enemyWeapons = new ArrayList<>();
        featuresDrop = new ArrayList<>();

        setFill(Color.BLACK);

    }

    public static long getTimeElapsed() {
        return timeElapsed;
    }

    public static void setTimeElapsed(long timeElapsed) {
        GameScene.timeElapsed = timeElapsed;
    }

    public static long getStart() {
        return start;
    }

    public static void setStart(long start) {
        GameScene.start = start;
    }

    public static long getFinish() {
        return finish;
    }

    public static void setFinish(long finish) {
        GameScene.finish = finish;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void generateEnemies(){
        enemyTime += 0.02;
        if (enemyTime > 2 && enemies.size() < 6) {
            Enemy newEnemy = new Enemy(1);
            newEnemy.setY(0-(int)newEnemy.getSprite().getHeight());
            newEnemy.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getSprite().getWidth())));
            enemies.add(newEnemy);
            enemyTime=0;
            if (player.getScore() > 100 && getRandom(2) == 1){
                Enemy newEnemy2 = new Enemy(2);
                newEnemy2.setY(0-(int)newEnemy.getSprite().getHeight());
                newEnemy2.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getSprite().getWidth())));
                enemies.add(newEnemy2);
            }
        }
    }



    @Override
    public void draw() {
        setStart(System.currentTimeMillis());
        activeKeys.clear();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawUI();
                if((player.isAlive() || player.getLives() > 0 || player.getExplosion().isAlive()) && !isPause()){
                    bgL1.draw(gc);
                    //showDevInfo();
                    player.draw(gc);
                    generateEnemies();
                    drawEnemies(gc);
                    drawBullets(gc);
                    drawFeaturesDrop();
                    removeDead();
                    keyPressHandler(this);
                    collisionDetect();
                }else if (isPause()){
                    drawGamePause();
                    keyPressHandler(this);
                } else {
                    if(!isGameOver() && !isPause()) {
                        setGameOver(true);
                        setFinish(System.currentTimeMillis());
                        setTimeElapsed(getStart()-getFinish());
                        writeToArchive();
                    }
                    drawGameOver();
                    keyPressHandler(this);
                }
            }
        }.start();
    }

    private void writeToArchive() {
        //creating a jason object using maven dep.-> check pom.xml
        JSONParser jsonParser = new JSONParser();

        JSONObject jobj = new JSONObject();
        jobj.put("date",new Date().toString());
        jobj.put("score",player.getScore());
        jobj.put("time",getTimeElapsed());

        try (FileReader reader = new FileReader("arch.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray arch = (JSONArray) obj;
            System.out.println(arch);


        } catch (FileNotFoundException e) {
            try {
                FileWriter fileWriter = new FileWriter("arch.json",true);
                fileWriter.append(jobj.toString());
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void drawGameOver() {
        Font font = Font.font("Arial",FontWeight.NORMAL,24);
        gc.setFont(font);
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER",GAME_WIDTH/2 -240,GAME_HEIGHT/2);
        gc.setFill(Color.WHITE);
        gc.fillText("TIME: " + (getTimeElapsed() * (-1) / 1000) + " seconds",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 24);
        gc.fillText("SCORE: " + player.getScore(),GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 48);
        font = Font.font("Arial",FontWeight.NORMAL,12);
        gc.setFont(font);
        gc.setFill(Color.LAVENDERBLUSH);
        gc.fillText("To restart the game press SPACE.... ",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 96);

    }

    private void drawGamePause() {
        Font font = Font.font("Arial",FontWeight.NORMAL,24);
        gc.setFont(font);
        gc.setFill(Color.RED);
        gc.fillText("PAUSE",GAME_WIDTH/2 -240,GAME_HEIGHT/2);
        gc.setFill(Color.WHITE);
        font = Font.font("Arial",FontWeight.NORMAL,24);
        gc.setFont(font);
        gc.setFill(Color.LAVENDERBLUSH);
        gc.fillText("DO YOU WANT TO EXIT?  Y / N  ",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 96);

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
        font = Font.font("Arial", FontWeight.NORMAL,12);
        gcUi.setFont(font);
        gcUi.fillText("Player HP: " + player.getHp(), 50,UI_HEIGHT);
        gcUi.setStroke(Color.GREEN);
        gcUi.setLineWidth(20);
        gcUi.strokeLine(50, UI_HEIGHT-20, Math.max((UI_WIDTH-50)  * (player.getHp() / 100),50), UI_HEIGHT-20);
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
                                if (getRandom(3) == 1)
                                featuresDrop.add(new FeatureDrop(enemy.getX(), enemy.getY(), getRandom(2)));
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
        gc.fillText("Feature list: " + featuresDrop.size(), 0,36);


        gc.setLineWidth(1.0);
        gc.strokeLine(0,GameScene.GAME_HEIGHT/2,GameScene.GAME_WIDTH,GameScene.GAME_HEIGHT/2);
    }

    public void keyPressHandler(AnimationTimer timer){
        if(activeKeys.contains(KeyCode.UP)){
            player.move(0,-5);
        }
        if(activeKeys.contains(KeyCode.DOWN)){
            player.move(0,5);
        }
        if(activeKeys.contains(KeyCode.LEFT)) {
            player.move(-5,0);
        }
        if(activeKeys.contains(KeyCode.RIGHT)){
            player.move(5,0);
        }
        if (activeKeys.contains(KeyCode.SPACE)){
            if (!isGameOver() && !isPause()) {
                player.shoot();
            } else if (isGameOver()){
                player = new Player();
                enemies = new ArrayList<>();
                playerWeapons = new ArrayList<>();
                enemyWeapons = new ArrayList<>();
                featuresDrop = new ArrayList<>();
                setGameOver(false);
                setStart(System.currentTimeMillis());
            }

        }
        if (activeKeys.contains(KeyCode.ESCAPE)){
            if(!isGameOver() && !isPause()) {
                setPause(true);
            } else if(isGameOver()){
                timer.stop();
                soundManager.shutdown();
                Game.setScene(1);
            }
        }
        if (activeKeys.contains(KeyCode.Y)){
            if (isPause()){
                timer.stop();
                soundManager.shutdown();
                Game.setScene(1);
            }
        }
        if (activeKeys.contains(KeyCode.N)){
            if (isPause()){
                setPause(false);
            }
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
