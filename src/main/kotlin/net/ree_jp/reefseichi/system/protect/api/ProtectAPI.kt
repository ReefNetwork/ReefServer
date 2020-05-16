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

package net.ree_jp.reefseichi.system.protect.api

import cn.nukkit.level.Position
import net.ree_jp.reefseichi.system.protect.ReefProtect
import net.ree_jp.reefseichi.system.protect.data.LandData

class ProtectAPI {

    val start = mutableMapOf<String, Position>()
    val end = mutableMapOf<String, Position>()

    fun isProtect(xuid: String, pos: Position): Boolean {
        val helper = ReefProtect.getInstance().getHelper()

        for (land in helper.getAll()) {
            if (land.isVectorInside(pos) && land.level.folderName == pos.level.folderName && !land.isMember(xuid)) {
                return true
            }
        }
        return false
    }

    fun getLand(pos: Position): LandData {
        val helper = ReefProtect.getInstance().getHelper()

        for (land in helper.getAll()) {
            if (land.isVectorInside(pos) && land.level.folderName == pos.level.folderName) return land
        }
        throw Exception("土地が見つかりません")
    }

    fun addProtect(buyLand: LandData) {
        val helper = ReefProtect.getInstance().getHelper()

        for (land in helper.getAll()) {
            if (land.intersectsWith(buyLand)) throw Exception("土地が見つかりません")
        }
        helper.setLand(buyLand.getOwner(), buyLand.id, buyLand)
    }

    fun addMember(xuid: String, id: String, member: String) {
        val helper = ReefProtect.getInstance().getHelper()

        val land = helper.getLand(xuid, id)
        val members = land.subUser
        if (members.contains(member)) throw Exception("すでに追加されています")
        val mutableMembers = members.toMutableList()
        mutableMembers.add(member)
        land.subUser = mutableMembers
        helper.setLand(xuid, id, land)
    }

    fun removeMember(xuid: String, id: String, member: String) {
        val helper = ReefProtect.getInstance().getHelper()

        val land = helper.getLand(xuid, id)
        val members = land.subUser
        if (!members.contains(member)) throw Exception("まだ追加されていません")
        val mutableMembers = members.toMutableList()
        mutableMembers.remove(member)
        land.subUser = mutableMembers
        helper.setLand(xuid, id, land)
    }

    fun getPrice(land: LandData): Int {
        val count = (land.maxX - land.minX).toInt() * (land.maxZ - land.minZ).toInt()
        return count * 10
    }

    fun isGetPoint(xuid: String): Boolean {
        return start.contains(xuid) && end.contains(xuid)
    }

    private fun isCanBuy(xuid: String, land: LandData): Boolean {
        return true
    }
}