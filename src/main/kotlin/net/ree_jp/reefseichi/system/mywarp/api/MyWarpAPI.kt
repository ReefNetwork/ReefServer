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

import cn.nukkit.Server
import cn.nukkit.level.Position
import cn.nukkit.math.Vector3
import com.google.gson.Gson
import net.ree_jp.reefseichi.data.DataJson
import net.ree_jp.reefseichi.system.mywarp.ReefMyWarp

class MyWarpAPI {

    fun getPoint(xuid: String, id: String): Position {
        val helper = ReefMyWarp.getInstance().getHelper()
        val point = Gson().fromJson(helper.getValue(xuid, id), PosPoint::class.java)

        return point.toPosition()
    }

    fun setPoint(xuid: String, id: String, pos: Position) {
        val helper = ReefMyWarp.getInstance().getHelper()
        val point = PosPoint(pos, pos.level.folderName)

        helper.setValue(xuid, id, point.toJson())
    }
}

data class PosPoint(val pos: Vector3, val level: String) : DataJson() {

    fun toPosition(): Position {
        val level = Server.getInstance().getLevelByName(level)
        return Position.fromObject(pos, level)
    }
}