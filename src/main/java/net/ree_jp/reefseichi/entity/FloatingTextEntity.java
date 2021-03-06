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

package net.ree_jp.reefseichi.entity;

import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class FloatingTextEntity extends EntityReef {

    public FloatingTextEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        setScale(0f);
        setNameTagVisible();
        setNameTagAlwaysVisible();
    }

    @Override
    public int getNetworkId() {
        return 51;
    }
}
