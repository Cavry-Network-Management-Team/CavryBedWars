package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.configuration.ConfigManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.plugin;

public class Save extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Save(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        setArenaSetupCommand(true);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p);
        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        p.getWorld().save();
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.unloadWorld(Bukkit.getWorld(p.getWorld().getName()), true), 30L);
        p.sendMessage("§6 ▪ §7Arena changes saved!");
        p.sendMessage("§6 ▪ §7You can now enable it using:");
        p.spigot().sendMessage(Misc.msgHoverClick("§6/" + getParent().getName() + " enableArena " + ss.getWorldName() + "§7 (click to enable)", "§dEnable this arena.", "/" + getParent().getName() + " enableArena " + ss.getWorldName(), ClickEvent.Action.RUN_COMMAND));
        ss.done();
        return true;
    }
}
