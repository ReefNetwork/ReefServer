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

package net.ree_jp.reefseichi.system.gatya.event

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.block.BlockBreakEvent
import cn.nukkit.event.inventory.CraftItemEvent
import cn.nukkit.event.player.PlayerInteractEvent
import cn.nukkit.inventory.PlayerInventory
import cn.nukkit.item.Item
import cn.nukkit.utils.TextFormat
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.system.gatya.ReefGatya
import net.ree_jp.reefseichi.system.seichi.ReefSeichi

class GatyaListener : Listener {

    @EventHandler
    fun breakForGiveGatya(ev: BlockBreakEvent) {
        if (ev.isCancelled) return
        val p = ev.player
        val api = ReefGatya.getInstance().getApi()
        val drops = ev.drops
        val helper = ReefSeichi.getInstance().getHelper()
        val user = helper.getData(p.loginChainData.xuid)

        try {
            if ((user.xp % 1000) == 0) {
                drops[drops.size] = api.getGatya()
            } else {
                if (!user.skill.isCool && ((0..1000).random() == 0)) {
                    drops[drops.size] = api.getSpecial()
                }
            }
            ev.drops = drops
        } catch (ex: Exception) {
            ev.setCancelled()
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler
    fun tapForPlayGatya(ev: PlayerInteractEvent) {
        val p = ev.player
        val item = ev.item
        val api = ReefGatya.getInstance().getApi()

        if (!api.isGatya(item)) return

        if (reduceItem(p.inventory, api.getGatya())) {
            val value = (0..10000).random()
            val result = api.runGatya(value)
            if (result.id == Item.AIR) {
                p.sendActionBar("${TextFormat.GRAY}はずれ")
            } else {
                p.sendActionBar("${TextFormat.GREEN}(${value / 10000.0}％)")
                if (p.inventory.canAddItem(result)) {
                    p.inventory.addItem(result)
                } else {
                    p.dropItem(result)
                }
            }
        }
    }

    @EventHandler
    fun craftForBlockSpecialItem(ev: CraftItemEvent) {
        val p = ev.player
        val api = ReefGatya.getInstance().getApi()

        for (item in ev.input) {
            if (api.isGatya(item) || api.isSpecial(item)) {
                ev.setCancelled()
                p.sendMessage("特殊アイテムはクラフトに使用できません")
            }
        }
    }

    private fun reduceItem(inv: PlayerInventory, item: Item): Boolean {
        val hand = inv.itemInHand
        if (!hand.equals(item, true)) {
            hand.setCount(hand.count)
            inv.itemInHand = hand
            return true
        }
        return false
    }
}