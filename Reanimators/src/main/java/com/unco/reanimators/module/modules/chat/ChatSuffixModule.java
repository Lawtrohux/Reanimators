package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.player.EventSendChatMessage;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.impl.module.hidden.CommandsModule;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * created by noil on 11/8/19 at 7:48 PM
 */
@Module.Info(name = "Chat Suffix Fun", category = Module.Category.CHAT)
public final class ChatSuffixModule extends Module {

    private final String prefix = "\u23D0 \uD83C\uDDF0\uD83C\uDDE8\uD83C\uDDF1\uD83C\uDDF4₄";



    @Listener
    public void onSendChatMessage(EventSendChatMessage event) {
        final CommandsModule cmds = (CommandsModule) Seppuku.INSTANCE.getModuleManager().find(CommandsModule.class);
        if (cmds == null)
            return;

        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(cmds.prefix.getValue()))
            return;

        event.setCanceled(true);
        Minecraft.getMinecraft().getConnection().sendPacket(new CPacketChatMessage(event.getMessage() + " " + prefix));
    }
}
