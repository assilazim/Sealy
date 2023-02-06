package commands;


import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {
    @Override
    public String getCommandName() {
        return "!ping";
    }

    @Override
    public String getCommandDescription() {
        return "Sends a pong command";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
