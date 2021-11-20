package mz.launcher;

import mz.game.Game;

/**
 * Launcher class.
 * The purpose of this class only to launch te main class.
 * Building of the artifacts have some issues if the main class inherits from some other class.
 */
public class Launcher {
    public static void main(String[] args) {
        Game.main(args);
    }
}
