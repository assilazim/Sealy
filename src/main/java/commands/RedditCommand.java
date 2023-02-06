package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RedditCommand implements Command {

    private static final String BASE_URL = "https://www.reddit.com/r/";
    private static final String TOP_POSTS_URL = "/top.json?limit=5";

    @Override
    public String getCommandName() {
        return "!reddit";
    }

    @Override
    public String getCommandDescription() {
        return "**reddit** [name of subreddit] - Retrieves the top 5 hottest posts of the given Reddit subreddit";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        if (args.length < 2) {
            event.getChannel().sendMessage("Please provide a valid subreddit name").queue();
            return;
        }

        String subredditName = args[1];
        String apiUrl = BASE_URL + subredditName + TOP_POSTS_URL;

        // Use Gson to parse the JSON response
        JsonParser parser = new JsonParser();
        JsonObject response = parser.parse(apiUrl).getAsJsonObject();
        JsonArray posts = response.get("data").getAsJsonObject().get("children").getAsJsonArray();

        String message = String.format("The top 5 hottest posts in %s are: \n", subredditName);
        for (int i = 0; i < posts.size(); i++) {
            JsonObject post = posts.get(i).getAsJsonObject().get("data").getAsJsonObject();
            int score = post.get("score").getAsInt();
            String title = post.get("title").getAsString();
            message += String.format("%d. %s (Score: %d)\n", i + 1, title, score);
        }

        event.getChannel().sendMessage(message).queue();
    }
}