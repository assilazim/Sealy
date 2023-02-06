package assilazim;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;


public class Main {
    public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();

    try {
        JDA bot = JDABuilder.createDefault(dotenv.get("DISCORD_TOKEN"))
                .setActivity(Activity.watching("Waiting for Requests"))
                .addEventListeners(new CommandHandler())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .build();
    }
    catch(Exception e) {
        System.out.print(e.getMessage());
        System.exit(0);
        }
    }
}
