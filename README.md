# Sealy Bot 

Sealy is a Discord bot written in Java, using the JDA library to interact with the Discord API. Sealy is designed to provide an easy-to-use, fun and interactive bot to make your Discord server more enjoyable.
Say so-long to the days of iterating through various tabs and/or sifting along multiple websites only for basic information. Sealy Bot is here to save the day!

## Features

Sealy has a variety of commands that users can use to interact with the bot. These commands include:

- **!ping** - A simple command to check if the bot is online and responding.
- **!calc** - A calculator command to perform simple operations.
- **!help** - A command to provide information about the bot and its commands.
- **!magic8** - An interactive magic 8-ball command.
- **!trig** - A command to perform simple trigonometry operations.
- **!google** - A command to search google for a specific query.
- **!reddit** - A command to search reddit for a specific query.
- **!todo** - A command to manage your to-do list.
- **!remindme** - A command to set reminders and be notified when they're due.
- **!random** - A command to generate a random number.
- **!botinvite** - A command to generate an invite link for the bot.

## Configuration 
1. To configure Sealybot, you will need to rename `.env-sample` to `.env` and provide the following environment variables: 
* DISCORD_TOKEN - Your Discord API token. This can be obtained from the Discord Developer Portal. 
* DB_HOST - The host of your database. This should be set to 'localhost'. 
* DB_PORT - The port of your database. The default port for MySQL is 3306. 
* DB_NAME - The name of your database. * DB_USER - The username for your database. 
* DB_PASS - The password for your database. 
* DOCKER_CONTAINER - The name of your Docker container. 
* MYSQL_HOST - The host of your MySQL instance. This should be set to 'localhost'. 
* MYSQL_PORT - The port of your MySQL instance. The default port for MySQL is 3306. 
* MYSQL_USER - The username for your MySQL instance. 
* MYSQL_PASS - The password for your MySQL instance. 

2. Once you have provided these environment variables, you will need to rename `docker-compose-sample.yml` to `docker-compose.yml` and update lines 11 and 12 with personal DB information.

3. Then, build and run the Docker container. 
* To do this, you can use the following commands: 
* ` docker built -t sealybot .` 
* `docker-compose up -d` 



