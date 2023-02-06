package commands;

import assilazim.Command;
import assilazim.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class HelpCommand implements Command {

    @Override
    public String getCommandName() {
        return "!help";
    }

    @Override
    public String getCommandDescription() {
        return "Shows all available commands and descriptions";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Here's what I can do!:\n");
        for (HashMap.Entry<String, Command> entry : CommandHandler.getCommands().entrySet()) {
            helpMessage.append("**").append(entry.getKey()).append("** - ").append(entry.getValue().getCommandDescription()).append("\n");
        }
        event.getChannel().sendMessage(helpMessage.toString()).queue();
    }
}
