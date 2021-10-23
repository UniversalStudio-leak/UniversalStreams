package ru.universalstudio.streams.commands;

import org.bukkit.entity.*;
import org.bukkit.command.*;
import ru.universalstudio.streams.*;

public class StreamsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Manager.getInstance().getInv().openInv((Player)sender);
        return false; // СХУЯЛИ
    }

}