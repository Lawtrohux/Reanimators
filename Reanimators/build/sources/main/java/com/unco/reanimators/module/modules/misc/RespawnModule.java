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
@Module.Info(name = "Respawn Module", category = Module.Category.MISC, description = "Auto respawns.")
public class RespawnModule extends Module {

    @Listener
    public void displayGuiScreen(EventDisplayGui event) {
        if(event.getScreen() != null && event.getScreen() instanceof GuiGameOver) {
            Minecraft.getMinecraft().player.respawnPlayer();
        }
    }

}
