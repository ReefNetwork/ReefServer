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

package net.ree_jp.reefseichi.system.mywarp.api

import cn.nukkit.level.Position
import com.google.gson.Gson
import net.ree_jp.reefseichi.system.mywarp.ReefMyWarp

class MyWarpAPI {

    fun getPoint(xuid: String, id: String): Position {
        val helper = ReefMyWarp.getInstance().getHelper()

        return Gson().fromJson(helper.getValue(xuid, id), Position::class.java)
    }

    fun setPoint(xuid: String, id: String, pos: Position) {
        val helper = ReefMyWarp.getInstance().getHelper()

        helper.setValue(xuid, id, Gson().toJson(pos))
    }
}