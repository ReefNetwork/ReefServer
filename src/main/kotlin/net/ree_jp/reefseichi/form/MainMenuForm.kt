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
import cn.nukkit.utils.TextFormat
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.system.fly.ReefFly
import net.ree_jp.reefseichi.system.mywarp.form.MyWarpSelectForm
import net.ree_jp.reefseichi.system.protect.form.ProtectAdminForm
import net.ree_jp.reefseichi.system.seichi.form.SkillSelectForm
import net.ree_jp.simplebuilder.api.SimpleBuilderAPI
import net.ree_jp.storage.StackStoragePlugin

class MainMenuForm(player: Player, content: String) : Response, FormWindowSimple("ReefSeichiMenu", content) {

    init {
        val xuid = player.loginChainData.xuid
        val fly = ReefFly.getInstance().getApi()
        val economy = EconomyAPI.getInstance()
        val builder = SimpleBuilderAPI.getInstance()

        addButton(ElementButton("ワールドを移動する") { player.showFormWindow(WorldSelectForm(player, "")) })
        addButton(ElementButton("スキルを選択する") { player.showFormWindow(SkillSelectForm(player, "")) })
        addButton(ElementButton("フライを${statusString(!fly.isFly(xuid))}\n${ReefFly.FLY_PRICE}/m coin") {
            if (fly.isCanFlyWorld(player.getLevel()) && economy.myMoney(player) >= ReefFly.FLY_PRICE) {
                fly.setFly(player)
                player.sendMessage("フライを${statusString(fly.isFly(xuid))}にしました")
            } else player.sendMessage("${ReefNotice.SUCCESS}このワールドではフライは使えないかお金が足りません")
        })
        addButton(
            ElementButton(
                "ビルドモードを${statusString(!builder.isBuilder(player))}にする"
            ) {
                Server.getInstance().dispatchCommand(player, "builder")
            })
        addButton(ElementButton("ストレージを開く") { StackStoragePlugin.getInstance().getApi().sendGui(player) })
        addButton(ElementButton("マイワープ") { player.showFormWindow(MyWarpSelectForm(player, "")) })
        addButton(ElementButton("土地保護") { player.showFormWindow(ProtectAdminForm(player, "")) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }

    private fun statusString(bool: Boolean): String {
        return if (bool) "${TextFormat.GREEN}有効${TextFormat.RESET}" else "${TextFormat.GRAY}無効${TextFormat.RESET}"
    }
}