package xyz.gnarbot.gnar.handlers.servers;


import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import xyz.gnarbot.gnar.handlers.commands.CommandDistributor;

class ShardListener extends ListenerAdapter
{
    private final Shard shard;
    
    public ShardListener(Shard shard)
    {
        this.shard = shard;
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (!event.getAuthor().isBot()
                && CommandDistributor.INSTANCE.checkValid(event.getMessage().getContent()))
        {
            Host host = shard.getHost(event.getGuild());
    
            if (host != null) host.handleMessageEvent(event);
        }
    }
    
    @Override
    public void onFriendRequestReceived(FriendRequestReceivedEvent event)
    {
        event.getFriendRequest().accept().queue();
    }
    
    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        shard.update();
    }
    
    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        shard.update();
    }
}
