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

import cn.nukkit.entity.Entity
import cn.nukkit.level.generator.Normal
import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import net.ree_jp.reefseichi.entity.FloatingTextEntity
import net.ree_jp.reefseichi.event.EventListener
import net.ree_jp.reefseichi.event.FormListener
import net.ree_jp.reefseichi.system.ban.ReefBan
import net.ree_jp.reefseichi.system.customMessage.ReefCustomMessage
import net.ree_jp.reefseichi.system.discord.ReefDiscord
import net.ree_jp.reefseichi.system.fly.ReefFly
import net.ree_jp.reefseichi.system.protect.ReefProtect
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import java.util.*

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
        entityRegister()
        loadWorld()
        Thread.sleep(1000)
        ReefDiscord.getBot().sendMessage("サーバーが起動しました")
        logger.info(TextFormat.GREEN.toString() + "Reef" + TextFormat.YELLOW + "Seichi" + TextFormat.LIGHT_PURPLE + "Enable")
        super.onEnable()
    }

    override fun onDisable() {
        removeTempEntity()
        writeData()
        ReefDiscord.getBot().sendMessage("サーバーを停止しました")
        Thread.sleep(300)
        logger.info(TextFormat.GREEN.toString() + "Reef" + TextFormat.YELLOW + "Seichi" + TextFormat.GRAY + "Disable")
        super.onDisable()
    }

    fun getKey(type: String): String {
        if (!config.exists(type)) throw Exception("キーがありません")

        return config.get(type, String())
    }

    private fun loadWorld() {
        server.generateLevel("dig_1", Random().nextLong(), Normal::class.java)
        server.generateLevel("dig_2", Random().nextLong(), Normal::class.java)
        server.generateLevel("dig_3", Random().nextLong(), Normal::class.java)
        server.loadLevel("dig_1")
        server.loadLevel("dig_2")
        server.loadLevel("dig_3")
    }

    private fun listenerRegister() {
        val pm = server.pluginManager

        pm.registerEvents(EventListener(), this)
        pm.registerEvents(FormListener(), this)

        ReefBan.registerListener(this)
        ReefCustomMessage.registerListener(this)
        ReefDiscord.registerListener(this)
        ReefSeichi.registerListener(this)
        ReefProtect.registerListener(this)
        ReefFly.registerListener(this)

        ReefDiscord.login()
    }

    private fun writeData() {
        ReefSeichi.getInstance().getHelper().save()
    }

    private fun entityRegister() {
        Entity.registerEntity("FloatingText", FloatingTextEntity::class.java)
    }

    private fun removeTempEntity() {
        for (level in server.levels.values) {
            for (entity in level.entities) {
                if (entity is FloatingTextEntity && entity.isTempEntity) entity.close()
            }
        }
    }

    private fun showStartMessage() {
        print("\nRRRRRR                 fff  SSSSS         iii        hh      iii  SSSSS\n")
        print("RR   RR   eee    eee  ff   SS        eee        cccc hh          SS        eee  rr rr  vv   vv   eee  rr rr\n")
        print("RRRRRR  ee   e ee   e ffff  SSSSS  ee   e iii cc     hhhhhh  iii  SSSSS  ee   e rrr  r  vv vv  ee   e rrr  r \n")
        print("RR  RR  eeeee  eeeee  ff        SS eeeee  iii cc     hh   hh iii      SS eeeee  rr       vvv   eeeee  rr\n")
        print("RR   RR  eeeee  eeeee ff    SSSSS   eeeee iii  ccccc hh   hh iii  SSSSS   eeeee rr        v     eeeee rr \n")
    }
}