package main.bot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
    static void sendMessage(TextChannel ch, String msg) 
    {
        ch.sendMessage(msg).queue();
    }

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(main.bot.Main.prefix + "info")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Safe Arrival Bot Information ");
            info.setDescription("Hey, I'm the Safe Arrival Bot! I'm here to make sure you and your peers stay safe when travelling around campus."
                    + "Tell me how long you'll be out, and I'll make sure you come back by that time - if you're not back, I'll ping your RAs.");            info.setFooter("Use ~help for a list of commands!");
			info.setColor(0xa83279);

			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}
		
        else if (args[0].equalsIgnoreCase(Main.prefix + "help")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("List of Commands");
            info.setDescription("~info: gives you information on what the bot is used for" + "\n" 
            + "~going: used when the student wants to tell the bot how long they will be gone for" + "\n" 
            + "~times: the bot will state what time the student is meant to be back" + "\n" 
            + "~arrived : used when the student has arrived back to their dorm");
            info.setColor(0xa83279);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }

		else if (args[0].equalsIgnoreCase(main.bot.Main.prefix + "going")) {
            String currentM = event.getMember().getUser().getId();
            
			int time_set = Integer.parseInt(args[1]);
			Reminder remind = new Reminder(time_set, false);
			Date date=java.util.Calendar.getInstance().getTime();
			int hour = date.getHours(); 
			int minutes = date.getMinutes();
			LocalTime time = LocalTime.of(hour, minutes).plusMinutes(new Long(time_set));
			
			if (Main.newTimes.containsKey(currentM) == false) {
				HashMap<LocalTime, Reminder> temp = new HashMap<>(); 
				temp.put(time, remind); 
				Main.newTimes.put(currentM, temp); 
			}
			else {
				Main.newTimes.get(currentM).put(time, remind); 
			}
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Hey <@" + currentM + ">" +"! Please arrive home safely! We will be expecting you to come home at " + time +"! ").queue();
		}
		
		else if (args[0].equalsIgnoreCase(main.bot.Main.prefix + "times")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Arrival Times");
			
			List<String> onTime = new ArrayList(); 
			
			for(String i: Main.newTimes.keySet()) {
				String descriptions = "<@" + i + ">: "; 
				for(LocalTime j: Main.newTimes.get(i).keySet()) {
					descriptions += j + "; "; 
				}
				onTime.add(descriptions); 
			}
				
			String onTimeString = ""; 
			for(int i = 0; i < onTime.size(); i++) {
				onTimeString += "\n" + onTime.get(i); 
			}
			
			List<String> delay = new ArrayList(); 
			
			for(String i: Main.secondTime.keySet()) {
				String descriptions = "<@" + i + ">: "; 
				for(LocalTime j: Main.secondTime.get(i).keySet()) {
					descriptions += j + "; "; 
				}
				delay.add(descriptions); 
			}
				
			String delayString = ""; 
			for(int i = 0; i < delay.size(); i++) {
				delayString += "\n" + delay.get(i); 
			}
			
			info.setDescription("On Time: " + onTimeString + "\n" + "\n" + "Delayed (5 min warning): " + delayString); 
			info.setColor(0xa83279);
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(info.build()).queue();
		}
		
		else if(args[0].equalsIgnoreCase(main.bot.Main.prefix + "arrived")) {
			event.getChannel().sendTyping().queue(); 
			
			String currentM = event.getMember().getUser().getId(); 
			
			if (Main.newTimes.containsKey(currentM) == false && Main.secondTime.containsKey(currentM) == false) {
				event.getChannel().sendMessage("Sorry, there does not appear to be any scheduled arrival times. ").queue(); 
			}
			else if (Main.secondTime.containsKey(currentM) == true) {
				event.getChannel().sendMessage("We are glad to see you have arrived!").queue(); 

				Date date=java.util.Calendar.getInstance().getTime();
				int hour = date.getHours(); 
				int minutes = date.getMinutes(); 
				LocalTime currentTime = LocalTime.of(hour, minutes); 
				
				for(LocalTime i: Main.secondTime.get(currentM).keySet()) {
					if(currentTime.isAfter(i)) {
						currentTime = i; 
					}
				}
				
				Main.secondTime.get(currentM).get(currentTime).timer.cancel(); 
				Main.secondTime.get(currentM).remove(currentTime); 
				
				if (Main.secondTime.get(currentM).isEmpty())
				{
					Main.secondTime.remove(currentM); 
				}
			}
			else {
				event.getChannel().sendMessage("Glad you arrived safely! ").queue();
				
				Date date=java.util.Calendar.getInstance().getTime();
				int hour = date.getHours(); 
				int minutes = date.getMinutes(); 
				LocalTime currentTime = LocalTime.of(1, 1); 
				
				for(LocalTime i: Main.newTimes.get(currentM).keySet()) {
					currentTime = i; 
					if(currentTime.isBefore(i)) {
						currentTime = i; 
					}
				}
				
				Main.newTimes.get(currentM).get(currentTime).timer.cancel();
				Main.newTimes.get(currentM).remove(currentTime); 
				
				if (Main.newTimes.get(currentM).isEmpty())
				{
					Main.newTimes.remove(currentM); 
				}
			}
		}
		
//		else if(args[0].equalsIgnoreCase(main.bot.Main.prefix + "~add")) {
//			
//		}
	}


}
