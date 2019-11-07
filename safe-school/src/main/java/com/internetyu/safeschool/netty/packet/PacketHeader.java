package com.internetyu.safeschool.netty.packet;

import com.internetyu.safeschool.util.DataUtils;
import lombok.Data;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000615:07
 */
@Data
public class PacketHeader {

    private Integer length;//报文总长度（仅含报文头与报文体）

    private Integer cmd;//命令id

    private Integer serialNumber;//流水号

    private Integer version;

    private Integer safetyMark;

    private byte[] equipmentID;

    public byte[] getHeaderBytes() {
        return DataUtils.concatAll(DataUtils.integerTo2Bytes(length), DataUtils.integerTo2Bytes(cmd), DataUtils.integerTo4Bytes(serialNumber), DataUtils.integerTo2Bytes(version), DataUtils.integerTo2Bytes(safetyMark), equipmentID);
    }
}
