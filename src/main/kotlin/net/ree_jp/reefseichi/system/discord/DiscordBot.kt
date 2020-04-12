package net.ree_jp.reefseichi.system.discord

import cn.nukkit.Server
import net.ayataka.kordis.DiscordClient
import net.ree_jp.reefseichi.event.DiscordListener

class DiscordBot(private val client: DiscordClient) {

    private val serverId = 638760361369010177
    private val categoryId = 697805835312562186

    val channelId = 697806029605306379

    init {
        client.addListener(DiscordListener())
    }

    suspend fun sendMessage(text: String) {
        try {
            client.servers.find(serverId)?.textChannels?.find(channelId)?.send(text)
        } catch (ex: Exception) {
            Server.getInstance()
                .broadcastMessage("discordに接続できませんでした" + ex.message)
        }
    }
}