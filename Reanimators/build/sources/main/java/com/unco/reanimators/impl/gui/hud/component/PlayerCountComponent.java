package com.unco.reanimators.impl.gui.hud.component;

import com.unco.reanimators.api.gui.hud.component.DraggableHudComponent;
import net.minecraft.client.Minecraft;

/**
 * Author Seth
 * 12/5/2019 @ 3:00 PM.
 */
public final class PlayerCountComponent extends DraggableHudComponent {

    public PlayerCountComponent() {
        super("PlayerCount");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        final String playerCount = "ONLINE: " + Minecraft.getMinecraft().player.connection.getPlayerInfoMap().size();

        this.setW(Minecraft.getMinecraft().fontRenderer.getStringWidth(playerCount));
        this.setH(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(playerCount, this.getX(), this.getY(), -1);
    }

}
