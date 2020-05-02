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

package net.ree_jp.reefseichi.system.seichi.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.utils.TextFormat

class SeichiStatusListener : Listener {

    @EventHandler
    fun joinForSendStatusMessage(ev: PlayerJoinEvent) {
        val p = ev.player
        p.sendMessage("${TextFormat.GRAY}------Status------")
        p.sendMessage("version: ${p.loginChainData.gameVersion}")
        p.sendMessage("id: ${p.loginChainData.xuid}")
        p.sendMessage("${TextFormat.GRAY}------------------")
    }
}