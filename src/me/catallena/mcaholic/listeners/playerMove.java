package me.catallena.mcaholic.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerMoveEvent;
import me.catallena.mcaholic.pluginMain;
import org.bukkit.event.Listener;

public class playerMove implements Listener {

	pluginMain pl;

	public playerMove(pluginMain pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (pl.getConfig().getString("Players." + p.getName() + ".music").equalsIgnoreCase("true")) {
			pluginMain.getMusicThread().getSongPlayer().addPlayer(p);
		} else {
			pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
		}
	}

}
