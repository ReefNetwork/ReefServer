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

import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

public abstract class EntityReef extends Entity {

    public boolean isTempEntity = true;

    public EntityReef(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public void setVector3(Vector3 pos) {
        x = pos.x;
        y = pos.y;
        z = pos.z;
    }
}
