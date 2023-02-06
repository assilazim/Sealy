package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RemindMeCommand implements Command {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public String getCommandName() {
        return "!remindme";
    }

    @Override
    public String getCommandDescription() {
        return "**!remindme** [number] [s, seconds, m, minutes, h, hours] [reminder] - Set a reminder; Rosie will send you a direct message reminding you when time is up.";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        if (args.length < 4) {
            event.getChannel().sendMessage("Invalid command usage. Please use !remindme [number] [s, seconds, m, minutes, h, hours] [reminder]").queue();
            return;
        }

        int duration = Integer.parseInt(args[1]);
        String unit = args[2];
        String reminder = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);

        long durationInMillis = 0;
        switch (unit) {
            case "s":
            case "seconds":
                durationInMillis = TimeUnit.SECONDS.toMillis(duration);
                break;
            case "m":
            case "minutes":
                durationInMillis = TimeUnit.MINUTES.toMillis(duration);
                break;
            case "h":
            case "hours":
                durationInMillis = TimeUnit.HOURS.toMillis(duration);
                break;
            default:
                event.getChannel().sendMessage("Invalid time unit. Please use s, seconds, m, minutes, h, hours").queue();
                return;
        }

        LocalDateTime reminderTime = LocalDateTime.now().plus(Duration.ofMillis(durationInMillis));
        Runnable reminderRunnable = () -> event.getAuthor().openPrivateChannel().complete().sendMessage("Reminder: " + reminder).queue();
        scheduler.schedule(reminderRunnable, durationInMillis, TimeUnit.MILLISECONDS);

        event.getChannel().sendMessage("Reminder set for " + reminderTime.toString()).queue();
    }
}
