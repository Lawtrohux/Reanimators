package com.unco.reanimators.impl.command;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.command.Command;

/**
 * Author Seth
 * 5/12/2019 @ 9:10 AM.
 */
public final class ReloadCommand extends Command {

    public ReloadCommand() {
        super("Reload", new String[] {"Rload"}, "Reloads the client", "Reload");
    }

    @Override
    public void exec(String input) {
        if (!this.clamp(input, 1, 1)) {
            this.printUsage();
            return;
        }

        Seppuku.INSTANCE.reload();
        Seppuku.INSTANCE.logChat("Client Reloaded");
    }
}
