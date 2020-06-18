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

package net.ree_jp.reefseichi.system.seichi

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.seichi.api.SeichiAPI
import net.ree_jp.reefseichi.system.seichi.api.SeichiStatusAPI
import net.ree_jp.reefseichi.system.seichi.data.SeichiData
import net.ree_jp.reefseichi.system.seichi.data.Skill
import net.ree_jp.reefseichi.system.seichi.event.SeichiListener
import net.ree_jp.reefseichi.system.seichi.event.SeichiStatusListener
import net.ree_jp.reefseichi.system.seichi.sql.SeichiHelper

class ReefSeichi {

    companion object {

        private lateinit var instance: ReefSeichi

        fun getInstance(): ReefSeichi {
            if (!::instance.isInitialized) {
                instance = ReefSeichi()
            }
            return instance
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(SeichiListener(), plugin)
            pm.registerEvents(SeichiStatusListener(), plugin)

            Server.getInstance().scheduler.scheduleRepeatingTask(
                ReefSeichiPlugin.getInstance(),
                { getInstance().getStatusAPI().showStatusAll() },
                20
            )
        }
    }

    private lateinit var api: SeichiAPI

    private lateinit var statusApi: SeichiStatusAPI

    private lateinit var helper: SeichiHelper

    private val levelList = mapOf(
        1 to 1, 2 to 100, 3 to 1000, 4 to 2500, 5 to 5000,
        6 to 7000, 7 to 9000, 8 to 11000, 9 to 13000, 10 to 15000,
        11 to 18000, 12 to 21000, 13 to 24000, 14 to 27000, 15 to 30000
    )

    fun getAPI(): SeichiAPI {
        if (!::api.isInitialized) {
            api = SeichiAPI(getHelper())
        }
        return api
    }

    fun getStatusAPI(): SeichiStatusAPI {
        if (!::statusApi.isInitialized) {
            statusApi = SeichiStatusAPI()
        }
        return statusApi
    }

    fun getHelper(): SeichiHelper {
        if (!::helper.isInitialized) {
            helper = SeichiHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
        }
        return helper
    }

    fun getLevel(xp: Int): Int {
        for (level in levelList) {
            if (level.value > xp) {
                return level.key - 1
            }
        }
        throw Exception("適応したレベルが見つかりませんでした")
    }

    fun getNextLevel(xp: Int): Int {
        val level = getLevel(xp)
        val needXp = levelList[level] ?: throw Exception("不正なレベル")
        return needXp - xp
    }

    fun getDefault(xuid: String): SeichiData {
        return SeichiData(
            xuid,
            getDefaultSkill(),
            listOf(getDefaultSkill()),
            0,
            0
        )
    }

    private fun getDefaultSkill(): Skill {
        return Skill("default", 0, 1, 1, 1, 1)
    }
}