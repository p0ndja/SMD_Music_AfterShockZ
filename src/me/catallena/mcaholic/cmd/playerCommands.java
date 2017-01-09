package me.catallena.mcaholic.cmd;

import java.util.Random;

import javax.persistence.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import me.catallena.mcaholic.pluginMain;

public class playerCommands implements CommandExecutor {

	pluginMain pl;

	public playerCommands(pluginMain pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		String message = "";
		for (String part : args) {
			if (message != "")
				message += " ";
			message += part;
		}
		// Toggle between mute and un-mute
		if (cmd.getName().equalsIgnoreCase("music")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					Player p = (Player) sender;
				if (pl.getConfig().getString("Players." + p.getName() + ".music").equalsIgnoreCase("false")) {
					p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "You have" + ChatColor.GREEN + " Unmute Music!");
					p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "Type '/music' again to " + ChatColor.RED +"Mute.");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 2);
						// p.playSound(p.getLocation(), Sound.<SOUND>, <VOLUME>, <PITCH>);
						pluginMain.getMusicThread().getSongPlayer().addPlayer(p);
						pl.getConfig().set("Players." + p.getName() + ".music", "true");
						pl.saveConfig();
					} else {
					if (pl.getConfig().getString("Players." + p.getName() + ".music").equalsIgnoreCase("true")) {
						p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "You have" + ChatColor.RED + " Mute Music!");
						p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "Type '/music' again to " + ChatColor.GREEN +"Unmute.");
							p.playSound(p.getLocation(), Sound.PIG_DEATH, 1, 0);
							pluginMain.getMusicThread().getSongPlayer().removePlayer(p);
							pl.getConfig().set("Players." + p.getName() + ".music", "false");
							pl.saveConfig();
						}
					}
				}
			}
		}
		if (args.length >= 1) {
			Player p = (Player) sender;
			p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "This command don't have sub-command.");
			p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Music" + ChatColor.DARK_GRAY + ChatColor.BOLD + " // " + ChatColor.GRAY + "Just type " + ChatColor.YELLOW + "'/music'" + ChatColor.GRAY + " for toggling");
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 0);
		}

		return true;

	}
}
