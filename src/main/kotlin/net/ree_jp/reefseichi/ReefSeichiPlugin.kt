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

package net.ree_jp.reefseichi

import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import net.ree_jp.reefseichi.event.BanListener
import net.ree_jp.reefseichi.event.CustomMessageListener
import net.ree_jp.reefseichi.event.DiscordSendListener
import net.ree_jp.reefseichi.event.EventListener
import net.ree_jp.reefseichi.system.discord.ReefDiscord

class ReefSeichiPlugin : PluginBase() {

    companion object {

        const val DISCORD_TOKEN = "discordToken"

        private lateinit var instance: ReefSeichiPlugin

        fun getInstance(): ReefSeichiPlugin {
            return instance
        }
    }

    override fun onLoad() {
        instance = this
        super.onLoad()
    }

    override fun onEnable() {
        showStartMessage()
        dataFolder.mkdir()
        saveDefaultConfig()

        listenerRegister()
        Thread.sleep(1000)
        ReefDiscord.getBot().sendMessage("サーバーが起動しました")
        logger.info(TextFormat.GREEN.toString() + "Reef" + TextFormat.YELLOW + "Seichi" + TextFormat.LIGHT_PURPLE + "Enable")
        super.onEnable()
    }

    override fun onDisable() {
        ReefDiscord.getBot().sendMessage("サーバーを停止しました")
        Thread.sleep(300)
        logger.info(TextFormat.GREEN.toString() + "Reef" + TextFormat.YELLOW + "Seichi" + TextFormat.GRAY + "Disable")
        super.onDisable()
    }

    fun getKey(type: String): String {
        if (!config.exists(type)) throw Exception("config key not found")

        return config.get(type, String())
    }

    private fun listenerRegister() {
        val pm = server.pluginManager

        pm.registerEvents(EventListener(), this)
        pm.registerEvents(BanListener(), this)
        pm.registerEvents(CustomMessageListener(), this)
        pm.registerEvents(DiscordSendListener(), this)

        ReefDiscord.login()
    }

    private fun showStartMessage() {
        print("\nRRRRRR                 fff  SSSSS         iii        hh      iii  SSSSS\n")
        print("RR   RR   eee    eee  ff   SS        eee        cccc hh          SS        eee  rr rr  vv   vv   eee  rr rr\n")
        print("RRRRRR  ee   e ee   e ffff  SSSSS  ee   e iii cc     hhhhhh  iii  SSSSS  ee   e rrr  r  vv vv  ee   e rrr  r \n")
        print("RR  RR  eeeee  eeeee  ff        SS eeeee  iii cc     hh   hh iii      SS eeeee  rr       vvv   eeeee  rr\n")
        print("RR   RR  eeeee  eeeee ff    SSSSS   eeeee iii  ccccc hh   hh iii  SSSSS   eeeee rr        v     eeeee rr \n")
    }
}