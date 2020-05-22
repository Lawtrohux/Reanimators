package com.unco.reanimators.impl.module.world;

import com.unco.reanimators.api.event.world.EventCanCollide;
import com.unco.reanimators.api.module.Module;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 6/5/2019 @ 9:03 PM.
 */
public final class LiquidInteractModule extends Module {

    public LiquidInteractModule() {
        super("LiquidInteract", new String[] {"LiquidInt", "LiqInt"}, "Allows you to interact with liquids", "NONE", -1, ModuleType.WORLD);
    }

    @Listener
    public void canCollide(EventCanCollide event) {
        event.setCanceled(true);
    }

}
