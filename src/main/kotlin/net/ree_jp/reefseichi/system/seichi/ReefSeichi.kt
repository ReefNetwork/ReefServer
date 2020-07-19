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
        1 to 0, 2 to 100, 3 to 1000, 4 to 3500, 5 to 6000,
        6 to 9000, 7 to 12000, 8 to 16000, 9 to 21000, 10 to 25000,
        11 to 30000, 12 to 35000, 13 to 40000, 14 to 45000, 15 to 50000,
        16 to 60000, 17 to 70000, 18 to 80000, 19 to 90000, 20 to 100000,
        21 to 115000, 22 to 130000, 23 to 145000, 24 to 160000, 25 to 175000
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
        val level = getLevel(xp) + 1
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

    fun getDefaultSkill(): Skill {
        return Skill("default", 0, 1, 1, 1, 1)
    }
}