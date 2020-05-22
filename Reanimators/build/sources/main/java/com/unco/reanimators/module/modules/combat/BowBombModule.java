package com.unco.reanimators.impl.module.combat;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventSendPacket;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/30/2019 @ 4:29 AM.
 */
@Module.Info(name = "BowBomb", description = "Do increased damage with bows while falling", category = Module.Category.COMBAT)
public class BowBombModule extends Module {

    public BowBombModule() {
        super("BowBomb", new String[]{"BBomb"}, "Deal more damage with arrows while falling or flying", "NONE", -1, ModuleType.COMBAT);
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof CPacketPlayerDigging) {
                final Minecraft mc = Minecraft.getMinecraft();

                final CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
                if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                    if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && mc.player.getItemInUseMaxCount() >= 20) {
                        if (!mc.player.onGround) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.1f, mc.player.posZ, false));
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 10000f, mc.player.posZ, true));
                        }
                    }
                }
            }
        }
    }

}
