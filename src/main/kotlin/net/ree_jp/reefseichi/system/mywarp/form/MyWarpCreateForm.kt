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
import cn.nukkit.form.element.ElementInput
import cn.nukkit.form.element.ElementLabel
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseCustom
import cn.nukkit.form.window.FormWindowCustom
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.mywarp.ReefMyWarp

class MyWarpCreateForm(content: String) : Response, FormWindowCustom("マイワープ作成") {

    init {
        addElement(ElementLabel("$content マイワープ作成には1回${ReefMyWarp.CREATE_PRICE}"))
        addElement(ElementInput("作成するポイントの名前を入力してください", "MyWarpPoint"))
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseCustom) throw Exception("不正なformの返り値")

        val xuid = player.loginChainData.xuid
        val warp = ReefMyWarp.getInstance()
        val api = warp.getAPI()
        val helper = warp.getHelper()
        val id = response.getInputResponse(1)
        val economy = EconomyAPI.getInstance()
        val price = ReefMyWarp.CREATE_PRICE

        if (helper.isExistsKey(xuid, id)) throw Exception("すでにその名前のポイントは存在しています")
        if (economy.myMoney(player) < price) throw Exception("お金が足りません")

        economy.reduceMoney(player, price.toDouble())
        api.setPoint(xuid, id, player)
        player.sendMessage("${ReefNotice.SUCCESS}$id を作成しました")
    }
}