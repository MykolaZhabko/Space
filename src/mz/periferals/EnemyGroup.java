package mz.periferals;

import mz.scenes.GameScene;
import mz.sprites.Enemy;
import mz.sprites.GeneralSprite;

import java.util.ArrayList;

public class EnemyGroup {
    private ArrayList<Enemy> enemyGroup;

    public ArrayList<Enemy> getEnemyGroup() {
        return enemyGroup;
    }

    public void setEnemyGroup(ArrayList<Enemy> enemyGroup) {
        this.enemyGroup = enemyGroup;
    }

    public EnemyGroup(){
        enemyGroup = groupL1_1();
        
    }

    public ArrayList<Enemy> groupL1_1(){
        ArrayList<Enemy> group = new ArrayList<>();
        int enemyWidth = 42;
        int enemyHeight = 39;
        int initialX = enemyHeight;
        int initialY = 0 - enemyHeight;
        for (int i = 0; i < (GameScene.GAME_WIDTH/enemyWidth)/2; i++)
        {
            System.out.println("Enemy #" + i + " Aded");
            Enemy enemy = new Enemy("Enemies/Spaceship-Drakir1.png");
            enemy.setX(initialX);
            enemy.setY(initialY);
            initialX += (enemyWidth*2);
            group.add(enemy);
        }
        return group;
    }

}
