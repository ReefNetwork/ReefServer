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

package net.ree_jp.reefseichi.system.mywarp.sql

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.sql.SqliteHelper

class MyWarpHelper : SqliteHelper("${ReefSeichiPlugin.getInstance().dataFolder}/mywarp.db") {

    fun getAllKey(xuid: String): List<String> {
        if (!isExists(xuid)) return listOf()

        val keys = mutableListOf<String>()
        val result = connection.createStatement().executeQuery("SELECT key FROM '$xuid'")
        while (result.next()) keys.add(result.getString("key"))
        return keys
    }
}