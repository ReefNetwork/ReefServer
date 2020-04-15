package net.ree_jp.reefseichi.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerPreLoginEvent
import net.ree_jp.reefseichi.api.ban.ReefBanAPI
import net.ree_jp.reefseichi.system.ban.sqlite.BanHelper

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
                val reason = BanHelper.getInstance().getBanReason(banXuid)
                p.kick("banned for reef server\nbanId : $banXuid\nreason : $reason", false)
            }catch (ex: Exception) {
                p.kick("ReefBanSystemError\n${ex.message}")
            }
        }
    }
}