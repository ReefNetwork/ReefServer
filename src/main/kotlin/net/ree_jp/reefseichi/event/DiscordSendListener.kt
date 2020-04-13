package net.ree_jp.reefseichi.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import net.ree_jp.reefseichi.system.discord.ReefDiscord

class DiscordSendListener : Listener {

    @EventHandler
    fun joinForSendDiscord(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name

        ReefDiscord.getBot().sendMessage("$n さんがログインしました")
    }

    @EventHandler
    fun quitForSendDiscord(ev: PlayerQuitEvent) {
        val p = ev.player
        val n = p.name
        val reason = ev.reason

        ReefDiscord.getBot().sendMessage("$n さんが$reason でログアウトしました")
    }

    @EventHandler
    fun chatForSendDiscord(ev: PlayerChatEvent) {
        val p = ev.player
        val n = p.name
        val message = ev.message

        ReefDiscord.getBot().sendMessage("[$n] `$message`")
    }
}