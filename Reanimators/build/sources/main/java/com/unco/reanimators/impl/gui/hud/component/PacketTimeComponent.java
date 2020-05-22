package com.unco.reanimators.impl.gui.hud.component;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.network.EventReceivePacket;
import com.unco.reanimators.api.gui.hud.component.DraggableHudComponent;
import com.unco.reanimators.api.util.Timer;
import net.minecraft.client.Minecraft;
import club.minnced.impl.annotated.handler.annotation.Listener;

import java.text.DecimalFormat;

/**
 * Author Seth
 * 8/31/2019 @ 3:07 AM.
 */
public final class PacketTimeComponent extends DraggableHudComponent {

    private Timer timer = new Timer();

    public PacketTimeComponent() {
        super("PacketTime");
        Seppuku.INSTANCE.getEventManager().addEventListener(this);
    }

    @Listener
    public void recievePacket(EventReceivePacket event) {
        if(event.getStage() == EventStageable.EventStage.PRE) {
            if(event.getPacket() != null) {
                this.timer.reset();
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        final float seconds = ((System.currentTimeMillis() - this.timer.getTime()) / 1000.0f) % 60.0f;
        final String delay = "PACKET: " + (seconds >= 3.0f ? "\2474" : "\247f") + new DecimalFormat("#.#").format(seconds);

        this.setW(Minecraft.getMinecraft().fontRenderer.getStringWidth(delay));
        this.setH(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(delay, this.getX(), this.getY(), -1);
    }

}
