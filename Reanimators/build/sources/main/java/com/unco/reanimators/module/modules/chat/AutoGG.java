package com.unco.reanimators.module.modules.chat;

import com.unco.reanimators.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import com.unco.reanimators.event.events.PacketEvent;
import com.unco.reanimators.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

// Rina.
@Module.Info(name = "AutoGG", category = Module.Category.CHAT)
public class AutoGG extends Module {
	private ConcurrentHashMap<String, Integer> target_players = null;

	@Override
	public int onEnable() {
		target_players = new ConcurrentHashMap<>();
        return 0;
    }

	@Override
	public void onDisable() {
		target_players = null;
	}

	@Override
	public void onUpdate() {
		if (isDisabled() || mc.player == null) return;
		if (target_players == null) {
			target_players = new ConcurrentHashMap<>();
		}

		for (Entity entity : mc.world.getLoadedEntityList()) {
			if (!EntityUtil.isPlayer(entity)) continue;

			EntityPlayer player = (EntityPlayer) entity;

			if (player.getHealth() > 0) continue;

			String name = player.getName();
			if (should_announce(name)) {
				send_announce(name);
				break;
			}
		}

		target_players.forEach((name, timeout) -> {
			if (timeout <= 0) {
				target_players.remove(name);
			} else {
				target_players.put(name, timeout - 1);
			}
		});
	}

	@EventHandler
	public Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
		if (mc.player == null) return;
		if (target_players == null) {
			target_players = new ConcurrentHashMap<>();
		}

		if (!(event.getPacket() instanceof CPacketUseEntity)) return;
		CPacketUseEntity cpacketUseEntity = ((CPacketUseEntity) event.getPacket());

		if (!(cpacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK))) return;

		Entity target_entity = cpacketUseEntity.getEntityFromWorld(mc.world);
		if (!EntityUtil.isPlayer(target_entity)) return;

		add_target_player(target_entity.getName());
	});

	@EventHandler
	public Listener<LivingDeathEvent> LivingDeathEvent = new Listener<>(event -> {
		if (mc.player == null) return;
		if (target_players == null) {
			target_players = new ConcurrentHashMap<>();
		}

		EntityLivingBase entity = event.getEntityLiving();
		if (entity == null) return;

		EntityPlayer player = (EntityPlayer) entity;
		if (player.getHealth() > 0) return;

		String name = player.getName();
		if (should_announce(name)) {
			send_announce(name);
		}
	});

	private boolean should_announce(String name) {
		return target_players.containsKey(name);
	}

	private void send_announce(String name) {
		target_players.remove(name);

		StringBuilder msg = new StringBuilder();

		msg.append("Clown down " + name + ", gf. reanimators client on top ");

		String msg_ = msg.toString().replaceAll("\u00A7", "");

		if (msg_.length() > 255) {
			msg_ = msg_.substring(0, 255);
		}

		mc.player.connection.sendPacket(new CPacketChatMessage(msg_));
	}

	public void add_target_player(String name) {
		if (Objects.equals(name, mc.player.getName())) return;

		if (target_players == null) {
			target_players = new ConcurrentHashMap<>();
		}
		target_players.put(name, 20);
	}
}