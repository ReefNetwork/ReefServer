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

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.seichi.api.SeichiAPI
import net.ree_jp.reefseichi.system.seichi.data.SeichiData
import net.ree_jp.reefseichi.system.seichi.data.Skill
import net.ree_jp.reefseichi.system.seichi.sqlite.SeichiHelper

class ReefSeichi {

    companion object {

        private lateinit var instance: ReefSeichi

        fun getInstance(): ReefSeichi {
            if (!::instance.isInitialized) {
                instance = ReefSeichi()
            }
            return instance
        }
    }

    private lateinit var api: SeichiAPI

    private lateinit var helper: SeichiHelper

    fun getAPI(): SeichiAPI {
        if (!::api.isInitialized) {
            api = SeichiAPI(getHelper())
        }
        return api
    }

    fun getHelper(): SeichiHelper {
        if (!::helper.isInitialized) {
            helper = SeichiHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
        }
        return helper
    }

    fun getLevel(xp: Int): Int {
        TODO()
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