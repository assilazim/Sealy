package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CalculatorCommand implements Command {

    @Override
    public String getCommandName() {
        return "!calc";
    }

    @Override
    public String getCommandDescription() {
        return "**!calc** [number1] [**+**, **-**, **\\***, **/**, **%**, **^**] [number2] " +
                "- Performs basic arithmetic operations";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        if (args.length == 4) {
            double num1 = Double.parseDouble(args[1]);
            double num2 = Double.parseDouble(args[3]);
            double result = 0;

            switch (args[2]) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    break;
                case "%":
                    result = num1 % num2;
                    break;
                default:
                    event.getChannel().sendMessage("Invalid operator!").queue();
                    return;
            }
                event.getChannel().sendMessage("Result = " + result).queue();
        }
        else {
            event.getChannel().sendMessage("Invalid arguments!").queue();
        }
    }
}
