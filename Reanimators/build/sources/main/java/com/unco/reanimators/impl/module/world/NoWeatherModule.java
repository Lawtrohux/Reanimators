package com.unco.reanimators.impl.module.world;

import com.unco.reanimators.api.event.world.EventRainStrength;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 8/14/2019 @ 1:45 AM.
 */
public final class NoWeatherModule extends Module {

    public NoWeatherModule() {
        super("NoWeather", new String[]{"AntiWeather"}, "Allows you to control the weather client-side", "NONE", -1, ModuleType.WORLD);
    }

    @Listener
    public void onRainStrength(EventRainStrength event) {
        if (Minecraft.getMinecraft().world == null)
            return;

        event.setCanceled(true);
    }

}
