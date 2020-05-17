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

package net.ree_jp.reefseichi.system.protect.data

import cn.nukkit.level.Level
import cn.nukkit.level.Position
import cn.nukkit.math.AxisAlignedBB
import cn.nukkit.math.Vector3

class LandData(
    private val min: Vector3,
    private val max: Vector3,
    private val owner: String,
    val id: String,
    val level: Level,
    var subUser: List<String>,
    var spawnPoint: Position,
    var canSkill: Boolean
) : AxisAlignedBB {

    private var entity = 0

    override fun getMinX(): Double {
        return min.getX()
    }

    override fun getMinZ(): Double {
        return min.getZ()
    }

    override fun clone(): LandData {
        return clone(id)
    }

    fun clone(id: String): LandData {
        return LandData(min, max, owner, id, level, subUser, spawnPoint, canSkill)
    }

    override fun getMaxX(): Double {
        return max.getX()
    }

    override fun getMaxZ(): Double {
        return max.getY()
    }

    override fun getMinY(): Double {
        return 0.0
    }

    override fun getMaxY(): Double {
        return 256.0
    }

    fun getOwner(): String {
        return owner
    }

    fun getEntity(): Int {
        return entity
    }

    fun setEntity(count: Int) {
        entity = count
    }

    fun getMaxEntity(): Int {
        TODO()
    }

    fun isMember(xuid: String): Boolean {
        if (owner == xuid) return true
        for (user in subUser) {
            if (user == xuid) return true
        }

        return false
    }
}