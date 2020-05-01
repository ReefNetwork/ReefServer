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
import net.ree_jp.reefseichi.ReefNotice
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

        try {
            if (!helper.isExistsKey(xuid, key)) helper.setValue(xuid, key, "初心者")
            p.displayName = "[${helper.getValue(xuid, key, String::class.java)}] $n"
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler
    fun joinForJoinCustomMessage(ev: PlayerJoinEvent) {
        val p = ev.player
        val n = p.name
        val xuid = p.loginChainData.xuid
        val helper = ReefCustomMessage.getHelper()
        val joinKey = ReefCustomMessage.JOIN_KEY
        val rejoinKey = ReefCustomMessage.REJOIN_KEY

        try {
            if (isRejoin(xuid)) {
                if (helper.isExistsKey(xuid, rejoinKey)) ev.setJoinMessage(
                    helper.getValue(
                        xuid,
                        rejoinKey,
                        String::class.java
                    )
                ) else ev.setJoinMessage("$n さんが再参加しました")
            } else {
                if (helper.isExistsKey(xuid, joinKey)) {
                    ev.setJoinMessage(helper.getValue(xuid, joinKey, String::class.java))
                } else if (p.firstPlayed == null) ev.setJoinMessage("$n さんが初参加しました") else ev.setJoinMessage("$n さんが参加しました")
            }
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
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

        try {
            if (helper.isExistsKey(xuid, key)) {
                ev.setQuitMessage(helper.getValue(xuid, key, String::class.java))
            } else ev.setQuitMessage("$n さんが $reason でログアウトしました")
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
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