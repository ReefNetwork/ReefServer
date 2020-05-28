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

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.block.Block
import cn.nukkit.event.Cancellable
import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.block.BlockBreakEvent
import cn.nukkit.event.block.BlockPlaceEvent
import cn.nukkit.event.player.PlayerInteractEvent
import cn.nukkit.level.Location
import cn.nukkit.level.ParticleEffect
import cn.nukkit.network.protocol.LevelSoundEventPacket
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.sql.ReefHelper
import net.ree_jp.reefseichi.system.protect.ReefProtect

class ProtectListener : Listener {

    private val protect = mutableListOf<String>()

    @EventHandler(priority = EventPriority.LOW)
    fun breakForProtectLand(ev: BlockBreakEvent) {
        val p = ev.player
        val message = "${ReefNotice.SUCCESS}この場所ではブロックを掘ることは出来ません"

        try {
            isEditLand(p, ev.block, message, ev)
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
            ev.setCancelled()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun placeForProtectLand(ev: BlockPlaceEvent) {
        val p = ev.player
        val message = "${ReefNotice.SUCCESS}この場所ではブロックを置くことは出来ません"

        try {
            isEditLand(p, ev.block, message, ev)
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
            ev.setCancelled()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun tapForProtectLand(ev: PlayerInteractEvent) {
        val p = ev.player
        val message = "${ReefNotice.SUCCESS}この場所でその動作は出来ません"

        try {
            isEditLand(p, ev.block, message, ev)
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
            ev.setCancelled()
        }
    }

    private fun isEditLand(p: Player, block: Block, message: String, ev: Cancellable) {
        val xuid = p.loginChainData.xuid
        val pos = block.location
        val level = p.level
        val api = ReefProtect.getInstance().getApi()
        val isLand = api.existsProtect(pos)

        if (ev is PlayerInteractEvent && level.folderName == "lobby") {
            return
        }

        when (level.folderName) {
            "dig_1", "dig_2" -> return
            "dig_3" -> if (isLand && !api.isProtect(xuid, pos)) return else if (isLand) {
                val land = api.getLand(pos)
                showBlockMessage(
                    p, pos, "${ReefNotice.SUCCESS}この場所は${ReefHelper.getInstance()
                        .getUser(land.getOwner()).name}さんの${land.id} です", ev
                )
            }
        }
        showBlockMessage(p, pos, message, ev)
    }

    private fun showBlockMessage(p: Player, pos: Location, message: String, ev: Cancellable) {
        val xuid = p.loginChainData.xuid
        val level = p.level

        if (p.isOp && p.isCreative) {
            p.sendActionBar(message)
            return
        }
        if (!protect.contains(xuid)) {
            p.sendMessage(message)
            level.addLevelSoundEvent(p, LevelSoundEventPacket.SOUND_BLOCK_END_PORTAL_SPAWN)
            for (i in 1..15) {
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
}