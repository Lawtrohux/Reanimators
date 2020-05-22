package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.api.event.player.EventPlayerDamageBlock;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/24/2019 @ 4:55 PM.
 */
public final class NoBreakAnimModule extends Module {

    public NoBreakAnimModule() {
        super("NoBreakAnim", new String[] {"AntiBreakAnim", "NoBreakAnimation"}, "Prevents the break animation server-side", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void damageBlock(EventPlayerDamageBlock event) {
        Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, event.getPos(), event.getFace()));
    }

}
