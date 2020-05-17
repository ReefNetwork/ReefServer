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
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseModal
import cn.nukkit.form.window.FormWindowModal
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.protect.ReefProtect
import net.ree_jp.reefseichi.system.protect.data.LandData

class LandDeleteCheckForm(player: Player, land: LandData, content: String) : Response, FormWindowModal(
    "削除確認",
    "",
    "",
    ""
) {

    init {
        val helper = ReefProtect.getInstance().getHelper()
        setButton1("削除する") {
            helper.removeLand(land.getOwner(), land.id)
            player.sendMessage("${ReefNotice.SUCCESS}削除しました")
        }
        setButton2("戻る") { player.showFormWindow(LandDeleteForm(player, "")) }
        val message = "$content ${land.id}を本当に削除しますか?\n元に戻すことはできません"
        setContent(message)
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseModal) throw Exception("不正なformの返り値")

        runTask(response.clickedButtonId == 0)
    }
}