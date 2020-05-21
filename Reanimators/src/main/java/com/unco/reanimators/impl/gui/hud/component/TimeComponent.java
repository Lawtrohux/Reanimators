package me.rigamortis.seppuku.impl.gui.hud.component;

import me.rigamortis.seppuku.api.gui.hud.component.DraggableHudComponent;
import net.minecraft.client.Minecraft;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by noil on 9/1/2019 at 4:27 PM
 */
public final class TimeComponent extends DraggableHudComponent {

    public TimeComponent() {
        super("Time");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        final Minecraft mc = Minecraft.getMinecraft();
        final String time = new SimpleDateFormat("h:mm a").format(new Date());

        this.setW(mc.fontRenderer.getStringWidth(time));
        this.setH(mc.fontRenderer.FONT_HEIGHT);

        mc.fontRenderer.drawStringWithShadow(time, this.getX(), this.getY(), -1);
    }

}
