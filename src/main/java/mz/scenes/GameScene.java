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
import java.util.*;

/**
 * The SCENE for actual GAME.
 * Game loop is here and collision detections handlers.
 */

public class GameScene extends GeneralScene implements GameConstants {
    private Player player;
    private final Level1 bgL1;
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

    /**
     * Constructor:
     * - Initialise the Player, background, the lists for enemy bullets, player bullets, enemies and drops.
     */
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

    /**
     * Constructor for loading the game
     * @param load - any boolean value will load the game
     */
    public GameScene(boolean load){
        player = new Player();
        int[] values = readSerializedPlayer();
        player.setScore(values[0]);
        player.setX(values[1]);
        player.setY(values[2]);
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

    /**
     * Method for generating enemies on the battlefield
     */
    public void generateEnemies(){
        enemyTime += 0.02;
        if (enemyTime > 2 && enemies.size() < 6) {
            Enemy newEnemy = new Enemy(1);
            newEnemy.setY(-(int)newEnemy.getSprite().getHeight());
            newEnemy.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getSprite().getWidth())));
            enemies.add(newEnemy);
            enemyTime=0;
            if (player.getScore() > 100 && getRandom(2) == 1){
                Enemy newEnemy2 = new Enemy(2);
                newEnemy2.setY(-(int)newEnemy.getSprite().getHeight());
                newEnemy2.setX((int) Math.round(Math.random()*(GAME_WIDTH - (int)newEnemy.getSprite().getWidth())));
                enemies.add(newEnemy2);
            }
        }
    }


    /**
     * The game loop.
     */
    @Override
    public void draw() {
        setStart(System.currentTimeMillis());
        activeKeys.clear();

        //Game loop starts here
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawUI();
                if((player.isAlive() || player.getLives() > 0 || player.getExplosion().isAlive()) && !isPause()){
                    bgL1.draw(gc);
                    player.draw(gc);
                    generateEnemies();
                    drawEnemies(gc);
                    drawBullets(gc);
                    drawFeaturesDrop();
                    removeDead();
                    collisionDetect();
                }else if (isPause()){
                    drawGamePause();
                } else {
                    if(!isGameOver() && !isPause()) {
                        setGameOver(true);
                        setFinish(System.currentTimeMillis());
                        setTimeElapsed(getStart()-getFinish());
                        writeToArchive();
                    }
                    drawGameOver();

                }
                keyPressHandler(this);
            }
        }.start();
    }

    /**
     * Writes the record of played game to JSON file
     */
    private void writeToArchive() {
        //gameplay time formatting
        long milliseconds = Math.abs(getTimeElapsed());
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

        JSONObject jobj = new JSONObject();
        jobj.put("date",new Date().toString());
        jobj.put("score",player.getScore());
        jobj.put("time",String.format("%d h %d min %d sec", hours,minutes,seconds));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jobj);

        File archive = new File("spaceGame");
        if(archive.mkdir()){
           //Creating the file
            try {
                FileWriter fwr = new FileWriter("spaceGame/arch.json", false);
                fwr.write(jsonArray.toJSONString());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            //File was created already
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("spaceGame/arch.json"));
                JSONArray readFileJsonObj =(JSONArray) obj;
                readFileJsonObj.add(jobj);
                FileWriter fwr = new FileWriter("spaceGame/arch.json", false);
                fwr.write(readFileJsonObj.toJSONString());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
                //in case file was created and empty due to unknown reason
                FileWriter fwr = null;
                try {
                    fwr = new FileWriter("spaceGame/arch.json", false);
                    fwr.write(jsonArray.toJSONString());
                    fwr.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        }
    }

    /**
     * Draws the information on the screen when the GAME is over.
     */
    private void drawGameOver() {
        Font font = Font.font("Arial",FontWeight.NORMAL,24);
        gc.setFont(font);
        gc.setFill(Color.RED);
        gc.fillText("GAME OVER",GAME_WIDTH/2 -240,GAME_HEIGHT/2);
        gc.setFill(Color.WHITE);
        gc.fillText("TIME: " + (getTimeElapsed() * (-1) / 1000) + " seconds",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 24);
        gc.fillText("SCORE: " + player.getScore(),GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 48);
        font = Font.font("Arial",FontWeight.NORMAL,24);
        gc.setFont(font);
        gc.setFill(Color.LAVENDERBLUSH);
        gc.fillText("To restart the game press - R ",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 96);
        gc.fillText("To exit the game press - ESC ",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 120);

    }

    /**
     * Draws the information on the screen when the GAME is paused.
     */
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
        gc.fillText("TO SAVE GAME PRESS - S",GAME_WIDTH/2 -240,GAME_HEIGHT/2 + 120);

    }

    /**
     * Drawing the informative UI for the player
     * Includes: health points of the player, score, lives.
     */
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


    /**
     * Draws the dropping feature from killed enemy
     */
    private void drawFeaturesDrop() {
        for (FeatureDrop drop: featuresDrop){
            drop.draw(gc);
            drop.collideWithPlayer(player);
        }
    }

    /**
     * All collisions detection method
     */
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

    /**
     * Helper method for colision detection.
     * @param a - Object #1
     * @param b - Object #2
     * @return - return TRUE if collide and FALSE if not.
     */
    private boolean isCollide(GeneralSprite a, GeneralSprite b){
        return  a.getX() >= b.getX() &&
                a.getX() <= b.getX() + b.getSprite().getWidth() &&
                a.getY() >= b.getY() &&
                a.getY() <= b.getY() + b.getSprite().getHeight();
    }

    /**
     * Draws all bullets fired from player as well as from enemies
     * @param gc - GraphicsContext from canvas.
     */
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

    /**
     *  Draws all enemies.
     * @param gc - GraphicsContext from canvas.
     */
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

    /**
     * Handles key presses on the scene.
     * @param timer - game loop.
     */
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
        if (activeKeys.contains(KeyCode.S)){
            if (isPause()){
                serializePlayer();
                setPause(false);
            }
        }
    }

    /**
     * Remove not alive objects.
     */
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

    /**
     * Helper method. Random number generator.
     * @param num
     * @return
     */
    private int getRandom(int num) {
        int rnd = new Random().nextInt(num);
        return rnd;
    }

    /**
     * Method to serialize the Player object.
     * Currently only score is needed for loading the game
     */
    private void serializePlayer(){
        try {
            FileOutputStream fos = new FileOutputStream("spaceGame/player.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Method to read serialized object and use some of the properties for Player
     */
    private int[] readSerializedPlayer(){
        int[] values;
        try {
            FileInputStream fis = new FileInputStream("spaceGame/player.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Player player = (Player) ois.readObject();
            ois.close();

            System.out.println("Loaded player:");
            System.out.println(player);
            values = new int[]{player.getScore(),player.getX(),player.getY()};
            return values;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        values = new int[]{0, (int) (GAME_WIDTH/2 - player.getSprite().getWidth()/2), (int) (GAME_HEIGHT-this.player.getSprite().getHeight())};
        return values;
    }
}
