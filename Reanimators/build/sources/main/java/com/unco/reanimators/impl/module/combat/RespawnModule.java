package com.unco.reanimators.impl.module.combat;

import com.unco.reanimators.api.event.minecraft.EventDisplayGui;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/20/2019 @ 9:09 AM.
 */
public final class RespawnModule extends Module {

    public RespawnModule() {
        super("Respawn", new String[] {"AutoRespawn", "Resp"}, "Automatically respawn after death", "NONE", -1, ModuleType.COMBAT);
    }

    @Listener
    public void displayGuiScreen(EventDisplayGui event) {
        if(event.getScreen() != null && event.getScreen() instanceof GuiGameOver) {
            Minecraft.getMinecraft().player.respawnPlayer();
        }
    }

}
