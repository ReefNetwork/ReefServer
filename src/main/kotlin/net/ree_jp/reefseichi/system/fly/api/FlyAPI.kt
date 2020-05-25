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

package net.ree_jp.reefseichi.system.fly.api

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.level.Level
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.system.fly.ReefFly

class FlyAPI {

    private val list = mutableListOf<String>()

    fun isFly(xuid: String): Boolean {
        return list.contains(xuid)
    }

    fun setFly(player: Player) {
        setFly(player, !isFly(player.loginChainData.xuid))
    }

    fun setFly(player: Player, bool: Boolean) {
        val xuid = player.loginChainData.xuid
        if (bool) {
            if (isFly(xuid)) return
            list.add(xuid)
            player.allowFlight = true
        } else {
            list.remove(xuid)
            player.allowFlight = false
        }
    }

    fun isCanFlyWorld(level: Level): Boolean {
        val name = level.folderName

        if (name == "dig_1" || name == "dig_2" || name == "dig_3") return true
        return false
    }

    fun cutAll() {
        val economy = EconomyAPI.getInstance()
        val players = mutableMapOf<String, Player>()
        val price = ReefFly.FLY_PRICE
        for (p in Server.getInstance().onlinePlayers.values) players[p.loginChainData.xuid] = p
        for (xuid in list.iterator()) {
            if (players.containsKey(xuid)) {
                val p = players[xuid] ?: return

                if (economy.myMoney(p) < price) {
                    setFly(p, false)
                    p.sendMessage("${ReefNotice.SUCCESS}お金が足りないためフライを終了しました")
                } else {
                    economy.reduceMoney(p, price.toDouble())
                    p.sendMessage("${ReefNotice.SUCCESS}フライは継続中です")
                }
            } else list.remove(xuid)
        }
    }
}