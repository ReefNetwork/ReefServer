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

package net.ree_jp.reefseichi.system.mywarp.form

import cn.nukkit.Player
import cn.nukkit.form.element.ElementButton
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseSimple
import cn.nukkit.form.window.FormWindowSimple
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.mywarp.ReefMyWarp

class MyWarpSelectForm(player: Player, content: String) : Response, FormWindowSimple("マイワープ", content) {

    init {
        val xuid = player.loginChainData.xuid
        val warp = ReefMyWarp.getInstance()
        for (id in warp.getHelper().getAllKey(xuid)) {
            val pos = warp.getAPI().getPoint(xuid, id)
            addButton(ElementButton(id) {
                player.teleport(pos)
                player.sendMessage("${ReefNotice.SUCCESS}$id にテレポートしました")
            })
        }
        addButton(ElementButton("マイワープを設定する") {
            player.showFormWindow(
                MyWarpCreateForm(
                    ""
                )
            )
        })
        addButton(ElementButton("マイワープを削除する") {
            player.showFormWindow(
                MyWarpDeleteForm(
                    player, ""
                )
            )
        })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}