package com.unco.reanimators.impl.module.misc;

import com.unco.reanimators.api.event.EventStageable;
import com.unco.reanimators.api.event.minecraft.EventDisplayGui;
import com.unco.reanimators.api.event.minecraft.EventRunTick;
import com.unco.reanimators.api.event.network.EventSendPacket;
import com.unco.reanimators.api.module.Module;
import com.unco.reanimators.api.util.Timer;
import com.unco.reanimators.api.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import club.minnced.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 4/22/2019 @ 6:17 AM.
 */
public final class ReconnectModule extends Module {

    private String lastIp;
    private int lastPort;
    private boolean reconnect;
    private Timer timer = new Timer();

    public final Value<Float> delay = new Value<Float>("Delay", new String[]{"Del"}, "Delay in MS (milliseconds) between reconnect attempts.", 3000.0f, 0.0f, 10000.0f, 500.0f);

    public ReconnectModule() {
        super("Reconnect", new String[]{"Rejoin", "Recon", "AutoReconnect"}, "Automatically reconnects to the last server after being kicked", "NONE", -1, ModuleType.MISC);
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof C00Handshake) {
                final C00Handshake packet = (C00Handshake) event.getPacket();
                if (packet.getRequestedState() == EnumConnectionState.LOGIN) {
                    this.lastIp = packet.ip;
                    this.lastPort = packet.port;
                }
            }
        }
    }

    @Listener
    public void runTick(EventRunTick event) {
        if (event.getStage() == EventStageable.EventStage.POST) {
            if (this.lastIp != null && this.lastPort > 0 && this.reconnect) {
                if (this.timer.passed(this.delay.getValue())) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(null, Minecraft.getMinecraft(), this.lastIp, this.lastPort));
                    this.timer.reset();
                    this.reconnect = false;
                }
            }
        }
    }

    @Listener
    public void displayGui(EventDisplayGui event) {
        if (event.getScreen() != null) {
            if (event.getScreen() instanceof GuiDisconnected) {
                this.reconnect = true;
            }
        }
    }

}
