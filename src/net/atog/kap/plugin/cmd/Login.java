package net.atog.kap.plugin.cmd;

import net.atog.kap.plugin.auth.AuthAPI;
import net.atog.kap.plugin.encrypt.KhalyMD5;
import net.atog.kap.plugin.main.KhalyAuth;
import net.atog.kap.plugin.util.API;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Login implements CommandExecutor {
	private KhalyAuth pl;
	public Login(KhalyAuth pl) {
		this.pl = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(!(s instanceof Player)) {
			s.sendMessage(API.color("&cSolo lo puede usar un jugador."));
		}else{
			Player p = (Player)s;
			AuthAPI auth = new AuthAPI(pl);
			if(args.length > 0) {
				//FileConfiguration players = pl.getPlayers();
				String pass = pl.getRDatabase().getPassword(p.getName());
				if(pass == "$none_:xdddds") {
					//p.sendMessage(API.color("&eTienes que registrarte primero, &c/register <contraseña> <contraseña>"));
				}else{
					if(auth.isLogged(p)) {
						return true;
					}
					String argsEncrypt = KhalyMD5.encrypt(args[0]);
					String passEncrypt = KhalyMD5.encrypt(pass);
					if(argsEncrypt == passEncrypt) {
						//p.sendMessage(API.color("&eBienvenid@ a KhalyRPG %s!").replaceAll("%s", ""+p.getName()));
						pl.lista.add(p);
						p.removePotionEffect(PotionEffectType.BLINDNESS);
						p.removePotionEffect(PotionEffectType.SLOW);
						p.removePotionEffect(PotionEffectType.CONFUSION);
					}else{
						p.kickPlayer(API.color("&8[&b&lKhaly&6&lRPG&8]\n&cContraseña incorrecta, intenta de nuevo."));
						Bukkit.getConsoleSender().sendMessage(API.color("&c" + p.getName() + " &eHa fallado con su contraseña."));
					}
				}
			}else{
				//p.sendMessage(API.color("&eDebes ingresar la contraseña."));
			}
		}
		
		return false;
	}

}
