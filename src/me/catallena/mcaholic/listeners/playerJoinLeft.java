package me.catallena.mcaholic.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.catallena.mcaholic.pluginMain;

public class playerJoinLeft implements Listener {

	pluginMain pl;

	public playerJoinLeft(pluginMain pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPlayedBefore()) {
			pluginMain.getMusicThread().getSongPlayer().addPlayer(p);
			pl.getConfig().set("Players." + p.getName() + ".music", "true");
			pl.saveConfig();
		}
		if (pl.getConfig().getString("Players." + p.getName() + ".music").equalsIgnoreCase("true")) {
			pluginMain.getMusicThread().getSongPlayer().addPlayer(p);
		} else {
			pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
			pl.getConfig().set("Players." + p.getName() + ".music", "false");
			pl.saveConfig();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
		pl.getConfig().set("Players." + p.getName() + ".music", "true");
		pl.saveConfig();
	}
}
