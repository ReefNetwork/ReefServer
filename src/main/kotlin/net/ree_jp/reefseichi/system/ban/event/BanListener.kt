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

package net.ree_jp.reefseichi.system.ban.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerPreLoginEvent
import net.ree_jp.reefseichi.system.ban.api.ReefBanAPI
import net.ree_jp.reefseichi.system.ban.ReefBan

class BanListener : Listener {

    @EventHandler
    fun preLoginForBanCheck(ev: PlayerPreLoginEvent) {
        val p = ev.player
        val data = p.loginChainData
        val xuid = data.xuid
        val ip = p.address
        val deviceId = data.deviceId

        val api = ReefBanAPI.getInstance()

        if (api.isBan(xuid, ip, deviceId)) {
            try {
                val banXuid = api.getBanXuid(xuid, ip, deviceId)
                val reason = ReefBan.getHelper().getBanReason(banXuid)
                p.kick("banned for reef server\nbanId : $banXuid\nreason : $reason", false)
            }catch (ex: Exception) {
                p.kick("ReefBanSystemError\n${ex.message}")
            }
        }
    }
}