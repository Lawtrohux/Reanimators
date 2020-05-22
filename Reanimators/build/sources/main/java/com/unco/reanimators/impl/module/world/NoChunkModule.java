package com.unco.reanimators.impl.module.world;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventReceivePacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.network.play.server.SPacketChunkData;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 6/2/2019 @ 1:30 PM.
 */
public final class NoChunkModule extends Module {

    public NoChunkModule() {
        super("NoChunk", new String[]{"AntiChunk"}, "Prevents processing of chunk data packets", "NONE", -1, ModuleType.WORLD);
    }

    @Listener
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof SPacketChunkData) {
                event.setCanceled(true);
            }
        }
    }

}
