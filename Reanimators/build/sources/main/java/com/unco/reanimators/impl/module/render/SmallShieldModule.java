package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.api.event.player.EventPlayerUpdate;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author fsck
 * 2019-10-20.
 */
public final class SmallShieldModule extends Module {

    final Minecraft mc = Minecraft.getMinecraft();
    ItemRenderer itemRenderer = Minecraft.getMinecraft().entityRenderer.itemRenderer;

    public SmallShieldModule() {
        super("SmallShield", new String[]{"SmallShield", "SS"}, "Smaller shield", "NONE", -1, ModuleType.RENDER);
    }

    @Listener
    public void changeOffhandProgress(EventPlayerUpdate event){
        itemRenderer.equippedProgressOffHand = 0.5F;
    }
}
