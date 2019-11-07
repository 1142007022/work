package com.internetyu.safeschool.netty.coder;

import com.internetyu.safeschool.netty.packet.SafeSchoolPacket;
import com.internetyu.safeschool.util.DataUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000614:30
 */
@Slf4j
public class SafeSchoolDecoder extends MessageToByteEncoder<SafeSchoolPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SafeSchoolPacket msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getBytesData());
        log.info("发送数据: {}" , DataUtils.bytes2HexString(msg.getBytesData()));
    }
}
