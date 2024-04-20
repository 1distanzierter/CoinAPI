package de.artur.coinapi;

import de.artur.coinapi.api.CoinAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static MySQL mysql;

    @Override
    public void onLoad() {
        instance = this;

    }
    @Override
    public void onEnable() {
        if (this.getConfig().getString("host") == null) {
            this.getConfig().set("host", "host");
        }

        if (this.getConfig().getString("database") == null) {
            this.getConfig().set("database", "database");
        }

        if (this.getConfig().getString("user") == null) {
            this.getConfig().set("user", "user");
        }

        if (this.getConfig().getString("password") == null) {
            this.getConfig().set("password", "password");
        }

        this.saveConfig();
        this.connectMySQL();
        Bukkit.getConsoleSender().sendMessage("§8[§6CoinSystem§8] §r§7Plugin §aerfolgreich §7geladen!");

    }


    @Override
    public void onDisable() {

        mysql.close();
    }


    public static Main getInstance() {

        return instance;
    }
    public void connectMySQL() {
        String host = this.getConfig().getString("host");
        String database = this.getConfig().getString("database");
        String user = this.getConfig().getString("user");
        String password = this.getConfig().getString("password");
        if (host.equalsIgnoreCase("host") && database.equalsIgnoreCase("database") && user.equalsIgnoreCase("user") && password.equalsIgnoreCase("password")) {
            Bukkit.getConsoleSender().sendMessage("§r§cBitte trage deine MySQL-Daten in der Config ein!");
            Bukkit.getPluginManager().disablePlugin(getInstance());
        } else {
            mysql = new MySQL(host, database, user, password);
            mysql.connect();
            mysql.update("CREATE TABLE IF NOT EXISTS coinapi(UUID varchar(64), COINS int);");
        }
    }
}
