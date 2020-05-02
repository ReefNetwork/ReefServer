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
import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import kotlinx.coroutines.runBlocking
import net.ayataka.kordis.DiscordClient
import net.ayataka.kordis.Kordis
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.discord.event.DiscordSendListener

class ReefDiscord {

    companion object {
        private lateinit var bot: DiscordBot

        fun login() {
            try {
                Server.getInstance()
                    .logger.info(TextFormat.GREEN.toString()+">> "+TextFormat.RESET+"login discord...")
                runBlocking { bot = DiscordBot(ReefDiscord().createClient()) }
                Server.getInstance()
                    .logger.info(TextFormat.GREEN.toString()+">> "+TextFormat.RESET+"success...")
            }catch (ex: Exception) {
                Server.getInstance()
                    .logger.info(TextFormat.RED.toString() + ">> " + TextFormat.RESET + ex.message)
            }
        }

        fun getBot(): DiscordBot {
            if (!::bot.isInitialized) {
                login()
            }
            return bot
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(DiscordSendListener(), plugin)
        }
    }

    suspend fun createClient(): DiscordClient {
        return Kordis.create {
            token = ReefSeichiPlugin.getInstance().getKey(ReefSeichiPlugin.DISCORD_TOKEN)
        }
    }
}