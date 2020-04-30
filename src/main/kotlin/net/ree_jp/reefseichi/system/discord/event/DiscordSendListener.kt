/*
 * RRRRRR                         jjj
 * RR   RR   eee    eee               pp pp
 * RRRRRR  ee   e ee   e _____    jjj ppp  pp
 * RR  RR  eeeee  eeeee           jjj pppppp
 * RR   RR  eeeee  eeeee          jjj pp
 *                              jjjj  pp
 *
 * Copyright (c) 2020. Ree-jp.  All Rights Reserved.
 */

package net.ree_jp.reefseichi.system.discord.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.system.discord.ReefDiscord

class DiscordSendListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun joinForSendDiscord(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name

        try {
            ReefDiscord.getBot().sendMessage("$n さんがログインしました")
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun quitForSendDiscord(ev: PlayerQuitEvent) {
        val p = ev.player
        val n = p.name
        val reason = ev.reason

        try {
            ReefDiscord.getBot().sendMessage("$n さんが$reason でログアウトしました")
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun chatForSendDiscord(ev: PlayerChatEvent) {
        val p = ev.player
        val n = p.name
        val message = ev.message

        try {
            ReefDiscord.getBot().sendMessage("[$n] `$message`")
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }
}