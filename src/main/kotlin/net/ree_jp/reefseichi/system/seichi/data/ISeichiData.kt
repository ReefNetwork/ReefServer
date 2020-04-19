package net.ree_jp.reefseichi.system.seichi.data

interface ISeichiData {

    val xuid: String

    val skill: Skill

    val skills: List<Skill>

    val mana: Int

    val level: Int

    val xp: Int
}