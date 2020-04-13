package net.ree_jp.reefseichi.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import net.ree_jp.reefseichi.sqlite.ReefHelper

class EventListener: Listener {

    @EventHandler
    fun joinForSaveUserData(ev: PlayerJoinEvent) {
        val p = ev.player
        val data = p.loginChainData

        val n = p.name
        val xuid = data.xuid
        val address = p.address
        val deviceId = data.deviceId

        val helper = ReefHelper.getInstance()

        if (helper.isExistsUser(xuid)) {
            val user = helper.getUser(xuid)
            if (user.name != n) helper.setUser(xuid, n, user.address, user.deviceId)
            if (!user.address.contains(address)) {
                val addressList = user.address.toMutableList()
                addressList.add(address)
                helper.setUser(xuid, n, addressList.toList() ,user.deviceId)
            }
            if (!user.address.contains(deviceId)) {
                val deviceIdList = user.deviceId.toMutableList()
                deviceIdList.add(address)
                helper.setUser(xuid, n, deviceIdList.toList() ,user.deviceId)
            }
        }else{
            helper.setUser(xuid, n, listOf(address), listOf(deviceId))
        }
    }

    @EventHandler
    fun joinForShowMessage(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name
    }
}