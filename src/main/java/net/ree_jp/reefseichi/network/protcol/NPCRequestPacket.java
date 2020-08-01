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

package net.ree_jp.reefseichi.network.protcol;

import cn.nukkit.network.protocol.ProtocolInfo;

public class NPCRequestPacket extends cn.nukkit.network.protocol.NPCRequestPacket {
    public static final byte NETWORK_ID = ProtocolInfo.NPC_REQUEST_PACKET;

    public static final int REQUEST_SET_ACTIONS = 0;
    public static final int REQUEST_EXECUTE_ACTION = 1;
    public static final int REQUEST_EXECUTE_CLOSING_COMMANDS = 2;
    public static final int REQUEST_SET_NAME = 3;
    public static final int REQUEST_SET_SKIN = 4;
    public static final int REQUEST_SET_INTERACTION_TEXT = 5;

    public int entityRuntimeId;
    public int requestType;
    public String commandString;
    public int actionType;

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(entityRuntimeId);
        this.putUnsignedVarInt(requestType);
        this.putString(commandString);
        this.putUnsignedVarInt(actionType);
    }
}
