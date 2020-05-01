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

package net.ree_jp.reefseichi.system.seichi.api

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.utils.TextFormat
import gt.creeperface.nukkit.scoreboardapi.ScoreboardAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.sqlite.MoreDataHelper
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import java.lang.Exception
import java.time.LocalDateTime

class SeichiStatusAPI {

    companion object {
        const val STATUS_NORMAL_MODE = 0
        const val STATUS_LIGHT_MODE = 1
        const val STATUS_NONE_MODE = 2
        const val STATUS_DEBUG_MODE = 3

        const val STATUS_DATA_KEY = "SeichiStatsAPI_status_key"
    }

    private val builder = ScoreboardAPI.builder()

    private val type = mutableMapOf<String, Int>()

    private var displayColor = 0

    fun setType(xuid: String, mode: Int) {
        MoreDataHelper.getInstance().setValue(xuid, STATUS_DATA_KEY, mode)
    }

    fun showStatusAll() {
        displayColor++
        for (p in Server.getInstance().onlinePlayers.values) {
            try {
                showStatus(p)
            } catch (ex: Exception) {
                p.sendMessage("${ReefNotice.ERROR}${ex.message}")
            }
        }
    }

    private fun showStatus(p: Player) {
        val xuid = p.loginChainData.xuid
        val helper = MoreDataHelper.getInstance()

        if (!type.containsKey(xuid)) type[xuid] = helper.getValue(xuid, STATUS_DATA_KEY, Int::class.java)

        when (type.getValue(xuid)) {
            STATUS_NORMAL_MODE -> showNormal(p)
            STATUS_LIGHT_MODE -> showLight(p)
            STATUS_NONE_MODE -> removeStatus(p)
            STATUS_DEBUG_MODE -> showDebug(p)
        }
    }

    private fun showNormal(p: Player) {
        val n = p.displayName
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val seichiData = seichi.getHelper().getData(xuid)
        val xp = seichiData.xp
        val level = seichi.getLevel(xp)
        val nextXp = seichi.getNextLevel(xp)
        val scoreBoard = builder.build()
        scoreBoard.setDisplayName(getDisplayColor())
        scoreBoard.setScore(1, "", 0)
        scoreBoard.setScore(2, "${TextFormat.GRAY}${LocalDateTime.now()}   ", 1)
        scoreBoard.setScore(3, "", 2)
        scoreBoard.setScore(4, "${TextFormat.GOLD}name${TextFormat.RESET} : $n   ", 3)
        scoreBoard.setScore(5, "", 4)
        scoreBoard.setScore(6, "${TextFormat.RED}level${TextFormat.RESET} : $level   ", 5)
        scoreBoard.setScore(7, "${TextFormat.DARK_GREEN}xp${TextFormat.RESET} : $xp/${nextXp + xp}   ", 6)
        scoreBoard.setScore(8, "${TextFormat.AQUA}mana${TextFormat.RESET} : ${seichiData.mana}", 7)
        scoreBoard.setScore(9, "${TextFormat.DARK_BLUE}skill${TextFormat.RESET} : ${seichiData.skill.name}", 8)
        scoreBoard.setScore(10, "", 9)
        scoreBoard.setScore(11, "${TextFormat.GREEN}world${TextFormat.RESET} : ${p.level.folderName}   ", 10)
        scoreBoard.setScore(12, "${TextFormat.LIGHT_PURPLE}location${TextFormat.RESET} : ${p.x},${p.y},${p.z}   ", 11)
        scoreBoard.setScore(13, "", 12)
        scoreBoard.setScore(14, "reefmcbe.ddo.jp:19132", 13)
        scoreBoard.addPlayer(p)
    }

    private fun showLight(p: Player) {
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val seichiData = seichi.getHelper().getData(xuid)
        val xp = seichiData.xp
        val level = seichi.getLevel(xp)
        val nextXp = seichi.getNextLevel(xp)
        val scoreBoard = builder.build()
        scoreBoard.setDisplayName("Reef Server")
        scoreBoard.setScore(1, "現在のLevel:$level ", 0)
        scoreBoard.setScore(2, "レベルアップまで:$nextXp ", 1)
        scoreBoard.setScore(3, "マナ:${seichiData.mana} ", 2)
        scoreBoard.setScore(4, "現在のスキル:${seichiData.skill.name} ", 3)
        scoreBoard.setScore(5, "座標:${p.floorX},${p.floorY},${p.floorZ} ", 4)
        scoreBoard.addPlayer(p)
    }

    private fun removeStatus(p: Player) {
        val scoreBoard = builder.build()
        scoreBoard.addPlayer(p)
        scoreBoard.removePlayer(p)
    }

    private fun showDebug(p: Player) {
        val n = p.displayName
        val xuid = p.loginChainData.xuid
        val scoreBoard = builder.build()
        scoreBoard.setDisplayName(getDisplayColor())
        scoreBoard.setScore(1, LocalDateTime.now().toString(), 0)
        scoreBoard.setScore(2, n, 0)
        scoreBoard.setScore(3, xuid, 0)
        scoreBoard.addPlayer(p)
    }

    private fun getDisplayColor(): String {
        when (displayColor) {
            1 -> return "${TextFormat.YELLOW}R${TextFormat.RESET}eef Server"
            2 -> return "R${TextFormat.YELLOW}e${TextFormat.RESET}ef Server"
            3 -> return "Re${TextFormat.YELLOW}e${TextFormat.RESET}f Server"
            4 -> return "Ree${TextFormat.YELLOW}f${TextFormat.RESET} Server"
            5 -> return "Reef ${TextFormat.YELLOW}S${TextFormat.RESET}erver"
            6 -> return "Reef S${TextFormat.YELLOW}e${TextFormat.RESET}rver"
            7 -> return "Reef Se${TextFormat.YELLOW}r${TextFormat.RESET}ver"
            8 -> return "Reef Ser${TextFormat.YELLOW}v${TextFormat.RESET}er"
            9 -> return "Reef Serv${TextFormat.YELLOW}e${TextFormat.RESET}r"
            10 -> return "Reef Serve${TextFormat.YELLOW}r${TextFormat.RESET}"
            11, 13, 14, 15 -> return "${TextFormat.YELLOW}ReefServer${TextFormat.RESET}"
        }
        if (displayColor > 18) displayColor = 0
        return "ReefServer"
    }
}