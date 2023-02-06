package assilazim;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    String getCommandName();
    String getCommandDescription();
    void handleCommand(String[] args, MessageReceivedEvent event);
}
