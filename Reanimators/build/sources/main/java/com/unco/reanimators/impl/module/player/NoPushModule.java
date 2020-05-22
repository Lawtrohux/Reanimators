package com.unco.reanimators.impl.module.player;

import com.unco.reanimators.api.event.player.EventApplyCollision;
import com.unco.reanimators.api.event.player.EventPushOutOfBlocks;
import com.unco.reanimators.api.event.player.EventPushedByWater;
import com.unco.reanimators.api.module.Module;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/9/2019 @ 12:52 AM.
 */
public final class NoPushModule extends Module {

    public NoPushModule() {
        super("NoPush", new String[]{"AntiPush"}, "Disable collision with entities, blocks and water", "NONE", -1, ModuleType.PLAYER);
    }

    @Listener
    public void pushOutOfBlocks(EventPushOutOfBlocks event) {
        event.setCanceled(true);
    }

    @Listener
    public void pushedByWater(EventPushedByWater event) {
        event.setCanceled(true);
    }

    @Listener
    public void applyCollision(EventApplyCollision event) {
        event.setCanceled(true);
    }

}
