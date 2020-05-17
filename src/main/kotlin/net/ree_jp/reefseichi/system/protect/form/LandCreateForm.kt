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
import cn.nukkit.level.Location
import cn.nukkit.level.Position
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.protect.ReefProtect

class LandCreateForm(player: Player, content: String) : Response, FormWindowSimple("土地作成", "") {

    init {
        val xuid = player.loginChainData.xuid
        val api = ReefProtect.getInstance().getApi()
        val pos1 = api.start[xuid] ?: Location()
        val pos2 = api.end[xuid] ?: Location()

        setContent("$content ポイント1 ${posToStoring(pos1)}\nポイント2 ${posToStoring(pos2)}")
        addButton(ElementButton("ポイント1を設定する") {
            api.start[xuid] = player.position
            player.sendMessage("${ReefNotice.SUCCESS}ポイント1を${posToStoring(player)} に設定しました")
        })
        addButton(ElementButton("ポイント2を設定する") {
            api.end[xuid] = player.position
            player.sendMessage("${ReefNotice.SUCCESS}ポイント2を${posToStoring(player)} に設定しました")
        })
        if (api.isGetPoint(xuid)) {
            addButton(ElementButton("土地を作成する") {
                player.showFormWindow(LandCreateCheckForm(player, ""))
            })
        }
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }

    private fun posToStoring(pos: Position): String {
        return "${pos.level.folderName}:${pos.floorX}.${pos.floorY}.${pos.floorZ}"
    }
}