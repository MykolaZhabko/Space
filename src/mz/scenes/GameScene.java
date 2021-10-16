package mz.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mz.game.Game;
import mz.sprites.Player;

public class GameScene extends GeneralScene{
    private Player player;

    public GameScene(){
        player = new Player("SpaceShip.png",50,73,false,true);
    }



    @Override
    public void draw() {
        activeKeys.clear();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,GAME_WIDTH, GAME_HEIGHT);

                showDevInfo();
                player.draw(gc);

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
                if (activeKeys.contains(KeyCode.ESCAPE)){
                    this.stop();
                    Game.exit();
                }

            }
        }.start();
    }

    public void showDevInfo(){
        Font font = Font.font("Arial", FontWeight.NORMAL,12);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        String info = "Player x: " + player.getX() + ", y: " + player.getY();
        gc.fillText(info, 0,12);
    }
}
