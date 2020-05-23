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
import cn.nukkit.form.element.ElementInput
import cn.nukkit.form.element.ElementLabel
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseCustom
import cn.nukkit.form.window.FormWindowCustom
import cn.nukkit.level.Position
import cn.nukkit.math.Vector3
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.protect.ReefProtect
import net.ree_jp.reefseichi.system.protect.data.LandData

class LandCreateCheckForm(player: Player, content: String) : Response, FormWindowCustom("土地訪問") {

    private lateinit var land: LandData

    init {
        val xuid = player.loginChainData.xuid
        val api = ReefProtect.getInstance().getApi()
        val start = api.start[xuid] ?: throw Exception("ポイントが登録されていません")
        val end = api.end[xuid] ?: throw Exception("ポイントが登録されていません")

        val min = Position(
            if (start.floorX <= end.floorX) start.floorX.toDouble() else end.floorX.toDouble(),
            0.0,
            if (start.floorZ <= end.floorZ) start.floorZ.toDouble() else end.floorZ.toDouble()
        )
        val max = Vector3(
            if (start.floorX > end.floorX) start.floorX.toDouble() else end.floorX.toDouble(),
            0.0,
            if (start.floorZ > end.floorZ) start.floorZ.toDouble() else end.floorZ.toDouble()
        )

        val land = LandData(min, max, xuid, "", player.level, listOf(), min, true)
        val count = (land.maxX - land.minX).toInt() * (land.maxZ - land.minZ).toInt()

        addElement(ElementLabel("$content 本当に土地を購入しますか?\n合計$count ブロックです\n${api.getPrice(land)}円です\n購入する場合は土地の名前を決めて下の欄に入力してください"))
        addElement(ElementInput("土地の名前を入力してください(変更することはできません)"))
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseCustom) throw Exception("不正なformの返り値")

        val protect = ReefProtect.getInstance()
        val api = protect.getApi()
        val helper = protect.getHelper()
        val xuid = player.loginChainData.xuid
        val economy = EconomyAPI.getInstance()
        val id = response.getInputResponse(1)
        val price = api.getPrice(land)

        if (economy.myMoney(player) < price) {
            player.sendMessage("${ReefNotice.SUCCESS}お金が足りません")
        } else if (helper.isExists(xuid, id)) {
            player.sendMessage("${ReefNotice.SUCCESS}すでにその名前の土地は存在しています")
        } else {
            try {
                api.addProtect(land.clone(id))
                economy.reduceMoney(player, price.toDouble())
                player.sendMessage("${ReefNotice.SUCCESS}土地を購入しました")
            } catch (ex: Exception) {
                player.sendMessage("${ReefNotice.ERROR}土地を購入出来ませんでした")
                throw ex
            }
        }
    }
}