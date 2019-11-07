package com.internetyu.safeschool.netty.packet;

import com.internetyu.safeschool.util.DataUtils;
import lombok.Data;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000614:53
 */
@Data
public class SafeSchoolPacket {

    private byte[] flag;//0x55,0xAA

    private PacketHeader header;

    private byte[] body;

    public byte[] getBytesData() {
        return DataUtils.concatAll(flag, header.getHeaderBytes(), body);
    }

    public String toString() {
        return DataUtils.bytes2HexString(DataUtils.concatAll(flag, header.getHeaderBytes(), body));
    }

}
