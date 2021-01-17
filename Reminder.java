package main.bot;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.entities.TextChannel;

public class Reminder extends Main{
	Timer timer;

	public Reminder(int minutes, boolean which) {
		timer = new Timer("");
		int seconds = 60 * minutes; 
		if(which == false) {
			timer.schedule(new RemindTask(), seconds*1000);
		}
		else if(which == true) {
			timer.schedule(new RemindTaskCall(), seconds*1000);
		}
	}

	class RemindTask extends TimerTask {
		public void run() {
			confirm(timer); 
			timer.cancel(); //Terminate the timer thread
		}
	}
	
	class RemindTaskCall extends TimerTask{
		public void run() {
			call(); 
			timer.cancel(); 
		}
	}
	
	static void confirm(Timer timer)
	{
		String user = ""; 
		
		Date date=java.util.Calendar.getInstance().getTime();
		int hour = date.getHours(); 
		int minutes = date.getMinutes();
		LocalTime time = LocalTime.of(hour, minutes); 

		for(String name: Main.newTimes.keySet()) {
			if (Main.newTimes.get(name).containsKey(time)){
				user = name; 
			}
			break; 
		}
		
		List<TextChannel> channels = jda.getTextChannelsByName("general", true);
		for(TextChannel ch : channels)
		{
			sendMessage(ch, "Hey <@"+ user + ">! Have you arrived yet? ");
		}
		
		Main.newTimes.get(user).remove(time); 
		
		if (Main.secondTime.containsKey(user)) {
			Main.secondTime.get(user).put(time, new Reminder(5, true)); 
		}
		else {
			HashMap<LocalTime, Reminder> newEntry = new HashMap<>(); 
			newEntry.put(time, new Reminder(5, true)); 
			Main.secondTime.put(user, newEntry); 
		}
		
		if (Main.newTimes.get(user).isEmpty()) {
			Main.newTimes.remove(user); 
		}
	}
	
	static void call()
	{
		String user = ""; 
		
		Date date=java.util.Calendar.getInstance().getTime();
		int hour = date.getHours(); 
		int minutes = date.getMinutes();
		LocalTime time = LocalTime.of(hour, minutes).minusMinutes(5);

		for(String name: Main.secondTime.keySet()) {
			if (Main.secondTime.get(name).containsKey(time)){
				user = name; 
			}
			break; 
		}
		
		List<TextChannel> channels = jda.getTextChannelsByName("general", true);
		
		for(TextChannel ch : channels)
		{
			sendMessage(ch, "Hey <@&799894414184153148>! <@" + user +"> is missing, please contact them! ");
		}
		
		Main.secondTime.get(user).remove(time); 
		
		if (Main.secondTime.get(user).isEmpty()) {
			Main.secondTime.remove(user); 
		}
	}
	
	static void sendMessage(TextChannel ch, String msg) 
	{
		ch.sendMessage(msg).queue();
	}
}