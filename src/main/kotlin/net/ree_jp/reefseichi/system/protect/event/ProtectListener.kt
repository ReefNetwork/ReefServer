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

package net.ree_jp.reefseichi.system.protect.event

import cn.nukkit.Server
import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.block.BlockBreakEvent
import cn.nukkit.level.ParticleEffect
import cn.nukkit.network.protocol.LevelSoundEventPacket
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.api.ReefAPI
import net.ree_jp.reefseichi.system.protect.ReefProtect

class ProtectListener : Listener {

    private val protect = mutableListOf<String>()

    @EventHandler(priority = EventPriority.LOW)
    fun breakForProtectLand(ev: BlockBreakEvent) {
        val p = ev.player
        val xuid = p.loginChainData.xuid
        val pos = ev.block.location
        val api = ReefProtect.getInstance().getApi()

        try {
            if (api.isProtect(xuid, pos)) {
                val land = api.getLand(pos)
                val ownerName = ReefAPI.getInstance().getName(land.getOwner())
                if (p.isOp && p.gamemode == 1) {
                    p.sendActionBar("この場所は$ownerName さんの${land.id} です")
                    return
                }
                if (!protect.contains(xuid)) {
                    val level = p.level
                    p.sendMessage("${ReefNotice.SUCCESS} この場所は$ownerName さんの${land.id} です")
                    level.addLevelSoundEvent(p, LevelSoundEventPacket.SOUND_BLOCK_END_PORTAL_SPAWN)
                    for (i in 1..10) {
                        level.addParticleEffect(
                            pos.add(0.5, 1.5, 0.5),
                            ParticleEffect.END_CHEST,
                            -1,
                            level.dimension,
                            listOf(p)
                        )
                    }
                }
                protect.add(xuid)
                Server.getInstance().scheduler.scheduleDelayedTask(
                    ReefSeichiPlugin.getInstance(),
                    { protect.remove(xuid) },
                    20
                )
                ev.setCancelled()
            }
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
            ev.setCancelled()
        }
    }
}