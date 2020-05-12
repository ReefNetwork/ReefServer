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

class MyWarpDeleteForm(player: Player, content: String) : Response, FormWindowSimple("マイワープ削除", content) {

    init {
        val xuid = player.loginChainData.xuid
        val helper = ReefMyWarp.getInstance().getHelper()
        for (id in helper.getAllKey(xuid)) {
            addButton(ElementButton(id) {
                helper.deleteValue(xuid, id)
                player.sendMessage("${ReefNotice.SUCCESS}$id を削除しました")
            })
        }
        addButton(ElementButton("戻る") { player.showFormWindow(MyWarpSelectForm(player, "")) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}