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

package net.ree_jp.reefseichi.data

import cn.nukkit.Server
import cn.nukkit.level.Position
import cn.nukkit.math.Vector3

data class Vec3Point(
    val x: Double,
    val y: Double,
    val z: Double
) : DataJson() {

    companion object {

        fun create(pos: Vector3): Vec3Point {
            return Vec3Point(pos.x, pos.y, pos.z)
        }
    }

    fun toVector3(): Vector3 {
        return Vector3(x, y, z)
    }
}

data class Level(
    val level: String
) : DataJson() {

    companion object {

        fun create(level: cn.nukkit.level.Level): Level {
            return Level(level.folderName)
        }
    }

    fun toLevel(): cn.nukkit.level.Level {
        return Server.getInstance().getLevelByName(level)
    }
}

data class PosPoint(
    val x: Double,
    val y: Double,
    val z: Double,
    val level: Level
) : DataJson() {

    companion object {

        fun create(pos: Position): PosPoint {
            return PosPoint(pos.x, pos.y, pos.z, Level.create(pos.level))
        }
    }

    fun toPosition(): Position {
        return Position(x, y, z, level.toLevel())
    }
}