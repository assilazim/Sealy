package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class GoogleSearchCommand implements Command {

    @Override
    public String getCommandName() {
        return "!google";
    }

    @Override
    public String getCommandDescription() {
        return "**!google** [query] Returns the top 3 most popular Google searches for the given query";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        try {
            if (args.length < 2) {
                event.getChannel().sendMessage("Please provide a query").queue();
            } else {
                String query = args[1];
                String url = "https://www.google.com/search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                HttpGet httpGet = new HttpGet(url);
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpResponse response = httpClient.execute(httpGet);

                Document doc = Jsoup.parse(response.getEntity().getContent(), "utf-8", url);
                Elements elements = doc.select("div.g > div > div > div.rc > h3 > a");
                if (elements.isEmpty()) {
                    event.getChannel().sendMessage("No results found for '" + query + "'").queue();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Top 3 most popular results for '").append(query).append("':\n");
                    for (int i = 0; i < 3; i++) {
                        Element element = elements.get(i);
                        sb.append(element.text()).append(" - ").append(element.attr("href")).append("\n");
                    }
                    event.getChannel().sendMessage(sb.toString()).queue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}