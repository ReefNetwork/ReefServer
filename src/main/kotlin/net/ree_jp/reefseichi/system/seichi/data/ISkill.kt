package net.ree_jp.reefseichi.system.seichi.data

import cn.nukkit.Player
import cn.nukkit.math.Vector3

interface ISkill {

    val isCool: Boolean
        get() = false

    val coolTime: Int

    val height: Int

    val wight: Int

    val depth: Int

    fun skillRange(vec3: Vector3, p: Player): List<Vector3>
}