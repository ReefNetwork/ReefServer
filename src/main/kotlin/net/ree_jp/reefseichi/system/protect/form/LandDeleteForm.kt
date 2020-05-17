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

package net.ree_jp.reefseichi.system.protect.form

import cn.nukkit.Player
import cn.nukkit.form.element.ElementButton
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseSimple
import cn.nukkit.form.window.FormWindowSimple
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.protect.ReefProtect

class LandDeleteForm(player: Player, content: String) : Response, FormWindowSimple("土地削除", content) {

    init {
        val xuid = player.loginChainData.xuid
        val protect = ReefProtect.getInstance()
        val helper = protect.getHelper()

        for (land in helper.getAll(xuid)) {
            addButton(ElementButton(land.id) {
                player.showFormWindow(LandDeleteCheckForm(player, land, ""))
            })
        }
        addButton(ElementButton("土地選択に戻る") { player.showFormWindow(ProtectAdminForm(player, "")) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}