package com.unco.reanimators.impl.module.hidden;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.event.minecraft.EventKeyPress;
import com.unco.reanimators.api.macro.Macro;
import com.unco.reanimators.api.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 5/7/2019 @ 9:49 PM.
 */
public final class MacroModule extends Module {

    public MacroModule() {
        super("Macros", new String[] {"mac"}, "Allows you to bind macros to keys", "NONE", -1, ModuleType.HIDDEN);
        this.setHidden(true);
        this.toggle();
    }

    @Listener
    public void keyPress(EventKeyPress event) {
        for(Macro macro : Seppuku.INSTANCE.getMacroManager().getMacroList()) {
            if(event.getKey() == Keyboard.getKeyIndex(macro.getKey()) && Keyboard.getKeyIndex(macro.getKey()) != Keyboard.KEY_NONE) {
                final String[] split = macro.getMacro().split(";");

                for(String s : split) {
                    Minecraft.getMinecraft().player.sendChatMessage(s);
                }
            }
        }
    }

}
