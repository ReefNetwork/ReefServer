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

package net.ree_jp.reefseichi.system.seichi.data

import cn.nukkit.Player
import cn.nukkit.math.BlockFace
import cn.nukkit.math.Vector3
import net.ree_jp.reefseichi.data.DataJson

data class SeichiData(
    override val xuid: String,
    override var skill: Skill,
    override var skills: List<Skill>,
    override var xp: Int,
    override var mana: Int
) : ISeichiData, DataJson()

data class Skill(
    override val name: String,
    override val mana: Int,
    override val coolTime: Int,
    override val depth: Int,
    override val height: Int,
    override val wight: Int
) : ISkill, DataJson() {

    override var isCool: Boolean
        get() = false
        set(value) {}

    override fun skillRange(vec3: Vector3, p: Player): List<Vector3> {
        val result = mutableListOf<Vector3>()
        var sy = -(height - 1) / 2
        var my = p.floorY + height
        val sx: Int
        val mx: Int
        val sz: Int
        val mz: Int
        if (my < vec3.floorY) {
            sy = -(height - 1) / 2
            my = (height - 1) / 2
        }
        when (p.direction) {

            BlockFace.NORTH -> {
                sx = -(wight - 1) / 2
                mx = (wight - 1) / 2
                sz = -(depth - 1)
                mz = 0
            }

            BlockFace.SOUTH -> {
                sx = -(wight - 1) / 2
                mx = (wight - 1) / 2
                sz = 0
                mz = (depth - 1)
            }

            BlockFace.EAST -> {
                sx = -(depth - 1)
                mx = 0
                sz = -(wight - 1) / 2
                mz = (wight - 1) / 2
            }

            BlockFace.WEST -> {
                sx = 0
                mx = (depth - 1)
                sz = -(wight - 1) / 2
                mz = (wight - 1) / 2
            }

            BlockFace.DOWN -> {
                sy = p.floorY - depth
                my = p.floorY - 1
                sx = -(wight - 1) / 2
                mx = (wight - 1) / 2
                sz = -(height - 1) / 2
                mz = (height - 1) / 2
            }

            else -> throw Exception("不明な方角です")
        }
        for (x in sx..mx) {
            for (y in sy..my) {
                for (z in sz..mz) {
                    result.add(vec3.add(x.toDouble(), y.toDouble(), z.toDouble()))
                }
            }
        }
        return result
    }
}