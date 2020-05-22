package com.unco.reanimators.impl.module.player;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventSendPacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import club.minnced.impl.annotated.handler.annotation.Listener;

import static net.minecraft.network.play.client.CPacketEntityAction.Action.START_SPRINTING;
import static net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SPRINTING;

/**
 * Author Seth
 * 4/27/2019 @ 3:08 PM.
 */
public final class NoHungerModule extends Module {

    public NoHungerModule() {
        super("NoHunger", new String[]{"AntiHunger"}, "Prevents hunger loss by spoofing on ground state", "NONE", -1, ModuleType.PLAYER);
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer) event.getPacket();
                if (Minecraft.getMinecraft().player.fallDistance > 0 || Minecraft.getMinecraft().playerController.isHittingBlock) {
                    packet.onGround = true;
                } else {
                    packet.onGround = false;
                }
            }
            if (event.getPacket() instanceof CPacketEntityAction) {
                final CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
                if (packet.getAction() == START_SPRINTING || packet.getAction() == STOP_SPRINTING) {
                    event.setCanceled(true);
                }
            }
        }
    }

}
