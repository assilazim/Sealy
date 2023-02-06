package commands;

import assilazim.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoListCommand implements Command {
    // MySQL and Docker Variables
    private final String DB_HOST = System.getenv("DB_HOST");
    private final String DB_PORT = System.getenv("DB_PORT");
    private final String DB_NAME = System.getenv("DB_NAME");
    private final String DB_USER = System.getenv("DB_USER");
    private final String DB_PASS = System.getenv("DB_PASS");
    private final String DOCKER_CONTAINER = System.getenv("DOCKER_CONTAINER");
    private final String MYSQL_HOST = System.getenv("MYSQL_HOST");
    private final String MYSQL_PORT = System.getenv("MYSQL_PORT");
    private final String MYSQL_USER = System.getenv("MYSQL_USER");
    private final String MYSQL_PASS = System.getenv("MYSQL_PASS");

    // Other variables
    private static List<String> todoList = new ArrayList<>();

    @Override
    public String getCommandName() {
        return "!todo";
    }

    @Override
    public String getCommandDescription() {
        return "**!todo** [action] [query] - A simple command to manage your todo list\n" +
                "The available actions are add, view, complete, and delete. The add action " +
                "allows users to add an entry to their to-do list, the view action displays " +
                "the user's current to-do list, the complete action marks an entry as completed, and the delete " +
                "action removes an entry from the to-do list.";
    }

    @Override
    public void handleCommand(String[] args, MessageReceivedEvent event) {
        if (args.length < 2) {
            // Connect to the database to retrieve the current todo list
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + DB_NAME + "?user=" + MYSQL_USER + "&password=" + MYSQL_PASS);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT title FROM tasks");
                while (rs.next()) {
                    todoList.add(rs.getString("title"));
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            // Connect to the docker container to execute queries
            event.getChannel().sendMessage("Your current todo list:").queue();
            for (String item : todoList) {
                event.getChannel().sendMessage(item).queue();
            }
        } else if (args[1].equalsIgnoreCase("add")) {
            // Insert the new task into the database
            String userQuery = args[2];
            String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + DB_NAME + "?user=" + MYSQL_USER + "&password=" + MYSQL_PASS);

                String query = "INSERT INTO tasks (user_id, user_query, when_added, is_completed) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, event.getAuthor().getId());
                preparedStmt.setString(2, userQuery);
                preparedStmt.setString(3, currentDateTime);
                preparedStmt.setString(4, "false");
                preparedStmt.executeUpdate();

                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            todoList.add(args[2]);
            event.getChannel().sendMessage("Task added to todo list").queue();
        } else if (args[1].equalsIgnoreCase("delete")) {
            // Delete the task from the database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + DB_NAME + "?user=" + MYSQL_USER + "&password=" + MYSQL_PASS);

                String query = "DELETE FROM tasks WHERE user_query = ?";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, args[2]);
                preparedStmt.executeUpdate();

                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            todoList.remove(args[2]);
            event.getChannel().sendMessage("Task deleted from todo list").queue();
        } else if (args[1].equalsIgnoreCase("mark")) {
            // Update the task in the database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + DB_NAME + "?user=" + MYSQL_USER + "&password=" + MYSQL_PASS);

                String query = "UPDATE tasks SET is_completed = ? WHERE user_query = ?";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, "true");
                preparedStmt.setString(2, args[2]);
                preparedStmt.executeUpdate();

                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            todoList.remove(args[2]);
            event.getChannel().sendMessage("Task marked as completed").queue();
        }
    }
}
