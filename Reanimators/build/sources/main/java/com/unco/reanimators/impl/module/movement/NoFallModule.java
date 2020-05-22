package com.unco.reanimators.impl.module.movement;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventSendPacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/20/2019 @ 9:00 AM.
 */
public final class NoFallModule extends Module {

    public NoFallModule() {
        super("NoFall", new String[]{"NoFallDamage"}, "Prevents fall damage", "NONE", -1, ModuleType.MOVEMENT);
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof CPacketPlayer && Minecraft.getMinecraft().player.fallDistance >= 3.0f) {
                final CPacketPlayer packet = (CPacketPlayer) event.getPacket();
                packet.onGround = true;
            }
        }
    }

}
