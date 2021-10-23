package ru.universalstudio.streams.commands;

import org.bukkit.command.*;
import ru.universalstudio.streams.Manager;

public class StreamCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("ustreams.live")) {
            sender.sendMessage(Manager.getInstance().msg("NOT_PERM"));
            return false;
        } else if (args.length == 0) {
            sender.sendMessage(Manager.getInstance().msg("HELP_COMMANDS"));
            return false;
        } else {
            // usage remove and add?

            sender.sendMessage(Manager.getInstance().msg("HELP_COMMANDS"));
            return false;
        }
    }
}
