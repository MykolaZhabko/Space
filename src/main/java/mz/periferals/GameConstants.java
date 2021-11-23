package mz.periferals;

import javafx.scene.image.Image;

/**
 * This interface is used as a constants container for whole game.
 */

public interface GameConstants {
    int GAME_WIDTH = 600;
    int GAME_HEIGHT = 800;
    int UI_WIDTH = 700;
    int UI_HEIGHT = 900;

    int MENU_SCENE = 1;
    int GAME_SCENE = 0;
    int ARCHIVE_SCENE = 2;
    int ABOUT_SCENE = 3;

    Image UI = new Image("UIBACK.png",700,900,false,true);
    Image explosion1 = new Image("Explosions/explosion 1.png");
    Image explosion3 = new Image("Explosions/explosion 3.png");
    Image explosion4 = new Image("Explosions/explosion 4.png");
    Image enemy = new Image("Enemies/Spaceship-Drakir1.png",32, 29, true, true);
    Image enemy2 = new Image("Enemies/Spaceship-Drakir2.png",48,48,true,true);
    Image player = new Image("SpaceShip.png",50,73,true,true);

    Image enemyWeapon = new Image("Enemies/weapon/laserEnemy1.png",8,20,true,true);
    Image playerWeapon = new Image("Player.weapon/laserGreen1.png",6,32,true,true);


}
