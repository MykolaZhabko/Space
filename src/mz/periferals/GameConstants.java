package mz.periferals;


import javafx.scene.image.Image;
import mz.sprites.Weapon;

public interface GameConstants {
    int GAME_WIDTH = 600;
    int GAME_HEIGHT = 800;
    String playerLevel1 = "SpaceShip.png";
    Image explosion3 = new Image("Explosions/explosion 3.png");
    Image explosion4 = new Image("Explosions/explosion 4.png");
    Image enemy = new Image("Enemies/Spaceship-Drakir1.png",42, 39, true, true);
    Image player = new Image("SpaceShip.png",50,73,true,true);

    Image enemyWeapon = new Image("Enemies/weapon/laserEnemy1.png",8,20,true,true);
    Image playerWeapon = new Image("Player.weapon/laserGreen1.png",6,32,true,true);


}
