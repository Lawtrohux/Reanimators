package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventReceivePacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.server.SPacketSetSlot;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Shift clicks the crafting result into the inventory.
 *
 * @author Old Chum
 * @since 10/18/20
 */
@Module.Info(name = "Fast Craft", category = Module.Category.MISC, description = "Automatically collects the result when crafting.")
public class QuickCraftModule extends Module {

    @Listener
    public void onReceivePacket (EventReceivePacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof SPacketSetSlot) {
                // Check if this packet updates the recipe result and if the result is not empty
                if (((SPacketSetSlot) event.getPacket()).getSlot() == 0 && ((SPacketSetSlot) event.getPacket()).getStack().getItem() != Items.AIR) {
                    Minecraft mc = Minecraft.getMinecraft();

                    if (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiCrafting) {
                        mc.playerController.windowClick(mc.player.openContainer.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player);
                        mc.playerController.updateController();
                    }
                }
            }
        }
    }
}
