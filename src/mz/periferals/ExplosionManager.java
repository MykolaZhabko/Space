package mz.periferals;

import javafx.scene.media.AudioClip;
import mz.sprites.Explosion;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ExplosionManager {
    ExecutorService explosionPool;
    Map<String, Explosion> EffectsMap = new HashMap<>();
}
