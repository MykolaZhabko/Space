package mz.periferals;

import mz.scenes.GameScene;
import mz.sprites.Enemy;
import mz.sprites.GeneralSprite;

import java.util.ArrayList;

public class EnemyGroup {
    private ArrayList<Enemy> enemyGroup;

    public EnemyGroup(){
        enemyGroup = new ArrayList<>();
        
    }

    public ArrayList<Enemy> groupL1_1(){
        ArrayList<Enemy> group = new ArrayList<>();
        int enemyWidth = 42;
        int enemyHeight = 39;
        int initialX = enemyHeight;
        int initialY = 0 - enemyHeight;
        for (int i = 0; i < (GameScene.GAME_WIDTH/enemyWidth)/2; i++)
        {
            Enemy enemy = new Enemy("Enemies/Spaceship-Drakir1.png");
            enemy.setX(initialX);
            enemy.setY(initialY);
            initialX += (enemyWidth*2);
        }
        return group;
    }

}
