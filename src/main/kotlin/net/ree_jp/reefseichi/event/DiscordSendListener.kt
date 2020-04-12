package net.ree_jp.reefseichi.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerJoinEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ree_jp.reefseichi.system.discord.ReefDiscord

class DiscordSendListener : Listener {

    @EventHandler
    fun onChat(ev: PlayerChatEvent) {
        val p = ev.player
        val n = p.name
        val message = ev.message

        GlobalScope.launch {
            ReefDiscord.getBot().sendMessage("[$n] `$message`")
        }
    }

    @EventHandler
    fun onJoin(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name

        GlobalScope.launch {
            ReefDiscord.getBot().sendMessage("$n さんがやってきました")
        }
    }
}