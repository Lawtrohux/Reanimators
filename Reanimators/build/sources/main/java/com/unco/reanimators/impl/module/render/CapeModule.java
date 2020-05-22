package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.player.EventCapeLocation;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 12/8/2019 @ 3:04 PM.
 */
public final class CapeModule extends Module {

    public CapeModule() {
        super("Cape", new String[]{"Capes"}, "Displays your cape.", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void displayCape(EventCapeLocation event) {
        if (Minecraft.getMinecraft().player != null && event.getPlayer() == Minecraft.getMinecraft().player) {
            final ResourceLocation cape = Seppuku.INSTANCE.getCapeManager().getCape(event.getPlayer());
            if (cape != null) {
                event.setLocation(cape);
                event.setCanceled(true);
            }
        }
    }

}
