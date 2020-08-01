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

package net.ree_jp.reefseichi.system.test.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.server.DataPacketReceiveEvent
import net.ree_jp.reefseichi.network.protcol.NPCRequestPacket

class TestListener : Listener {

    @EventHandler
    fun receivePacket(ev: DataPacketReceiveEvent) {
        val p = ev.player
        val n = p.name
        val pk = ev.packet

        if (pk is NPCRequestPacket) {
            print("$n : $pk")
        }
    }
}