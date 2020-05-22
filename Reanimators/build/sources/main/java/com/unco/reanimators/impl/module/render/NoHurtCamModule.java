package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.api.event.render.EventHurtCamEffect;
import com.unco.reanimators.api.module.Module;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/11/2019 @ 2:55 AM.
 */
public final class NoHurtCamModule extends Module {

    public NoHurtCamModule() {
        super("NoHurtCam", new String[] {"AntiHurtCam"}, "Removes hurt camera effects", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void hurtCamEffect(EventHurtCamEffect event) {
        event.setCanceled(true);
    }

}
