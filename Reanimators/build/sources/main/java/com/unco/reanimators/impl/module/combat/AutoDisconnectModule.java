package com.unco.reanimators.impl.module.combat;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.player.EventPlayerUpdate;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 5/23/2019 @ 7:41 PM.
 */
public final class AutoDisconnectModule extends Module {

    public final Value<Float> health = new Value("Health", new String[]{"Hp"}, "The amount of health needed to disconnect.", 8.0f, 0.0f, 20.0f, 0.5f);

    public AutoDisconnectModule() {
        super("AutoDisconnect", new String[]{"Disconnect"}, "Automatically disconnects when health is low enough", "NONE", -1, ModuleType.COMBAT);
    }

    @Listener
    public void onUpdate(EventPlayerUpdate event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (Minecraft.getMinecraft().player.getHealth() <= this.health.getValue()) {
                Minecraft.getMinecraft().player.connection.sendPacket(new CPacketHeldItemChange(420));
                this.toggle();
            }
        }
    }

}
