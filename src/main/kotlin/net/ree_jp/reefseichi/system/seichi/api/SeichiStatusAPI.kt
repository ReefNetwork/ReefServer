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
import gt.creeperface.nukkit.scoreboardapi.scoreboard.SimpleScoreboard
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.sql.MoreDataHelper
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import java.time.LocalDateTime

class SeichiStatusAPI {

    companion object {
        const val STATUS_NORMAL_MODE = 0
        const val STATUS_LIGHT_MODE = 1
        const val STATUS_NONE_MODE = 2
        const val STATUS_DEBUG_MODE = 3

        const val STATUS_DATA_KEY = "SeichiStatsAPI_status_key"
    }

    private val scoreboards = mutableMapOf<String, SimpleScoreboard>()

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

        if (!type.containsKey(xuid)) {
            if (!helper.isExistsKey(xuid, STATUS_DATA_KEY)) helper.setValue(xuid, STATUS_DATA_KEY, STATUS_NORMAL_MODE)
            type[xuid] = helper.getValue(xuid, STATUS_DATA_KEY).toInt()
        }

        if (displayColor > 10) {
            getScoreboard(p).resetAllScores()
        }

        when (type.getValue(xuid)) {
            STATUS_NORMAL_MODE -> showNormal(p)
            STATUS_LIGHT_MODE -> showLight(p)
            STATUS_NONE_MODE -> removeStatus(p)
            STATUS_DEBUG_MODE -> showDebug(p)
        }
    }

    private fun getScoreboard(p: Player): SimpleScoreboard {
        val xuid = p.loginChainData.xuid
        var scoreboard = scoreboards[xuid]
        if (scoreboard !is SimpleScoreboard) {
            scoreboard = ScoreboardAPI.builder().build()
            scoreboards[xuid] = scoreboard
            scoreboard.addPlayer(p)
        }
        return scoreboard
    }

    private fun showNormal(p: Player) {
        val n = p.displayName
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val seichiData = seichi.getHelper().getData(xuid)
        val xp = seichiData.xp
        val level = seichi.getLevel(xp)
        val nextXp = seichi.getNextLevel(xp)
        val scoreboard = getScoreboard(p)
        scoreboard.setDisplayName(getDisplayColor())
        scoreboard.setScore(1, "", 0)
        scoreboard.setScore(2, "${TextFormat.GRAY}${LocalDateTime.now()}   ", 1)
        scoreboard.setScore(3, " ", 2)
        scoreboard.setScore(4, "${TextFormat.GOLD}name${TextFormat.RESET} : $n   ", 3)
        scoreboard.setScore(5, "  ", 4)
        scoreboard.setScore(6, "${TextFormat.RED}level${TextFormat.RESET} : $level   ", 5)
        scoreboard.setScore(7, "${TextFormat.DARK_GREEN}xp${TextFormat.RESET} : $xp/${nextXp + xp}   ", 6)
        scoreboard.setScore(8, "${TextFormat.AQUA}mana${TextFormat.RESET} : ${seichiData.mana}", 7)
        scoreboard.setScore(9, "${TextFormat.DARK_BLUE}skill${TextFormat.RESET} : ${seichiData.skill.name}", 8)
        scoreboard.setScore(10, "   ", 9)
        scoreboard.setScore(11, "${TextFormat.GREEN}world${TextFormat.RESET} : ${p.level.folderName}   ", 10)
        scoreboard.setScore(
            12,
            "${TextFormat.LIGHT_PURPLE}location${TextFormat.RESET} :  ${p.floorX},${p.floorY},${p.floorZ}   ",
            11
        )
        scoreboard.setScore(13, "    ", 12)
        scoreboard.setScore(14, "reefmcbe.ddo.jp:19132", 13)
        scoreboard.update()
    }

    private fun showLight(p: Player) {
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val seichiData = seichi.getHelper().getData(xuid)
        val xp = seichiData.xp
        val level = seichi.getLevel(xp)
        val nextXp = seichi.getNextLevel(xp)
        val scoreboard = getScoreboard(p)
        scoreboard.setDisplayName("Reef Server")
        scoreboard.setScore(1, "現在のLevel:$level ", 0)
        scoreboard.setScore(2, "レベルアップまで:$nextXp ", 1)
        scoreboard.setScore(3, "マナ:${seichiData.mana} ", 2)
        scoreboard.setScore(4, "現在のスキル:${seichiData.skill.name} ", 3)
        scoreboard.setScore(5, "座標:${p.floorX},${p.floorY},${p.floorZ} ", 4)
        scoreboard.update()
    }

    private fun removeStatus(p: Player) {
        val scoreboard = getScoreboard(p)
        scoreboard.removePlayer(p)
        scoreboard.update()
    }

    private fun showDebug(p: Player) {
        val n = p.displayName
        val xuid = p.loginChainData.xuid
        val scoreboard = getScoreboard(p)
        scoreboard.setDisplayName(getDisplayColor())
        scoreboard.setScore(1, LocalDateTime.now().toString(), 0)
        scoreboard.setScore(2, n, 0)
        scoreboard.setScore(3, xuid, 0)
        scoreboard.update()
    }

    private fun getDisplayColor(): String {
        when (displayColor) {
            1 -> return "${TextFormat.YELLOW}R${TextFormat.WHITE}eef Server"
            2 -> return "R${TextFormat.YELLOW}e${TextFormat.WHITE}ef Server"
            3 -> return "Re${TextFormat.YELLOW}e${TextFormat.WHITE}f Server"
            4 -> return "Ree${TextFormat.YELLOW}f${TextFormat.WHITE} Server"
            5 -> return "Reef ${TextFormat.YELLOW}S${TextFormat.WHITE}erver"
            6 -> return "Reef S${TextFormat.YELLOW}e${TextFormat.WHITE}rver"
            7 -> return "Reef Se${TextFormat.YELLOW}r${TextFormat.WHITE}ver"
            8 -> return "Reef Ser${TextFormat.YELLOW}v${TextFormat.WHITE}er"
            9 -> return "Reef Serv${TextFormat.YELLOW}e${TextFormat.WHITE}r"
            10 -> return "Reef Serve${TextFormat.YELLOW}r${TextFormat.WHITE}"
            11, 13, 14, 15 -> return "${TextFormat.YELLOW}Reef Server${TextFormat.WHITE}"
        }
        if (displayColor > 18) displayColor = 1
        return "Reef Server"
    }
}