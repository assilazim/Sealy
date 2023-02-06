package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Magic8BallCommand implements Command {

    public static final List<String> responses = Arrays.asList(
            "It is certain.",
            "It is decidedly so.",
            "Without a doubt.",
            "Yes - definitely.",
            "You may rely on it.",
            "As I see it, yes.",
            "Most likely.",
            "Outlook good.",
            "Yes.",
            "Signs point to yes.",
            "Reply hazy, try again.",
            "Ask again later.",
            "Better not tell you now.",
            "Cannot predict now.",
            "Concentrate and ask again.",
            "Don't count on it.",
            "My reply is no.",
            "My sources say no.",
            "Outlook not so good.",
            "Very doubtful."
    );
    @Override
    public String getCommandName() {
        return "!8ball";
    }

    @Override
    public String getCommandDescription() {
        return "**!8ball** [anything]. Ask the magic 8 ball a question!";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(responses.size());
        event.getChannel().sendMessage(responses.get(index)).queue();
    }
}
