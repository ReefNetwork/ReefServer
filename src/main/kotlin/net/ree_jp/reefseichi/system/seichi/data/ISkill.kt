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
import cn.nukkit.math.Vector3

interface ISkill {

    val name: String

    val isCool: Boolean
        get() = false

    val coolTime: Int

    val height: Int

    val wight: Int

    val depth: Int

    fun skillRange(vec3: Vector3, p: Player): List<Vector3>
}