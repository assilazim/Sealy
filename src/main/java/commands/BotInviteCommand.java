package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BotInviteCommand implements Command {

    @Override
    public String getCommandName() {
        return "invite";
    }

    @Override
    public String getCommandDescription() {
        return "**!invite** - this will provide a link to invite the bot to your server.";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Invite me to your server: https://discordapp.com/api/oauth2/authorize?client_id=1052104461469888563&permissions=9126890497&scope=bot").queue();
    }
}