package assilazim;

import commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class CommandHandler extends ListenerAdapter {
    private static HashMap<String, Command> commands = new HashMap<>();
    private final static String PREFIX = "!";
    public static void registerCommand(String commandName, Command command) {
        commands.put(commandName.toLowerCase(), command);
    }

    public static void registerCommands(Command... commands) {
        for (Command command : commands) {
            registerCommand(command.getCommandName().toLowerCase(), command);
        }
    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    static {
        registerCommands(
                new PingCommand(),
                new CalculatorCommand(),
                new HelpCommand(),
                new Magic8BallCommand(),
                new TrigonometryCommand(),
                new GoogleSearchCommand(),
                new RedditCommand(),
                new TodoListCommand(),
                new RemindMeCommand(),
                new RandomCommand(),
                new BotInviteCommand()
        );
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(PREFIX) || event.getAuthor().isBot()) {
            return;
        }
        String commandName = message.split(" ")[0];
        Command command = commands.get(commandName);
        if (command != null) {
            String[] args = message.split(" ");
            command.handleCommand(args, event);
        }
    }
}
