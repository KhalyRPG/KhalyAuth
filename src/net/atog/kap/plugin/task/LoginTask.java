package net.atog.kap.plugin.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.atog.kap.plugin.auth.AuthAPI;
import net.atog.kap.plugin.main.KhalyAuth;
import net.atog.kap.plugin.util.API;

public class LoginTask {
	private KhalyAuth pl;
	private AuthAPI auth;
	public LoginTask (KhalyAuth pl) {
		this.pl = pl;
		auth = new AuthAPI(pl);
	}
	
	public void startTask() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(!auth.isLogged(p)) {
						if(auth.isRegistrered(p)) {
							p.sendMessage(API.color("&b&lAuth&r &7➟ &aInicia sesión &e/login <contraseña>"));
						}else{
							p.sendMessage(API.color("&b&lAuth&r &7➟ &aRegistra tu cuenta &e/register <contraseña> <contraseña>"));
						}
					}
				}
			}
		}.runTaskTimer(pl, 0L, 60);
	}
	
	
	public void effects() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(!auth.isLogged(p)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 999980, 2));
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999980, 2));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999960, 50));
					}
				}
			}
		}.runTaskTimer(pl, 0L, 20);
	}
	
}
