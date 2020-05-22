package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.player.EventPlayerJoin;
import com.unco.reanimators.api.event.player.EventPlayerLeave;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.value.Value;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/25/2019 @ 2:12 PM.
 */
public final class GreeterModule extends Module {

    public final Value<Mode> mode = new Value<Mode>("Mode", new String[]{"Mode", "M"}, "Change between greeter modes. Client mode will only appear for you, Server mode will broadcast the greeting message for everyone.", Mode.CLIENT);

    private enum Mode {
        CLIENT, SERVER
    }

    public GreeterModule() {
        super("Greeter", new String[]{"Greet"}, "Automatically announces when a player joins and leaves", "NONE", -1, ModuleType.MISC);
    }

    @Override
    public String getMetaData() {
        return this.mode.getValue().name();
    }

    @Listener
    public void onPlayerJoin(EventPlayerJoin event) {
        switch (this.mode.getValue()) {
            case CLIENT:
                Seppuku.INSTANCE.logChat(event.getName() + " has joined the game");
                break;
            case SERVER:
                Seppuku.INSTANCE.getChatManager().add(event.getName() + " has joined the game");
                break;
        }
    }

    @Listener
    public void onPlayerLeave(EventPlayerLeave event) {
        switch (this.mode.getValue()) {
            case CLIENT:
                Seppuku.INSTANCE.logChat(event.getName() + " has left the game");
                break;
            case SERVER:
                Seppuku.INSTANCE.getChatManager().add(event.getName() + " has left the game");
                break;
        }
    }
}