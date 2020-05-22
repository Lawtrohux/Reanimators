package com.unco.reanimators.impl.module.movement;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.player.EventPlayerUpdate;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 5/11/2019 @ 7:30 AM.
 */
public final class HorseJumpModule extends Module {

    public HorseJumpModule() {
        super("HorseJump", new String[] {"JumpPower", "HJump"}, "Makes horses and llamas jump at max height", "NONE", -1, ModuleType.MOVEMENT);
    }

    @Listener
    public void onUpdate(EventPlayerUpdate event) {
        if(event.getStage() == EventStageable.EventStage.PRE) {
            Minecraft.getMinecraft().player.horseJumpPower = 1;
            Minecraft.getMinecraft().player.horseJumpPowerCounter = -10;
        }
    }

}
