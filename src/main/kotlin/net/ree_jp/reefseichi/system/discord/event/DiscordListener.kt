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