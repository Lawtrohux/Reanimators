package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.player.EventUpdateWalkingPlayer;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/19/2019 @ 8:15 AM.
 */
public final class PortalGuiModule extends Module {

    public PortalGuiModule() {
        super("PortalGui", new String[] {"PGui"}, "Allows you to open guis while in portals", "NONE", -1, ModuleType.MISC);
    }

    @Listener
    public void onWalkingUpdate(EventUpdateWalkingPlayer event) {
        if(event.getStage() == EventStageable.EventStage.PRE) {
            Minecraft.getMinecraft().player.inPortal = false;
        }
    }
}
