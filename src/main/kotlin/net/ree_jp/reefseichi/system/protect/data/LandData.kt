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
import net.ree_jp.reefseichi.data.PosPoint
import net.ree_jp.reefseichi.data.Vec3Point

data class LandData(
    private val min: Vec3Point,
    private val max: Vec3Point,
    private val owner: String,
    val id: String,
    private val level: net.ree_jp.reefseichi.data.Level,
    var subUser: List<String>,
    private var spawnPoint: PosPoint,
    var canSkill: Boolean
) : AxisAlignedBB {

    companion object {

        fun create(
            min: Vector3,
            max: Vector3,
            xuid: String,
            id: String,
            level: Level,
            subUser: List<String>,
            point: Position,
            canSkill: Boolean
        ): LandData {
            return LandData(
                Vec3Point.create(min),
                Vec3Point.create(max),
                xuid,
                id,
                net.ree_jp.reefseichi.data.Level.create(level),
                subUser,
                PosPoint.create(point),
                canSkill
            )
        }
    }

    private var entity = 0

    override fun getMinX(): Double {
        return min.toVector3().getX()
    }

    override fun getMinZ(): Double {
        return min.toVector3().getZ()
    }

    override fun clone(): LandData {
        return clone(id)
    }

    fun clone(id: String): LandData {
        return LandData(min, max, owner, id, level, subUser, spawnPoint, canSkill)
    }

    override fun getMaxX(): Double {
        return max.toVector3().getX()
    }

    override fun getMaxZ(): Double {
        return max.toVector3().getZ()
    }

    override fun getMinY(): Double {
        return 0.0
    }

    override fun getMaxY(): Double {
        return 256.0
    }

    fun getLevel(): Level {
        return level.toLevel()
    }

    fun getSpawnPoint(): Position {
        return spawnPoint.toPosition()
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