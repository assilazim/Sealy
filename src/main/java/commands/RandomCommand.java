package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class RandomCommand implements Command {

    private static final Random random = new Random();

    @Override
    public String getCommandName() {
        return "!random";
    }

    @Override
    public String getCommandDescription() {
        return "**!random** coin - flip a coin; \n" +
                "**!random** dice - roll a die; " +
                "**!random** card - draw a card; " +
                "**!random** choose option1 | option2 | option3 | ... " +
                "- choose an option; !random between [number1] [number2] - get a number in that range (inclusive).";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        if (args.length < 2) {
            event.getChannel().sendMessage("Invalid command usage. Please use !random [coin, dice, card, choose, between]").queue();
            return;
        }

        switch (args[1]) {
            case "coin":
                event.getChannel().sendMessage("You flipped a " + (random.nextBoolean() ? "heads" : "tails") + "!").queue();
                break;
            case "dice":
                event.getChannel().sendMessage("You rolled a " + (random.nextInt(6) + 1) + "!").queue();
                break;
            case "card":
                event.getChannel().sendMessage("You drew a " + getRandomCard() + "!").queue();
                break;
            case "choose":
                if (args.length < 3) {
                    event.getChannel().sendMessage("Please provide at least two options to choose from").queue();
                    return;
                }
                String[] options = args[2].split("\\|");
                event.getChannel().sendMessage("I choose " + options[random.nextInt(options.length)].trim() + "!").queue();
                break;
            case "between":
                if (args.length < 4) {
                    event.getChannel().sendMessage("Please provide two numbers to choose from").queue();
                    return;
                }
                int min = Integer.parseInt(args[2]);
                int max = Integer.parseInt(args[3]);
                event.getChannel().sendMessage("I choose " + (random.nextInt(max - min + 1) + min) + "!").queue();
                break;
            default:
                event.getChannel().sendMessage("Invalid command usage. Please use !random [coin, dice, card, choose, between]").queue();
                break;
        }
    }

    private String getRandomCard() {
        String suit = "";
        switch (random.nextInt(4)) {
            case 0:
                suit = "hearts";
                break;
            case 1:
                suit = "diamonds";
                break;
            case 2:
                suit = "clubs";
                break;
            case 3:
                suit = "spades";
        }
        int rank = random.nextInt(13) + 1;
        return getRankName(rank) + " of " + suit;
    }

    private String getRankName(int rank) {
        switch (rank) {
            case 1:
                return "Ace";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return String.valueOf(rank);
        }
    }
}