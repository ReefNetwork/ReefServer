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

package net.ree_jp.reefseichi.form

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.form.element.ElementButton
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseSimple
import cn.nukkit.form.window.FormWindowSimple

class WorldSelectForm(player: Player, content: String) : Response, FormWindowSimple("ワールド選択", content) {

    init {
        val server = Server.getInstance()

        addButton(ElementButton("ロビー") { player.teleport(server.getLevelByName("lobby").safeSpawn) })
        addButton(ElementButton("整地ワールド1") { player.teleport(server.getLevelByName("dig_1").safeSpawn) })
        addButton(ElementButton("整地ワールド2") { player.teleport(server.getLevelByName("dig_2").safeSpawn) })
        addButton(ElementButton("整地ワールド3") { player.teleport(server.getLevelByName("dig_3").safeSpawn) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}