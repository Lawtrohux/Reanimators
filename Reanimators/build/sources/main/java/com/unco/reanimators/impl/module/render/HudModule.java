package com.unco.reanimators.impl.module.render;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.gui.EventRenderPotions;
import com.unco.reanimators.api.event.render.EventRender2D;
import com.unco.reanimators.api.gui.hud.component.DraggableHudComponent;
import com.unco.reanimators.api.gui.hud.component.HudComponent;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.value.Value;
import com.unco.reanimators.impl.gui.hud.GuiHudEditor;
import com.unco.reanimators.impl.gui.hud.anchor.AnchorPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/7/2019 @ 10:17 PM.
 */
public final class HudModule extends Module {

    public final Value<Boolean> hidePotions = new Value<Boolean>("HidePotions", new String[]{"HidePotions", "HidePots", "Hide_Potions"}, "Hides the Vanilla potion hud (at the top right of the screen).", true);

    public HudModule() {
        super("Hud", new String[]{"Overlay"}, "Renders hud components on the screen.", "NONE", -1, ModuleType.RENDER);
        this.setHidden(true);
    }

    @Listener
    public void render(EventRender2D event) {
        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.gameSettings.showDebugInfo || mc.currentScreen instanceof GuiHudEditor || mc.player == null) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        for (HudComponent component : Seppuku.INSTANCE.getHudManager().getComponentList()) {
            if (component.isVisible()) {
                //dont render components with the TOP_CENTER anchor if we are looking at the tab list
                if (component instanceof DraggableHudComponent) {
                    final DraggableHudComponent draggableComponent = (DraggableHudComponent) component;
                    if (draggableComponent.getAnchorPoint() != null && draggableComponent.getAnchorPoint().getPoint() == AnchorPoint.Point.TOP_CENTER) {
                        if (!mc.gameSettings.keyBindPlayerList.isKeyDown()) {
                            draggableComponent.render(0, 0, mc.getRenderPartialTicks());
                        }
                    } else {
                        draggableComponent.render(0, 0, mc.getRenderPartialTicks());
                    }
                } else {
                    component.render(0, 0, mc.getRenderPartialTicks());
                }
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Listener
    public void renderPotions(EventRenderPotions event) {
        if (this.hidePotions.getValue()) {
            event.setCanceled(true);
        }
    }
}
