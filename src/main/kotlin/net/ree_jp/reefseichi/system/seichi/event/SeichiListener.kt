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

import cn.nukkit.Server
import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.block.BlockBreakEvent
import cn.nukkit.network.protocol.LevelSoundEventPacket
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.storage.StackStoragePlugin

class SeichiListener : Listener {

    @EventHandler
    fun breakForActiveSkill(ev: BlockBreakEvent) {
        val p = ev.player
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val api = seichi.getAPI()
        val seichiData = seichi.getHelper().getData(xuid)

        try {
            api.addXp(xuid, 1)

            val skill = seichiData.skill
            val level = p.level
            if (!skill.isCool && seichiData.mana >= skill.mana) {
                skill.isCool = true
                for (vec3 in skill.skillRange(ev.block.location, p)) {
                    level.useBreakOn(vec3, ev.item)
                }
                Server.getInstance().scheduler.scheduleDelayedTask(
                    ReefSeichiPlugin.getInstance(),
                    {
                        skill.isCool = false
                        p.sendActionBar("${ReefNotice.SUCCESS}クールタイムが終了しました")
                        level.addLevelSoundEvent(p, LevelSoundEventPacket.SOUND_NOTE)
                    }, skill.coolTime
                )
            }
        } catch (ex: Exception) {
            ev.setCancelled()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun breakForPutStorage(ev: BlockBreakEvent) {
        val p = ev.player

        try {
            if (!ev.isCancelled) {
                for (drop in ev.drops) {
                    StackStoragePlugin.getInstance().getApi().addItem(p, drop)
                }
                ev.drops = arrayOf()
            }
        } catch (ex: Exception) {
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }
}