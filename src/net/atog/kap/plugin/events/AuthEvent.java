package net.atog.kap.plugin.events;

import net.atog.kap.plugin.auth.AuthAPI;
import net.atog.kap.plugin.main.KhalyAuth;
import net.atog.kap.plugin.util.API;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

public class AuthEvent implements Listener{
	
	private KhalyAuth pl;
	private AuthAPI auth;
	
	public AuthEvent(KhalyAuth pl) {
		this.pl = pl;
		auth = new AuthAPI(pl);
	}
	
	@EventHandler
	public void HandleJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		new BukkitRunnable() {
			public void run() {
				if(!auth.isLogged(p)) {
					p.kickPlayer(API.color("&b&lAuth &r&7>> &cTardaste mucho en ingresar, intenta de nuevo."));
				}
			}
		}.runTaskLater(pl, (long) Math.floor(20 * 15));
	}
	
	@EventHandler
	public void HandleQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if(auth.isLogged(p)) {
			pl.lista.remove(p);
		}
	}
	@EventHandler
	public void HandleMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!auth.isLogged(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void HandleCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().startsWith("/login") || e.getMessage().startsWith("/register")) {
			return;
		}
		
		if(auth.isLogged(e.getPlayer())) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void HandleBreak(BlockBreakEvent e) {
		if(!auth.isLogged(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void HandleDamage(EntityDamageEvent e) {
		if(e.getEntityType() == EntityType.PLAYER) {
			Player p = (Player)e.getEntity();
			if(!auth.isLogged(p)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void HandleIntecract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!auth.isLogged(p)) {
			e.setCancelled(true);
		}
	}
}
