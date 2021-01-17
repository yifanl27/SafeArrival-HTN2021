package main.bot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; 

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public static JDABuilder builder;
    public static JDA jda; 
    public static String prefix = "~";
    public static boolean yes = false; 
    public static HashMap<String, HashMap<LocalTime, Reminder>> newTimes = new HashMap<>(); 
    public static HashMap<String, HashMap<LocalTime, Reminder>> secondTime = new HashMap<>(); 

    public static void main(String[] args) throws LoginException, InterruptedException {
        
    	String token = ""; //Token was removed to remain private, please insert token here
    	
    	builder = JDABuilder.createDefault(token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);

        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.watching("Safety"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        builder.addEventListeners(new Commands());
        
        builder.addEventListeners(new GuildMemberJoin());

        RoleReactions roleReactions = new RoleReactions();
        builder.addEventListeners(roleReactions);

        jda = builder.build();   
    }
}