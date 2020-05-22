package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventSendPacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketCloseWindow;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/16/2019 @ 6:19 PM.
 */
public final class MoreInvModule extends Module {

    public MoreInvModule() {
        super("MoreInv", new String[]{"XCarry", "MoreInventory"}, "Allows you to carry items in your crafting and dragging slot", "NONE", -1, ModuleType.MISC);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (Minecraft.getMinecraft().world != null) {
            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketCloseWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId));
        }
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof CPacketCloseWindow) {
                final CPacketCloseWindow packet = (CPacketCloseWindow) event.getPacket();
                if (packet.windowId == Minecraft.getMinecraft().player.inventoryContainer.windowId) {
                    event.setCanceled(true);
                }
            }
        }
    }


}
