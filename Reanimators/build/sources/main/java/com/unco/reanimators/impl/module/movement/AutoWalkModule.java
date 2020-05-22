package com.unco.reanimators.impl.module.movement;

import com.unco.reanimators.api.event.player.EventPlayerUpdate;
import com.unco.reanimators.api.event.player.EventUpdateWalkingPlayer;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 5/2/2019 @ 7:06 PM.
 */
public final class AutoWalkModule extends Module {

    public AutoWalkModule() {
        super("AutoWalk", new String[] {"AutomaticWalk"}, "Automatically presses the forward key", "NONE", -1, ModuleType.MOVEMENT);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
    }

    @Listener
    public void onWalkingUpdate(EventUpdateWalkingPlayer event) {
        Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = true;
    }

    @Listener
    public void onUpdate(EventPlayerUpdate event) {
        Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = true;
    }

}
