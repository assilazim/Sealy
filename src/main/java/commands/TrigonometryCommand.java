package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TrigonometryCommand implements Command {

    @Override
    public String getCommandName() {
        return "!trig";
    }

    @Override
    public String getCommandDescription() {
        return "**!trig** [function] [value] Calculates a trigonometry function for the provided parameters";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        try {
            String functionName = args[1];
            double value = Double.parseDouble(args[2]);
            double result;
            switch (functionName) {
                case "sin":
                    result = Math.sin(value);
                    break;
                case "cos":
                    result = Math.cos(value);
                    break;
                case "tan":
                    result = Math.tan(value);
                    break;
                case "csc":
                    result = 1 / Math.sin(value);
                    break;
                case "sec":
                    result = 1 / Math.cos(value);
                    break;
                case "cot":
                    result = 1 / Math.tan(value);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            event.getChannel().sendMessage(String.format("%.2f", result)).queue();
        } catch (IllegalArgumentException e) {
            event.getChannel().sendMessage("Invalid command parameters").queue();
        }
    }
}
