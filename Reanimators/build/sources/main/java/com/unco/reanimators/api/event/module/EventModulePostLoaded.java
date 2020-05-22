package com.unco.reanimators.api.event.module;

import com.unco.reanimators.api.module.Module;

public final class EventModulePostLoaded {
    private final Module module;

    public EventModulePostLoaded(final Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }
}
