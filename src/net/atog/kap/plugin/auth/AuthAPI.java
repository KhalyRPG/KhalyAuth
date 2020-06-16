package net.atog.kap.plugin.auth;

import java.util.List;

import org.bukkit.entity.Player;

import net.atog.kap.plugin.main.KhalyAuth;

public class AuthAPI {
	
	private KhalyAuth pl;
	
	public AuthAPI(KhalyAuth pl) {
		this.pl = pl;
	}
	
	public boolean isRegistrered(Player p) {
		String pass = pl.getRDatabase().getPassword(p.getName());
		if(pass == "$none_:xdddds") {
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isLogged(Player p) {
		List<Player> lista = pl.lista;
		if(lista.contains(p)) {
			return true;
		}else{
			return false;
		}
	}
	
}
