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
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.ban.api.ReefBanAPI
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.storage.StackStoragePlugin

class SeichiListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun breakForActiveSkill(ev: BlockBreakEvent) {
        val p = ev.player
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val api = seichi.getAPI()
        val economy = EconomyAPI.getInstance()
        val seichiData = seichi.getHelper().getData(xuid)

        try {
            if (ev.isCancelled || !p.isSurvival) return
            api.addXp(xuid, 1)

            val level = p.level
            var skill = seichiData.skill
            if (p.isSneaking) {
                seichiData.skills.forEach {
                    if (it.name == seichi.getDefaultSkill().name) skill = it
                }
            }
            if (seichiData.mana < skill.mana) {
                p.sendActionBar("${ReefNotice.SUCCESS}クールタイム中です")
                ev.setCancelled()
            }
            if (!skill.isCool) {
                economy.addMoney(p, 0.1)
                seichiData.mana = skill.mana - seichiData.mana
                skill.isCool = true
                for (vec3 in skill.skillRange(ev.block.location, p)) {
                    if (vec3 != ev.block.location) {
                        level.useBreakOn(vec3, ev.item)
                    }
                }
                Server.getInstance().scheduler.scheduleDelayedTask(
                    ReefSeichiPlugin.getInstance(),
                    {
                        skill.isCool = false
                        p.sendActionBar("${ReefNotice.SUCCESS}クールタイムが終了しました")
                        level.addLevelSoundEvent(p, LevelSoundEventPacket.SOUND_NOTE)
                    }, skill.coolTime
                )
            } else {
                if (skill.name == seichi.getDefaultSkill().name) {
                    val ex = Exception().stackTrace[0]
                    val location = ex.className + ex.lineNumber
                    ReefBanAPI.getInstance().setBan(xuid, location)
                }
                economy.addMoney(p, 0.01)
            }
        } catch (ex: Exception) {
            ev.setCancelled()
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun breakForPutStorage(ev: BlockBreakEvent) {
        val p = ev.player
        val bl = ev.block
        val inv = p.inventory
        val api = StackStoragePlugin.getInstance().getApi()

        try {
            if (ev.isCancelled) return
            for (drop in ev.drops) {
                when {
                    api.isCanStorage(drop) -> api.addItem(p, drop)

                    inv.canAddItem(drop) -> inv.addItem(drop)

                    else -> bl.level.dropItem(bl.location, drop)
                }
            }
            ev.drops = arrayOf()
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }
}