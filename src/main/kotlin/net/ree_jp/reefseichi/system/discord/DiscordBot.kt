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

package net.ree_jp.reefseichi.system.discord

import cn.nukkit.Server
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.ayataka.kordis.DiscordClient
import net.ayataka.kordis.entity.server.channel.text.ServerTextChannel
import net.ree_jp.reefseichi.event.DiscordListener

class DiscordBot(private val client: DiscordClient) {

    private val serverId = 638760361369010177
    private val categoryId = 697805835312562186

    val channelId = 697806029605306379

    init {
        client.addListener(DiscordListener())
    }

    fun sendMessage(text: String) {
        try {
            GlobalScope.launch {
                getChannel().send(text)
            }
        } catch (ex: Exception) {
            Server.getInstance()
                .broadcastMessage("discordに接続できませんでした" + ex.message)
        }
    }

    private fun getChannel(): ServerTextChannel {
        try {
            return client.servers.find(serverId)?.textChannels?.find(channelId)!!
        } catch (ex: Exception) {
            throw ex
        }
    }
}