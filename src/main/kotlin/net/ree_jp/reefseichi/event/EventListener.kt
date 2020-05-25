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

package net.ree_jp.reefseichi.event

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket
import cn.nukkit.utils.TextFormat
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.sql.ReefHelper

class EventListener : Listener {

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
                helper.setUser(xuid, n, addressList, user.deviceId)
            }
            if (!user.address.contains(deviceId)) {
                val deviceIdList = user.deviceId.toMutableList()
                deviceIdList.add(deviceId)
                helper.setUser(xuid, n, deviceIdList, user.deviceId)
            }
        } else {
            helper.setUser(xuid, n, listOf(address), listOf(deviceId))
        }
    }

    @EventHandler
    fun dataPacketReceiveForShowStartMessage(ev: DataPacketReceiveEvent) {
        val p = ev.player

        if (ev.packet is SetLocalPlayerAsInitializedPacket) {
            sendMessage(p)
            p.sendTitle(
                TextFormat.GREEN.toString() + "Reef" + TextFormat.YELLOW + "Seichi" + TextFormat.GRAY + "Server",
                "Ver " + ReefSeichiPlugin.getInstance().description.version
            )
        }
    }

    private fun sendMessage(p: Player) {
        p.sendMessage(TextFormat.GREEN.toString() + "----- Rule -----")
        p.sendMessage("")
        p.sendMessage(TextFormat.GREEN.toString() + "---- Welcome -----")
        p.sendMessage("ルールや操作方法はいつでも確認できます")
        p.sendMessage("不具合報告などはdiscordへ")
        p.sendMessage("Discord : https://discord.gg/M4A6cak")
        p.sendMessage(TextFormat.GREEN.toString() + "------------------")
    }
}