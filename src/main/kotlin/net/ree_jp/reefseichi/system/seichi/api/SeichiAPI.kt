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

package net.ree_jp.reefseichi.system.seichi.api

import net.ree_jp.reefseichi.system.seichi.sql.SeichiHelper

class SeichiAPI(private val helper: SeichiHelper) {

    fun addXp(xuid: String, xp: Int) {
        setXp(xuid, getXp(xuid) + xp)
    }

    fun removeXp(xuid: String, xp: Int) {
        val rewrite = getXp(xuid) - xp
        if (rewrite < 0) throw Exception("0以下にxpを設定することはできません")
        setXp(xuid, rewrite)
    }

    fun getXp(xuid: String): Int {
        if (!helper.isExists(xuid)) throw Exception("プレイヤーのデータが見つかりません")

        return helper.getData(xuid).xp
    }

    private fun setXp(xuid: String, xp: Int) {
        if (!helper.isExists(xuid)) throw Exception("プレイヤーのデータが見つかりません")

        val data = helper.getData(xuid)
        data.xp = xp
        helper.setData(xuid, data)
    }
}