package com.internetyu.safeschool.service;

import com.internetyu.safeschool.netty.packet.SafeSchoolPacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/7 000711:13
 */
public abstract class BaseService {

    void send(ChannelHandlerContext channelHandlerContext, SafeSchoolPacket safeSchoolPacket) {
        channelHandlerContext.write(safeSchoolPacket);
    }

    abstract Object soService(ChannelHandlerContext channelHandlerContext, SafeSchoolPacket safeSchoolPacket);

}
