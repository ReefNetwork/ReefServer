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

package net.ree_jp.reefseichi.event

import cn.nukkit.Server
import cn.nukkit.block.Block
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerFormRespondedEvent
import cn.nukkit.event.player.PlayerInteractEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.item.Item
import cn.nukkit.utils.TextFormat
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.form.MainMenuForm
import net.ree_jp.reefseichi.form.Response

class FormListener : Listener {

    private val formCool = mutableListOf<String>()

    @EventHandler
    fun joinForGiveMenuItem(ev: PlayerJoinEvent) {
        val p = ev.player
        val inv = p.inventory
        val menu = Item.get(Item.CLOCK).setCustomName("${TextFormat.GRAY}Menu")

        if (!inv.contains(menu)) {
            inv.addItem(menu)
            p.sendMessage("${ReefNotice.SUCCESS}Menuは時計を地面にクリックするかシーランタンをクリックするまたは/menuと打つと開きます")
            p.sendMessage("${ReefNotice.SUCCESS}もし時計を無くしてしまったときはもう一度ログインすると貰えます")
        }
    }

    @EventHandler
    fun touchForOpenMenu(ev: PlayerInteractEvent) {
        val p = ev.player
        val xuid = p.loginChainData.xuid

        try {
            if (((ev.item.id == Item.CLOCK) || (ev.block.id == Block.SEA_LANTERN)) && !formCool.contains(xuid)) {
                formCool.add(xuid)
                Server.getInstance().scheduler.scheduleDelayedTask(
                    ReefSeichiPlugin.getInstance(),
                    { formCool.remove(xuid) },
                    10, false
                )
                p.showFormWindow(MainMenuForm(p, ""))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }

    @EventHandler
    fun formResponse(ev: PlayerFormRespondedEvent) {
        val p = ev.player
        val window = ev.window
        val res = ev.response

        try {
            if (res != null && window is Response) {
                window.handleResponse(p, res)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            p.sendMessage("${ReefNotice.ERROR}${ex.message}")
        }
    }
}