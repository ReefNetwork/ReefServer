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

package net.ree_jp.reefseichi.system.seichi.sql

import com.google.gson.Gson
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.reefseichi.system.seichi.data.SeichiData
import net.ree_jp.reefseichi.system.seichi.data.Skill
import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class SeichiHelper(path: String) : ISeichiHelper {

    private val connection: Connection

    private val data = mutableMapOf<String, SeichiData>()

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path/seichi.db")
            createTable()
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    override fun isExists(xuid: String): Boolean {
        val stmt = connection.prepareStatement("SELECT * FROM seichi WHERE xuid = ?")
        stmt.setString(1, xuid)
        return stmt.executeQuery().next()
    }

    override fun getData(xuid: String): SeichiData {
        return data[xuid] ?: read(xuid)
    }

    override fun setData(xuid: String, seichiData: SeichiData) {
        data[xuid] = seichiData
    }

    override fun save() {
        for (seichiData in data) {
            write(seichiData.value)
        }
        data.clear()
    }

    private fun read(xuid: String): SeichiData {
        if (!isExists(xuid)) write(ReefSeichi.getInstance().getDefault(xuid))

        val stmt = connection.prepareStatement("SELECT * FROM seichi WHERE xuid = ?")
        stmt.setString(1, xuid)
        val result = stmt.executeQuery()

        val seichi = ReefSeichi.getInstance()
        val skillName = result.getString("skill")
        val skillsName = result.getString("skills").split("*separator*")
        val skills = mutableListOf<Skill>()
        for (skill in skillsName) {
            skills.add(seichi.skills[skill] ?: throw Exception("所持スキル:$skill が見つかりませんでした"))
        }
        return SeichiData(
            xuid,
            seichi.skills[skillName] ?: throw Exception("選択中のスキル:$skillName が見つかりませんでした"),
            skills,
            result.getInt("xp"),
            result.getInt("mana")
        )
    }

    private fun write(seichiData: SeichiData) {
        val skills = mutableListOf<String>()
        for (skill in seichiData.skills) {
            skills.add(skill.name)
        }
        val stmt = connection.prepareStatement("REPLACE INTO seichi VALUES (?, ?, ?, ?, ?)")
        stmt.setString(1, seichiData.xuid)
        stmt.setString(2, seichiData.skill.name)
        stmt.setString(3, skills.joinToString("*separator*"))
        stmt.setInt(4, seichiData.xp)
        stmt.setInt(5, seichiData.mana)
        stmt.execute()
    }

    private fun createTable() {
        connection.createStatement()
            .execute("CREATE TABLE IF NOT EXISTS seichi(xuid TEXT NOT NULL PRIMARY KEY ,skill TEXT NOT NULL ,skills TEXT NOT NULL ,xp INTEGER NOT NULL ,mana INTEGER NOT NULL )")
    }

    private fun getSKill(json: String): Skill {
        return Gson().fromJson(json, Skill::class.java)
    }
}