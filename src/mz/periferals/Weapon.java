package mz.periferals;

import mz.sprites.Bullet;

import java.util.ArrayList;

public class Weapon {
    private ArrayList<Bullet> playerWeapon;

    public ArrayList<Bullet> getPlayerWeapon() {
        return playerWeapon;
    }

    public void setPlayerWeapon(ArrayList<Bullet> playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    public Weapon(){
        playerWeapon = new ArrayList<>();
        playerWeapon.add(new Bullet("weapon/laserGreen1.png"));
    }
}
