package com.unco.reanimators.impl.gui.hud.component;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.gui.hud.component.DraggableHudComponent;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.util.RenderUtil;
import com.unco.reanimators.impl.gui.hud.GuiHudEditor;
import com.unco.reanimators.impl.module.render.HudModule;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.security.Key;

/**
 * created by noil on 5/7/2020
 */
public final class FirstLaunchComponent extends DraggableHudComponent {

    private Module hudModule;

    private String textData;

    public FirstLaunchComponent() {
        super("FirstLaunch");

        final String textData = "Welcome to Seppuku Client!\n\n" +
                "Press ~ (tilda/grave) to open the GUI / hud-editor.";
        this.setTextData(textData);

        this.setVisible(true);
        this.setSnappable(false);
        this.setW(200);
        this.setH(38);
        this.setX(2);
        this.setY(2);

        this.hudModule = Seppuku.INSTANCE.getModuleManager().find(HudModule.class);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        final Minecraft mc = Minecraft.getMinecraft();

        // Background
        RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH(), 0xFF202020);

        // Render text data
        mc.fontRenderer.drawSplitString(this.textData, (int) this.getX() + 2, (int) this.getY() + 2, 200, 0xFFFFFFFF);
    }

    public void onClose() {
        if (this.hudModule != null) {
            if(this.hudModule.isEnabled()) {
                this.hudModule.onEnable();
            }else {
                this.hudModule.toggle();
            }
            this.hudModule.setEnabled(true);
        }

        this.setVisible(false);
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }
}
