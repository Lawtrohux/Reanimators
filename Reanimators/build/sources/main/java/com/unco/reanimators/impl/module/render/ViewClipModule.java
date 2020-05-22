package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.api.event.render.EventOrientCamera;
import com.unco.reanimators.api.module.Module;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 7/22/2019 @ 8:54 AM.
 */
public final class ViewClipModule extends Module {

    public ViewClipModule() {
        super("ViewClip", new String[] {"ViewC"}, "Prevents the third person camera from ray-tracing", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void orientCamera(EventOrientCamera event) {
        event.setCanceled(true);
    }

}
