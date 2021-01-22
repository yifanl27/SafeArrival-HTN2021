package main.bot;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Note: All channel IDs in this class are specific to a certain Discord server. Please replace these IDs before using the Bot.

public class GuildMemberJoin extends ListenerAdapter{
    String[]messages= { "Welcome [member]! I am Safe Arrival and I will be keeping track of your destinations in order to ensure your safety.",
            "Hello [member], I am Safe Arrival!", "Hi[member]! I am excited to help you have a fun and safe journey."
    };
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Random rand= new Random();
        int number = rand.nextInt(messages.length);
        EmbedBuilder join= new EmbedBuilder();
        join.setColor(0x0D166);
        join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention())+ " Please go to <#800078659141894184> to choose your role!");
        
        long chanID = 800231403550670848L;
        event.getGuild().getTextChannelById(chanID).sendMessage(join.build()).queue();
    }}
