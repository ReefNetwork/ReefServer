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

package net.ree_jp.reefseichi.system.fly.event

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.entity.EntityLevelChangeEvent
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.system.fly.ReefFly

class FlyListener : Listener {

    @EventHandler
    fun levelChangeForStopFly(ev: EntityLevelChangeEvent) {
        val p = ev.entity
        if (p !is Player) return
        val xuid = p.loginChainData.xuid
        val api = ReefFly.getInstance().getApi()

        if (api.isFly(xuid) && !api.isCanFlyWorld(ev.target)) {
            api.setFly(p, false)
            p.sendMessage("${ReefNotice.SUCCESS}フライが解除されました")
        }
    }
}