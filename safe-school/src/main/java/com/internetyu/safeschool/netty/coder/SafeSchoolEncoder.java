package com.internetyu.safeschool.netty.coder;

import com.internetyu.safeschool.netty.packet.PacketHeader;
import com.internetyu.safeschool.netty.packet.SafeSchoolPacket;
import com.internetyu.safeschool.util.DataUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000614:44
 */
@Slf4j
public class SafeSchoolEncoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bs = new byte[in.readableBytes()];
        in.readBytes(bs);
        log.info("收到数据: {}" , DataUtils.bytes2HexString(bs, " "));
        out.add(buildPacket(bs));
    }

    /**
     * 将byte数组转成packet
     *
     * @param data 收到的byte数组
     * @return
     */
    private SafeSchoolPacket buildPacket(byte[] data) {
        //todo
        /*SafeSchoolPacket safeSchoolPacket = new SafeSchoolPacket();
        safeSchoolPacket.setFlag(new byte[]{0X55, (byte) 0XAA});
        PacketHeader packetHeader = new PacketHeader();
        packetHeader.setCmd(0X8000);
        packetHeader.setLength(0X20);
        packetHeader.setEquipmentID(new byte[]{0X11, 0X12});
        packetHeader.setSafetyMark(0X23);
        packetHeader.setVersion(0X45);
        packetHeader.setSerialNumber(0X123456);
        safeSchoolPacket.setHeader(packetHeader);
        safeSchoolPacket.setBody(new byte[]{0X01, 0X02, 0X03});
        return safeSchoolPacket;*/
        return null;
    }

}
