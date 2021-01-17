# SafeArrival-HTN2021

This repository contains the Java files required to run SafeArrival, a discord bot created for Hack the North 2020++. 

General Information
-----
The purpose of this bot is to generate an appropriate alert system to ensure the safety of individuals as they leave their homes/dorms and travel elsewhere for short periods of time. Individuals input the amount of time they will leave for (in minutes), and the bot will check in to see if they have arrived when the time comes. If they have not arrived five minutes after the given time, then the bot will ping a higher role so that to indicate that the user is missing. 

Libraries
-----
Please download the most recent JDA library with dependencies from https://github.com/DV8FromTheWorld/JDA. 

Notes
-----
- The token for our SafeArrival bot was not included for privacy reasons. In 'Main.java', please insert your own bot's token in the indicated line. 
- The roles currently contain the ID of it in our given server. Create the roles in your own discord server, and get the IDs to assign them in 'RoleReactions.java'. 
