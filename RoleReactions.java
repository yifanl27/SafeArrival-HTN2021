package main.bot;

import java.util.HashMap;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class RoleReactions extends ListenerAdapter {

    final long channelID = 800078659141894184L;
    final long roleID_RA = 799894414184153148L;
    final long roleID_St = 799847084252987442L;
    HashMap<Long, Long> reactionToRoleID = new HashMap<>();

    public RoleReactions() {
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getTextChannel().getIdLong() == channelID) {
            if (e.getJDA().getRoleById(roleID_RA) == null) {
                return;
            }

            if ((e.getReactionEmote().getName().equals("\uD83C\uDFEB"))) {
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(roleID_RA)).queue();

            } else if ((e.getReactionEmote().getName().equals("\uD83D\uDCDA"))) {
                e.getGuild().addRoleToMember(e.getUserId(), e.getJDA().getRoleById(roleID_St)).queue();
            }

        }
        }

    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        if (e.getTextChannel().getIdLong() == channelID) {
            long roleID = reactionToRoleID.get(e.getReactionEmote().getIdLong());
            e.getGuild().removeRoleFromMember(e.getUserId(), e.getJDA().getRoleById(roleID)).queue();
        }

    }
}