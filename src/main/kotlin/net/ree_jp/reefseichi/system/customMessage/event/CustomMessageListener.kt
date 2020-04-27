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

package net.ree_jp.reefseichi.system.customMessage.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import net.ree_jp.reefseichi.system.customMessage.ReefCustomMessage

class CustomMessageListener : Listener {

    private var quitTime = mutableMapOf<String, Long>()

    @EventHandler
    fun joinForChangeNameTag(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name
        val xuid = p.loginChainData.xuid
        val helper = ReefCustomMessage.getHelper()
        val key = ReefCustomMessage.DISPLAY_NAME_TAG_KEY

        p.displayName = "[${helper.getMessage(xuid, key)}] $n"
    }

    @EventHandler
    fun joinForJoinCustomMessage(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name
        val xuid = p.loginChainData.xuid
        val helper = ReefCustomMessage.getHelper()
        val joinKey = ReefCustomMessage.JOIN_KEY
        val rejoinKey = ReefCustomMessage.REJOIN_KEY

        if (isRejoin(xuid)) {
            if (helper.isExistsKey(xuid, rejoinKey)) ev.setJoinMessage(helper.getMessage(xuid, rejoinKey)) else ev.setJoinMessage("$n さんが再参加しました")
        } else {
            if (helper.isExistsKey(xuid, joinKey)) {
                ev.setJoinMessage(helper.getMessage(xuid, joinKey))
            } else if (p.firstPlayed == null) ev.setJoinMessage("$n さんが初参加しました") else ev.setJoinMessage("$n さんが参加しました")
        }
    }

    @EventHandler
    fun quitForQuitCustomMessage(ev: PlayerQuitEvent) {
        val p = ev.player
        val n = p.name
        val xuid = p.loginChainData.xuid
        val reason = ev.reason
        val helper = ReefCustomMessage.getHelper()
        val key = ReefCustomMessage.QUIT_KEY

        if (helper.isExistsKey(xuid, key)) {
            ev.setQuitMessage(helper.getMessage(xuid, key))
        } else ev.setQuitMessage("$n さんが $reason でログアウトしました")
    }

    @EventHandler
    fun quitForTimeRecord(ev: PlayerQuitEvent) {
        val p = ev.player
        val xuid = p.loginChainData.xuid

        quitTime[xuid] = System.currentTimeMillis()
    }

    private fun isRejoin(xuid: String): Boolean {
        val time = quitTime[xuid] ?: return false

        return System.currentTimeMillis() <= (time + 600000)
    }
}