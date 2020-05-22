package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventReceivePacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 8/12/2019 @ 1:59 AM.
 */
public final class VanillaTabModule extends Module {

    public VanillaTabModule() {
        super("VanillaTab", new String[]{"VTab", "VanillaT"}, "Removes the Header and Footer from the tab menu.", "NONE", -1, ModuleType.MISC);
    }

    @Listener
    public void recievePacket(EventReceivePacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if(event.getPacket() instanceof SPacketPlayerListHeaderFooter) {
                event.setCanceled(true);
            }
        }
    }

}
