package com.unco.reanimators.impl.module.movement;

import com.unco.reanimators.api.event.entity.EventHorseSaddled;
import com.unco.reanimators.api.event.entity.EventPigTravel;
import com.unco.reanimators.api.event.entity.EventSteerEntity;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/24/2019 @ 8:53 PM.
 */
@Module.Info(name = "Entity Control", category = Module.Category.PLAYER)
public final class EntityControlModule extends Module {


    @Listener
    public void pigTravel(EventPigTravel event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final boolean moving = mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.jump;

        final Entity riding = mc.player.getRidingEntity();

        if (riding != null && riding instanceof EntityPig) {
            if (!moving && riding.onGround) {
                event.setCanceled(true);
            }
        }
    }

    @Listener
    public void steerEntity(EventSteerEntity event) {
        event.setCanceled(true);
    }

    @Listener
    public void horseSaddled(EventHorseSaddled event) {
        event.setCanceled(true);
    }

}
