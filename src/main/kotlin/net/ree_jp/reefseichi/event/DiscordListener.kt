package net.ree_jp.reefseichi.event

import cn.nukkit.Server
import cn.nukkit.utils.TextFormat
import net.ayataka.kordis.event.EventHandler
import net.ayataka.kordis.event.events.message.MessageReceiveEvent
import net.ree_jp.reefseichi.system.discord.ReefDiscord

class DiscordListener {

    @EventHandler
    fun onMessageReceived(event: MessageReceiveEvent) {
        val bot = ReefDiscord.getBot()

        val member = event.message.member ?: return
        val channel = event.message.serverChannel ?: return
        val message = event.message

        if (member.bot) return

        if (channel.id == bot.channelId) {
            Server.getInstance()
                .broadcastMessage(TextFormat.AQUA.toString() + "[Discord]" + TextFormat.RESET + "[" + member.name + "] " + message.content)
        }
    }
}