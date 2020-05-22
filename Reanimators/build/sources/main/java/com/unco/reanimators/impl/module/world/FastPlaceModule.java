package com.unco.reanimators.impl.module.world;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.player.EventPlayerUpdate;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemExpBottle;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/23/2019 @ 12:58 PM.
 */
public final class FastPlaceModule extends Module {

    public final Value<Boolean> xp = new Value<Boolean>("XP", new String[]{"EXP"}, "Only activate while holding XP bottles.", false);

    public FastPlaceModule() {
        super("FastPlace", new String[]{"Fp"}, "Removes place delay", "NONE", -1, ModuleType.WORLD);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getMinecraft().rightClickDelayTimer = 6;
    }

    @Listener
    public void onUpdate(EventPlayerUpdate event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (this.xp.getValue()) {
                if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
                    Minecraft.getMinecraft().rightClickDelayTimer = 0;
                }
            } else {
                Minecraft.getMinecraft().rightClickDelayTimer = 0;
            }
        }
    }

}
