package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.gui.EventBookPage;
import com.unco.reanimators.api.event.gui.EventBookTitle;
import com.unco.reanimators.api.module.Module;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/16/2019 @ 8:07 AM.
 */
public final class ColoredBooksModule extends Module {

    public ColoredBooksModule() {
        super("ColoredBooks", new String[] {"BookColor", "BookColors", "cbooks", "cbook"}, "Allows you to use the & character to color book text and titles", "NONE", -1, ModuleType.MISC);
    }

    @Listener
    public void addPage(EventBookPage event) {
        event.setPage(event.getPage().replace("&", "\247"));
    }

    @Listener
    public void editTitle(EventBookTitle event) {
        event.setTitle(event.getTitle().replace("&", "\247"));
    }

}
