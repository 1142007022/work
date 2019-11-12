package com.internetyu.safeschool.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.CRC32;

public class DataUtils {
    //private static final String TAG = "DataUtil";
    //协议转义标识字符
    public static final int MSG_IDENTIFY_INT = 0x7F;
    public static final byte[] MSG_IDENTIFY = {0x7F};
    public static final int MSG_CLIENT_IDENTIFY_INT = 0x7E;
    public static final byte[] MSG_CLIENT_IDENTIFY = {0x7E};
    public static final int MSG_IDENTIFY_AFTER_INT = 0x7D;
    public static final byte[] MSG_IDENTIFY_AFTER = {0x7D};

    // crc表
    static long[] crcTable = {
            0x00000000L, 0x77073096L, 0xee0e612cL, 0x990951baL, 0x076dc419L, 0x706af48fL, 0xe963a535L, 0x9e6495a3L,
            0x0edb8832L, 0x79dcb8a4L, 0xe0d5e91eL, 0x97d2d988L, 0x09b64c2bL, 0x7eb17cbdL, 0xe7b82d07L, 0x90bf1d91L,
            0x1db71064L, 0x6ab020f2L, 0xf3b97148L, 0x84be41deL, 0x1adad47dL, 0x6ddde4ebL, 0xf4d4b551L, 0x83d385c7L,
            0x136c9856L, 0x646ba8c0L, 0xfd62f97aL, 0x8a65c9ecL, 0x14015c4fL, 0x63066cd9L, 0xfa0f3d63L, 0x8d080df5L,
            0x3b6e20c8L, 0x4c69105eL, 0xd56041e4L, 0xa2677172L, 0x3c03e4d1L, 0x4b04d447L, 0xd20d85fdL, 0xa50ab56bL,
            0x35b5a8faL, 0x42b2986cL, 0xdbbbc9d6L, 0xacbcf940L, 0x32d86ce3L, 0x45df5c75L, 0xdcd60dcfL, 0xabd13d59L,
            0x26d930acL, 0x51de003aL, 0xc8d75180L, 0xbfd06116L, 0x21b4f4b5L, 0x56b3c423L, 0xcfba9599L, 0xb8bda50fL,
            0x2802b89eL, 0x5f058808L, 0xc60cd9b2L, 0xb10be924L, 0x2f6f7c87L, 0x58684c11L, 0xc1611dabL, 0xb6662d3dL,
            0x76dc4190L, 0x01db7106L, 0x98d220bcL, 0xefd5102aL, 0x71b18589L, 0x06b6b51fL, 0x9fbfe4a5L, 0xe8b8d433L,
            0x7807c9a2L, 0x0f00f934L, 0x9609a88eL, 0xe10e9818L, 0x7f6a0dbbL, 0x086d3d2dL, 0x91646c97L, 0xe6635c01L,
            0x6b6b51f4L, 0x1c6c6162L, 0x856530d8L, 0xf262004eL, 0x6c0695edL, 0x1b01a57bL, 0x8208f4c1L, 0xf50fc457L,
            0x65b0d9c6L, 0x12b7e950L, 0x8bbeb8eaL, 0xfcb9887cL, 0x62dd1ddfL, 0x15da2d49L, 0x8cd37cf3L, 0xfbd44c65L,
            0x4db26158L, 0x3ab551ceL, 0xa3bc0074L, 0xd4bb30e2L, 0x4adfa541L, 0x3dd895d7L, 0xa4d1c46dL, 0xd3d6f4fbL,
            0x4369e96aL, 0x346ed9fcL, 0xad678846L, 0xda60b8d0L, 0x44042d73L, 0x33031de5L, 0xaa0a4c5fL, 0xdd0d7cc9L,
            0x5005713cL, 0x270241aaL, 0xbe0b1010L, 0xc90c2086L, 0x5768b525L, 0x206f85b3L, 0xb966d409L, 0xce61e49fL,
            0x5edef90eL, 0x29d9c998L, 0xb0d09822L, 0xc7d7a8b4L, 0x59b33d17L, 0x2eb40d81L, 0xb7bd5c3bL, 0xc0ba6cadL,
            0xedb88320L, 0x9abfb3b6L, 0x03b6e20cL, 0x74b1d29aL, 0xead54739L, 0x9dd277afL, 0x04db2615L, 0x73dc1683L,
            0xe3630b12L, 0x94643b84L, 0x0d6d6a3eL, 0x7a6a5aa8L, 0xe40ecf0bL, 0x9309ff9dL, 0x0a00ae27L, 0x7d079eb1L,
            0xf00f9344L, 0x8708a3d2L, 0x1e01f268L, 0x6906c2feL, 0xf762575dL, 0x806567cbL, 0x196c3671L, 0x6e6b06e7L,
            0xfed41b76L, 0x89d32be0L, 0x10da7a5aL, 0x67dd4accL, 0xf9b9df6fL, 0x8ebeeff9L, 0x17b7be43L, 0x60b08ed5L,
            0xd6d6a3e8L, 0xa1d1937eL, 0x38d8c2c4L, 0x4fdff252L, 0xd1bb67f1L, 0xa6bc5767L, 0x3fb506ddL, 0x48b2364bL,
            0xd80d2bdaL, 0xaf0a1b4cL, 0x36034af6L, 0x41047a60L, 0xdf60efc3L, 0xa867df55L, 0x316e8eefL, 0x4669be79L,
            0xcb61b38cL, 0xbc66831aL, 0x256fd2a0L, 0x5268e236L, 0xcc0c7795L, 0xbb0b4703L, 0x220216b9L, 0x5505262fL,
            0xc5ba3bbeL, 0xb2bd0b28L, 0x2bb45a92L, 0x5cb36a04L, 0xc2d7ffa7L, 0xb5d0cf31L, 0x2cd99e8bL, 0x5bdeae1dL,
            0x9b64c2b0L, 0xec63f226L, 0x756aa39cL, 0x026d930aL, 0x9c0906a9L, 0xeb0e363fL, 0x72076785L, 0x05005713L,
            0x95bf4a82L, 0xe2b87a14L, 0x7bb12baeL, 0x0cb61b38L, 0x92d28e9bL, 0xe5d5be0dL, 0x7cdcefb7L, 0x0bdbdf21L,
            0x86d3d2d4L, 0xf1d4e242L, 0x68ddb3f8L, 0x1fda836eL, 0x81be16cdL, 0xf6b9265bL, 0x6fb077e1L, 0x18b74777L,
            0x88085ae6L, 0xff0f6a70L, 0x66063bcaL, 0x11010b5cL, 0x8f659effL, 0xf862ae69L, 0x616bffd3L, 0x166ccf45L,
            0xa00ae278L, 0xd70dd2eeL, 0x4e048354L, 0x3903b3c2L, 0xa7672661L, 0xd06016f7L, 0x4969474dL, 0x3e6e77dbL,
            0xaed16a4aL, 0xd9d65adcL, 0x40df0b66L, 0x37d83bf0L, 0xa9bcae53L, 0xdebb9ec5L, 0x47b2cf7fL, 0x30b5ffe9L,
            0xbdbdf21cL, 0xcabac28aL, 0x53b39330L, 0x24b4a3a6L, 0xbad03605L, 0xcdd70693L, 0x54de5729L, 0x23d967bfL,
            0xb3667a2eL, 0xc4614ab8L, 0x5d681b02L, 0x2a6f2b94L, 0xb40bbe37L, 0xc30c8ea1L, 0x5a05df1bL, 0x2d02ef8dL
    };


    /**生成现在时间的byte[] 6字节 年月日时分秒各一个字节  基于2000年
     * @return
     */
    public static byte[] getTime() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int y = year - 2000;
        byte[] res = new byte[]{integerTo1Byte(y), integerTo1Byte(month), integerTo1Byte(day), integerTo1Byte(hour), integerTo1Byte(min), integerTo1Byte(second)};
        return res;
    }

    // 计算crc
    public static byte[] getCrc32(byte[] bytes, boolean isSmall) {
        long startVal = 0xFFFFFFFFL;
        for (int i = 0; i < bytes.length; i++) {
            startVal = crcTable[(int) ((startVal ^ bytes[i]) & 0xff)] ^ (startVal >> 8);
        }
        return longToBytes(startVal, 4, isSmall);
    }

    public static void main(String[] args) {
        /*byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] crc32 = getCrc32(bytes, true);
        System.out.println(DataUtils.bytes2HexString(crc32));*/
        /*byte[] test = new byte[]{0X10,0X11};
        System.out.println(twoBytesToInteger(test));*/
        String test = "test";
        for (int i = 0; i < test.getBytes().length; i++) {
            System.out.println(test.getBytes()[i]);
        }
        ;
    }

    /**
     * 从byte数组中截取对应的长度
     *
     * @param src   源数组
     * @param begin 起始位置
     * @param count 截取长度
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    /**
     * 将源数据的无意义位去除
     *
     * @param srcBytes     源数据（信息+ 0x00 或 0x00 + 信息）
     * @param frontAddZero true：在前端补零 false:在后端补0
     * @return
     */
    public static byte[] getRealBytes(byte[] srcBytes, boolean frontAddZero) {
        int length = srcBytes.length;
        if (frontAddZero) {
            if (srcBytes[0] != 0) {//第一位不是无意义位
                return srcBytes;
            }
            for (int i = 0; i < length; i++) {//从数组开头开始查找有意义位
                if (srcBytes[i] != 0) {//
                    byte[] realBytes = DataUtils.subBytes(srcBytes, 0, i + 1);
                    return realBytes;
                }
            }
        } else {
            if (srcBytes[length - 1] != 0) {//最后一位不是无意义位
                return srcBytes;
            }
            for (int i = length - 1; i >= 0; i--) {//从数组末尾开始查找有意义位
                if (srcBytes[i] != 0) {//
                    byte[] realBytes = DataUtils.subBytes(srcBytes, 0, i + 1);
                    return realBytes;
                }
            }
        }
        return null;
    }

    /**
     * 将源数据的无意义位去除,并转换成字符串（默认字符编码为UTF-8）
     *
     * @param srcBytes     源数据（信息+ 0x00 或 0x00 + 信息）
     * @param frontAddZero true：在前端补零 false:在后端补0
     * @return
     */
    public static String getRealStringFromBytes(byte[] srcBytes, boolean frontAddZero) {
        byte[] realBytes = getRealBytes(srcBytes, frontAddZero);
        try {
            if (realBytes != null) {
                String s = new String(realBytes, "UTF-8");
                return s;
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将源数据的无意义位去除,并转换成字符串
     *
     * @param srcBytes     源数据（信息+ 0x00）
     * @param frontAddZero true：在前端补零 false:在后端补0
     * @param charsetName  字符编码格式
     * @return
     */
    public static String getRealStringFromBytes(byte[] srcBytes, boolean frontAddZero, String charsetName) {
        byte[] realBytes = getRealBytes(srcBytes, frontAddZero);
        try {
            if (realBytes != null) {
                String s = new String(realBytes, charsetName);
                return s;
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将源数据长度补充至指定的数据长度（在源数据前面或后面补充,每个字节设置为0X00,指定长度小于数组长度时自动截取对应长度）
     *
     * @param srcBytes     源数据
     * @param length       需要补充到的数据长度
     * @param frontAddZero true：在前端补零 false:在后端补0
     * @return
     */
    public static byte[] getByteForLength(byte[] srcBytes, int length, boolean frontAddZero) {
        if (length == srcBytes.length) {
            return srcBytes;
        }
        byte[] bytes = new byte[length];
        if (length > srcBytes.length) {
            int needAddCount = length - srcBytes.length;
            for (int i = 0; i < bytes.length; i++) {
                if (frontAddZero) {//数据源前每个字节填充0X00
                    //前端补0
                    if (i < needAddCount) {
                        bytes[i] = 0;
                    } else {
                        bytes[i] = srcBytes[i - needAddCount];
                    }
                } else {//数据源后每个字节填充0X00
                    //后端补0
                    if (i >= srcBytes.length) {
                        bytes[i] = 0;
                    } else {
                        bytes[i] = srcBytes[i];
                    }
                }
            }
            return bytes;
        }
        if (length < srcBytes.length) {//截取指定长度
            for (int i = 0; i < length; i++) {
                bytes[i] = srcBytes[i];
            }
            return bytes;
        }
        return bytes;
    }

    /**
     * 将任意数字类型的字符串转换成指定长度的BCD字节码
     *
     * @param numStr
     * @param length       指定数据转化前长度(必须是2的倍数，2个数字占用一个字节)，大于字符串转化后的长度时，则需要在前端或后端补0
     * @param frontAddZero true：在前端补零 false:在后端补0
     * @return
     */
    public static byte[] getNumberStrBytesBCD(String numStr, int length, boolean frontAddZero) {
        if (length % 2 != 0) {//不是2的倍数，不返回数据
            return null;
        }
        int numLength = numStr.length();
        //8421码，即BCD码
        StringBuilder numberBuilder = new StringBuilder();
        if (numLength < length) {
            int append = length - numLength;

            if (frontAddZero) {
                //前端补0
                for (int i = 0; i < append; i++) {
                    numberBuilder.append("0");
                }
                numberBuilder.append(numStr);
            } else {
                //后端补0
                numberBuilder.append(numStr);
                for (int i = 0; i < append; i++) {
                    numberBuilder.append("0");
                }
            }

        } else {
            numberBuilder.append(numStr.substring(0, length));//只截取指定长度的字符串
        }
        numStr = numberBuilder.toString();
        int numberByte = 0;
        int bytesLength = length / 2;//计算转化后的数组长度
//        byte[] devicePhoneNumberBytes = new byte[bytesLength];
        ByteBuffer numberByteBuffer = ByteBuffer.allocate(bytesLength);
        for (int i = 0; i < numStr.length(); i++) {
            char c = numStr.charAt(i);
            int number = Integer.parseInt(String.valueOf(c), 16);
            if (i % 2 == 0) {//高4位
                numberByte = number << 4;
            } else {//低四位
                numberByte ^= number;
                int index = (i / 2);
//                devicePhoneNumberBytes[index] = DataUtils.integerTo1Byte(numberByte);
                numberByteBuffer.put(index, DataUtils.integerTo1Byte(numberByte));
            }
        }
        byte[] numberBytes = numberByteBuffer.array();
//        LogUtils.d(TAG, "numberBytesBCD->:" + bytes2HexString(numberBytes));
        return numberBytes;
    }

    /**
     * 将手机号码转换成BCD字节码
     *
     * @param phoneNum
     * @return
     */
    public static byte[] getPhoneBytesBCD(String phoneNum) {
        int length = phoneNum.length();
        //8421码表示手机号，BCD码（手机号不足 12位，则在前补充数字，大陆手机号补充数字 0，港澳台则根据其区号进行位数补充。）
        //港澳台手机号
        StringBuilder devicePhoneNumberBuilder = new StringBuilder();
        if (length < 12) {
            int append = 12 - length;
            for (int i = 0; i < append; i++) {
                devicePhoneNumberBuilder.append("0");
            }
            devicePhoneNumberBuilder.append(phoneNum);
        } else {
            devicePhoneNumberBuilder.append(phoneNum.substring(0, 12));//只截取前12位字符串
        }
        phoneNum = devicePhoneNumberBuilder.toString();
        int numberByte = 0;
        byte[] devicePhoneNumberBytes = new byte[6];
        for (int i = 0; i < phoneNum.length(); i++) {
            char c = phoneNum.charAt(i);
            int number = Integer.valueOf(String.valueOf(c));
            if (i % 2 == 0) {//高4位
                numberByte = number << 4;
            } else {//低四位
                numberByte ^= number;
                int index = (i / 2);
                devicePhoneNumberBytes[index] = DataUtils.integerTo1Byte(numberByte);
            }
        }
//        LogUtils.d(TAG, "phoneNumberBCD->:" + bytes2HexString(devicePhoneNumberBytes));
        return devicePhoneNumberBytes;
    }


    /**
     * 将手机号码BCD字节码转成字符串(6个字节存储一个手机号码)
     *
     * @param phoneNumBytes
     * @return
     */
    public static String getStringFromBCD(byte[] phoneNumBytes) {
        byte[] devicePhoneNumberBytes = subBytes(phoneNumBytes, 0, phoneNumBytes.length);
        StringBuilder devicePhoneNumberBuilder = new StringBuilder();
        for (int i = 0; i < devicePhoneNumberBytes.length; i++) {
            byte devicePhoneNumberByte2 = devicePhoneNumberBytes[i];
            int devicePhoneNumber = DataUtils.oneByteToInteger(devicePhoneNumberByte2);
            int numberHigh = devicePhoneNumber >> 4;
            devicePhoneNumberBuilder.append(Integer.toHexString(numberHigh));
            int numberLow = devicePhoneNumber & 0x0F;
            devicePhoneNumberBuilder.append(Integer.toHexString(numberLow));
        }
        for (; ; ) {
            String phone = devicePhoneNumberBuilder.toString();
            if (phone.startsWith("0")) {//去除填充的数字0（包括港澳台的手机号码）
                devicePhoneNumberBuilder.deleteCharAt(0);
            } else {
                break;
            }
        }
        return devicePhoneNumberBuilder.toString();
    }

    /**
     * 将手机号码BCD字节码转成字符串(6个字节存储一个手机号码)
     *
     * @param phoneNumBytes
     * @return
     */
    public static String getPhoneFromBCD(byte[] phoneNumBytes) {
        //8421码表示手机号，BCD码（手机号不足 12位，则在前补充数字，大陆手机号补充数字 0，港澳台则根据其区号进行位数补充。）
        //港澳台手机号
        byte[] devicePhoneNumberBytes = subBytes(phoneNumBytes, 0, 6);

        StringBuilder devicePhoneNumberBuilder = new StringBuilder();
        for (int i = 0; i < devicePhoneNumberBytes.length; i++) {
            byte devicePhoneNumberByte2 = devicePhoneNumberBytes[i];
            int devicePhoneNumber = DataUtils.oneByteToInteger(devicePhoneNumberByte2);
            int numberHigh = devicePhoneNumber >> 4;
            devicePhoneNumberBuilder.append(String.valueOf(numberHigh));
            int numberLow = devicePhoneNumber & 0x0F;
            devicePhoneNumberBuilder.append(String.valueOf(numberLow));
        }
        for (; ; ) {
            String phone = devicePhoneNumberBuilder.toString();
            if (phone.startsWith("0")) {//去除填充的数字0（包括港澳台的手机号码）
                devicePhoneNumberBuilder.deleteCharAt(0);
            } else {
                break;
            }
        }
//        LogUtils.d(TAG, "phoneNumber->:" + devicePhoneNumberBuilder.toString());
        return devicePhoneNumberBuilder.toString();
    }

    /**
     * 将日期转化成byte数组
     *
     * @param calendar
     * @return
     */
    public static byte[] getTimeStampBytes(Calendar calendar) {
        //年(6 位)                     月(4 位)     日(5 位)       时(5 位)       分(6 位)       秒(6 位)
        //有效值 0~63,从2010年开始计算 有效值 1~12  有效值 1~31    有效值 0~23    有效值 0~59    有效值 0~59
        int year = calendar.get(Calendar.YEAR);
        year -= 2010;//日期时间戳年份从2010开始，有效值为0-63
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        minute <<= 6;//左移6位
        hour <<= (6 + 6);//左移12位
        day <<= (6 + 6 + 5);//左移17位
        month <<= (6 + 6 + 5 + 5);//左移22位
        year <<= (6 + 6 + 5 + 5 + 4);//左移26位
        long timeStamp = year ^ month ^ day ^ hour ^ minute ^ second;//32位长度，4字节
        //转换成4个字节数组
        byte[] timeStampBytes = DataUtils.longToBytes(timeStamp, 4);
//        LogUtils.d(TAG, "TimeStamp->" + bytes2HexString(timeStampBytes));
        return timeStampBytes;
    }

    /**
     * 将日期转化成byte数组
     *
     * @param calendar
     * @param isLittle
     * @return
     */
    public static byte[] getTimeStampBytes(Calendar calendar, boolean isLittle) {
        //年(6 位)                     月(4 位)     日(5 位)       时(5 位)       分(6 位)       秒(6 位)
        //有效值 0~63,从2010年开始计算 有效值 1~12  有效值 1~31    有效值 0~23    有效值 0~59    有效值 0~59
        int year = calendar.get(Calendar.YEAR);
        year -= 2010;//日期时间戳年份从2010开始，有效值为0-63
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        minute <<= 6;//左移6位
        hour <<= (6 + 6);//左移12位
        day <<= (6 + 6 + 5);//左移17位
        month <<= (6 + 6 + 5 + 5);//左移22位
        year <<= (6 + 6 + 5 + 5 + 4);//左移26位
        long timeStamp = year ^ month ^ day ^ hour ^ minute ^ second;//32位长度，4字节
        //转换成4个字节数组
        byte[] timeStampBytes = DataUtils.longToBytes(timeStamp, 4, isLittle);
        //LogUtils.d(TAG, "TimeStamp->" + bytes2HexString(timeStampBytes));
        return timeStampBytes;
    }

    /**
     * 将时间戳byte数组转化成日期
     *
     * @param timeStampBytes
     * @return
     */
    public static Calendar getTimeStampFromBytes(byte[] timeStampBytes) {
        //年(6 位)                     月(4 位)     日(5 位)       时(5 位)       分(6 位)       秒(6 位)
        //有效值 0~63,从2010年开始计算 有效值 1~12  有效值 1~31    有效值 0~23    有效值 0~59    有效值 0~59
        long time = DataUtils.bytes2Long(timeStampBytes);
        int second = (int) (time & 0X3F);
        int minute = (int) (time >> 6 & 0X3F);
        int hour = (int) (time >> 12 & 0X1F);
        int day = (int) (time >> 17 & 0X1F);
        int month = (int) (time >> 22 & 0X0F) - 1;
        int year = (int) (time >> 26 & 0X3F) + 2010;
//        time & 3F

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar;
    }

    /**
     * 将时间戳byte数组转化成日期
     *
     * @param timeStampBytes
     * @param isLittle
     * @return
     */
    public static Calendar getTimeStampFromBytes(byte[] timeStampBytes, boolean isLittle) {
        //年(6 位)                     月(4 位)     日(5 位)       时(5 位)       分(6 位)       秒(6 位)
        //有效值 0~63,从2010年开始计算 有效值 1~12  有效值 1~31    有效值 0~23    有效值 0~59    有效值 0~59

        if (isLittle) {
            long time = DataUtils.bytes2Long(timeStampBytes, true);
            int second = (int) (time & 0X3F);
            int minute = (int) (time >> 6 & 0X3F);
            int hour = (int) (time >> 12 & 0X1F);
            int day = (int) (time >> 17 & 0X1F);
            int month = (int) (time >> 22 & 0X0F) - 1;
            int year = (int) (time >> 26 & 0X3F) + 2010;
//        time & 3F

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute, second);
            return calendar;
        } else {
            long time = DataUtils.bytes2Long(timeStampBytes);
            int second = (int) (time & 0X3F);
            int minute = (int) (time >> 6 & 0X3F);
            int hour = (int) (time >> 12 & 0X1F);
            int day = (int) (time >> 17 & 0X1F);
            int month = (int) (time >> 22 & 0X0F) - 1;
            int year = (int) (time >> 26 & 0X3F) + 2010;
//        time & 3F

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute, second);
            return calendar;
        }
    }

    /**
     * 接收到的命令进行协议转义获取转义前的真实数据 转义规则如下： 0x7F <————> 0x7d 后紧跟一个 0x02； 0x7d <————> 0x7d 后紧跟一个 0x01。
     *
     * @param receiveBytes
     * @return
     */
    public static ByteBuffer escapeReceive(byte[] receiveBytes) {
        int len = receiveBytes.length;
        int escapeTimes = 0;
        ByteBuffer buf = ByteBuffer.allocate(len);//
        for (int i = 0; i < len; i++) {
            if (receiveBytes[i] == MSG_IDENTIFY_AFTER_INT && receiveBytes[i + 1] == 0x01) {
                buf.put(MSG_IDENTIFY_AFTER);
                i++;
                escapeTimes++;
            } else if (receiveBytes[i] == MSG_IDENTIFY_AFTER_INT && receiveBytes[i + 1] == 0x02) {
                buf.put(MSG_IDENTIFY);
                i++;
                escapeTimes++;
            } else {
                buf.put(receiveBytes[i]);
            }
        }
//        LogUtils.d(TAG, "接收命令需要转义的字节数：" + escapeTimes);
        byte[] array = buf.array();
        int length = len - escapeTimes;//将转义字符还原后的实际字节长度
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.put(array, 0, length);
        return allocate;
    }

    public static byte[] escapeReceiveByte(byte[] receiveBytes) {
        return escapeReceive(receiveBytes).array();
    }

    /**
     * 接收到的命令进行协议转义获取转义前的真实数据 转义规则如下： 0x7e <————> 0x7d 后紧跟一个 0x02； 0x7d <————> 0x7d 后紧跟一个 0x01。
     *
     * @param receiveBytes
     * @return
     */
    public static ByteBuffer escapeReceiveClient(byte[] receiveBytes) {
        int len = receiveBytes.length;
        int escapeTimes = 0;
        ByteBuffer buf = ByteBuffer.allocate(len);//
        for (int i = 0; i < len; i++) {
            if (receiveBytes[i] == MSG_IDENTIFY_AFTER_INT && receiveBytes[i + 1] == 0x01) {
                buf.put(MSG_IDENTIFY_AFTER);
                i++;
                escapeTimes++;
            } else if (receiveBytes[i] == MSG_IDENTIFY_AFTER_INT && receiveBytes[i + 1] == 0x02) {
                buf.put(MSG_CLIENT_IDENTIFY);
                i++;
                escapeTimes++;
            } else {
                buf.put(receiveBytes[i]);
            }
        }
//        LogUtils.d(TAG, "接收命令需要转义的字节数：" + escapeTimes);
        byte[] array = buf.array();
        int length = len - escapeTimes;//将转义字符还原后的实际字节长度
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.put(array, 0, length);
        return buf;
    }

    /**
     * 将需要发送的命令按照规则进行转义 转义规则如下： 0x7F <————> 0x7d 后紧跟一个 0x02； 0x7d <————> 0x7d 后紧跟一个 0x01。
     *
     * @param sendBytes 转义的数组
     * @param start     起始索引
     * @param end       结束索引
     * @return
     */
    public static byte[] escapeSend(byte[] sendBytes, int start, int end) {
        int len = sendBytes.length;
        int escapeTimes = 0;
        ByteBuffer buf = ByteBuffer.allocate(len + (len / 2));//假设最多有一半的字节都需要转义（为增加效率，可根据实际情况做调整）
        for (int i = 0; i < start; i++) {
            buf.put(sendBytes[i]);
        }
        for (int i = start; i < end; i++) {
            if (sendBytes[i] == MSG_IDENTIFY_INT) {
                buf.put(MSG_IDENTIFY_AFTER);
                buf.put(DataUtils.integerTo1Byte(0x02));
                escapeTimes++;
            } else if (sendBytes[i] == MSG_IDENTIFY_AFTER_INT) {
                buf.put(MSG_IDENTIFY_AFTER);
                buf.put(DataUtils.integerTo1Byte(0x01));
                escapeTimes++;
            } else {
                buf.put(sendBytes[i]);
            }
        }
        for (int i = end; i < len; i++) {
            buf.put(sendBytes[i]);
        }
//        LogUtils.d(TAG, "发送命令需要转义的字节数：" + escapeTimes);
        byte[] array = buf.array();
        int length = len + escapeTimes;//转义后的实际字节长度
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.put(array, 0, length);
        return allocate.array();
    }

    /**
     * 将需要发送的命令按照规则进行转义 转义规则如下： 0x7e <————> 0x7d 后紧跟一个 0x02； 0x7d <————> 0x7d 后紧跟一个 0x01。
     *
     * @param sendBytes 转义的数组
     * @param start     起始索引
     * @param end       结束索引
     * @return
     */
    public static ByteBuffer escapeSendClient(byte[] sendBytes, int start, int end) {
        int len = sendBytes.length;
        int escapeTimes = 0;
        ByteBuffer buf = ByteBuffer.allocate(len + (len / 2));//假设最多有一半的字节都需要转义（为增加效率，可根据实际情况做调整）
        for (int i = 0; i < start; i++) {
            buf.put(sendBytes[i]);
        }
        for (int i = start; i < end; i++) {
            if (sendBytes[i] == MSG_CLIENT_IDENTIFY_INT) {
                buf.put(MSG_IDENTIFY_AFTER);
                buf.put(DataUtils.integerTo1Byte(0x02));
                escapeTimes++;
            } else if (sendBytes[i] == MSG_IDENTIFY_AFTER_INT) {
                buf.put(MSG_IDENTIFY_AFTER);
                buf.put(DataUtils.integerTo1Byte(0x01));
                escapeTimes++;
            } else {
                buf.put(sendBytes[i]);
            }
        }
        for (int i = end; i < len; i++) {
            buf.put(sendBytes[i]);
        }
//        LogUtils.d(TAG, "发送命令需要转义的字节数：" + escapeTimes);
        byte[] array = buf.array();
        int length = len + escapeTimes;//转义后的实际字节长度
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.put(array, 0, length);
        return allocate;
    }

    /**
     * 把一个整形该为byte
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte integerTo1Byte(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 把一个整形该为1位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo1Bytes(int value) {
        byte[] result = new byte[1];
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为2位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo2Bytes(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) ((value >>> 8) & 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为2位的byte数组 小true  大false
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static byte[] integerTo2Bytes(int value, boolean isLittle) {
        if (isLittle) {
            byte[] result = new byte[2];
            result[0] = (byte) (value & 0xFF);
            result[1] = (byte) ((value >>> 8) & 0xFF);
            return result;
        } else {
            byte[] result = new byte[2];
            result[0] = (byte) ((value >>> 8) & 0xFF);
            result[1] = (byte) (value & 0xFF);
            return result;
        }

    }

    /**
     * 把一个整形改为3位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo3Bytes(int value) {
        byte[] result = new byte[3];
        result[0] = (byte) ((value >>> 16) & 0xFF);
        result[1] = (byte) ((value >>> 8) & 0xFF);
        result[2] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为4位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] integerTo4Bytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) ((value >>> 24) & 0xFF);
        result[1] = (byte) ((value >>> 16) & 0xFF);
        result[2] = (byte) ((value >>> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为4位的byte数组
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static byte[] integerTo4Bytes(int value, boolean isLittle) {
        if (isLittle) {
            byte[] result = new byte[4];
            result[3] = (byte) ((value >>> 24) & 0xFF);
            result[2] = (byte) ((value >>> 16) & 0xFF);
            result[1] = (byte) ((value >>> 8) & 0xFF);
            result[0] = (byte) (value & 0xFF);
            return result;
        } else {
            byte[] result = new byte[4];
            result[0] = (byte) ((value >>> 24) & 0xFF);
            result[1] = (byte) ((value >>> 16) & 0xFF);
            result[2] = (byte) ((value >>> 8) & 0xFF);
            result[3] = (byte) (value & 0xFF);
            return result;
        }
    }

    /**
     * 把byte[]转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int byteToInteger(byte[] value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * 把byte[]转化位整形,通常为指令用
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static int byteToInteger(byte[] value, boolean isLittle) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value, isLittle);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value, isLittle);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value, isLittle);
        } else {
            result = fourBytesToInteger(value, isLittle);
        }
        return result;
    }

    /**
     * 把一个byte转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int twoBytesToInteger(byte[] value) {
        // if (value.length < 2) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个2位的数组转化位整形
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static int twoBytesToInteger(byte[] value, boolean isLittle) {
        // if (value.length < 2) {
        // throw new Exception("Byte array too short!");
        // }

        if (isLittle) {
            int temp0 = value[1] & 0xFF;
            int temp1 = value[0] & 0xFF;
            return ((temp0 << 8) + temp1);
        } else {
            int temp0 = value[0] & 0xFF;
            int temp1 = value[1] & 0xFF;
            return ((temp0 << 8) + temp1);
        }

    }

    /**
     * 把一个3位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个3位的数组转化位整形
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static int threeBytesToInteger(byte[] value, boolean isLittle) {
        if (isLittle) {
            int temp0 = value[2] & 0xFF;
            int temp1 = value[1] & 0xFF;
            int temp2 = value[0] & 0xFF;
            return ((temp0 << 16) + (temp1 << 8) + temp2);
        } else {
            int temp0 = value[0] & 0xFF;
            int temp1 = value[1] & 0xFF;
            int temp2 = value[2] & 0xFF;
            return ((temp0 << 16) + (temp1 << 8) + temp2);
        }

    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int fourBytesToInteger(byte[] value) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static int fourBytesToInteger(byte[] value, boolean isLittle) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        if (isLittle) {
            int temp0 = value[3] & 0xFF;
            int temp1 = value[2] & 0xFF;
            int temp2 = value[1] & 0xFF;
            int temp3 = value[0] & 0xFF;
            return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
        } else {
            int temp0 = value[0] & 0xFF;
            int temp1 = value[1] & 0xFF;
            int temp2 = value[2] & 0xFF;
            int temp3 = value[3] & 0xFF;
            return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
        }

    }

    /**
     * 把一个4位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static long fourBytesToLong(byte[] value) throws Exception {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个数组转化长整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static long bytes2Long(byte[] value) {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result += (value[i] & 0x0ff);
            } else {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }

    /**
     * 把一个数组转化长整形
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static long bytes2Long(byte[] value, boolean isLittle) {
        if (isLittle) {
            long result = 0;
            int len = value.length;
            int temp;
            for (int i = 0; i < len; i++) {
                temp = (len - 1 - i) * 8;
                if (temp == 0) {
                    result += (value[len - 1 - i] & 0x0ff);
                } else {
                    result += (value[len - 1 - i] & 0x0ff) << temp;
                }
            }
            return result;
        } else {
            long result = 0;
            int len = value.length;
            int temp;
            for (int i = 0; i < len; i++) {
                temp = (len - 1 - i) * 8;
                if (temp == 0) {
                    result += (value[i] & 0x0ff);
                } else {
                    result += (value[i] & 0x0ff) << temp;
                }
            }
            return result;
        }

    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @param isLittle
     * @return
     * @throws Exception
     */
    public static byte[] longToBytes(long value, int len, boolean isLittle) {
        if (isLittle) {
            byte[] result = new byte[len];
            int temp;
            for (int i = 0; i < len; i++) {
                temp = (len - 1 - i) * 8;
                if (temp == 0) {
                    result[len - 1 - i] += (value & 0x0ff);
                } else {
                    result[len - 1 - i] += (value >>> temp) & 0x0ff;
                }
            }
            return result;
        } else {
            byte[] result = new byte[len];
            int temp;
            for (int i = 0; i < len; i++) {
                temp = (len - 1 - i) * 8;
                if (temp == 0) {
                    result[i] += (value & 0x0ff);
                } else {
                    result[i] += (value >>> temp) & 0x0ff;
                }
            }
            return result;
        }

    }

    /**
     * 得到一个消息ID
     *
     * @return
     * @throws Exception
     */
    public static byte[] generateTransactionID() throws Exception {
        byte[] id = new byte[16];
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 2, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 4, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 6, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 8, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 10, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 12, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 14, 2);
        return id;
    }

    /**
     * 生成随机数(4字节)
     *
     * @return
     * @throws Exception
     */
    public static byte[] generateRandomID() {
        byte[] id = new byte[4];
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 2, 2);
        return id;
    }

    /**
     * 生成随机数(4字节)
     *
     * @param radHigh
     * @param radLow
     * @return
     */
    public static byte[] generate4RandomID(int radHigh, int radLow) {
        byte[] id = new byte[4];
        System.arraycopy(integerTo2Bytes(radHigh), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes(radLow), 0, id, 2, 2);
        return id;
    }

    /**
     * 生成随机数(4字节)
     *
     * @param randomId
     * @return
     */
    public static byte[] generate4RandomID(long randomId) {
        //生成随机数
        int radHigh = (int) (randomId >> 16);
        int radLow = (int) (randomId & 0x0000FFFF);
        byte[] id = new byte[4];
        System.arraycopy(integerTo2Bytes(radHigh), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes(radLow), 0, id, 2, 2);
        return id;
    }

    /**
     * 生成随机数(4字节)
     *
     * @param randomId
     * @param isLittle 是否是小端模式
     * @return
     */
    public static byte[] generate4RandomID(long randomId, boolean isLittle) {
        //生成随机数
        if (isLittle) {
            int radHigh = (int) (randomId >> 16);
            int radLow = (int) (randomId & 0x0000FFFF);
            byte[] id = new byte[4];
            System.arraycopy(integerTo2Bytes(radLow, true), 0, id, 0, 2);
            System.arraycopy(integerTo2Bytes(radHigh, true), 0, id, 2, 2);
            return id;
        } else {
            int radHigh = (int) (randomId >> 16);
            int radLow = (int) (randomId & 0x0000FFFF);
            byte[] id = new byte[4];
            System.arraycopy(integerTo2Bytes(radHigh), 0, id, 0, 2);
            System.arraycopy(integerTo2Bytes(radLow), 0, id, 2, 2);
            return id;
        }

    }

    /**
     * 获取随机数(从4字节解析出)
     *
     * @param randomIDBytes
     * @return
     */
    public static long getRandomIDFrom4Bytes(byte[] randomIDBytes) {
        byte[] highBytes = subBytes(randomIDBytes, 0, 2);
        int high = twoBytesToInteger(highBytes);
        byte[] lowBytes = subBytes(randomIDBytes, 2, 2);
        int low = twoBytesToInteger(lowBytes);
        long rad = high << 16;
        rad ^= low;
        return rad;
    }

    /**
     * 获取随机数(从4字节解析出)
     *
     * @param randomIDBytes
     * @param isLittle
     * @return
     */
    public static long getRandomIDFrom4Bytes(byte[] randomIDBytes, boolean isLittle) {
        if (isLittle) {
            byte[] highBytes = subBytes(randomIDBytes, 2, 2);
            int high = twoBytesToInteger(highBytes);
            byte[] lowBytes = subBytes(randomIDBytes, 0, 2);
            int low = twoBytesToInteger(lowBytes);
            long rad = high << 16;
            rad ^= low;
            return rad;
        } else {
            byte[] highBytes = subBytes(randomIDBytes, 0, 2);
            int high = twoBytesToInteger(highBytes);
            byte[] lowBytes = subBytes(randomIDBytes, 2, 2);
            int low = twoBytesToInteger(lowBytes);
            long rad = high << 16;
            rad ^= low;
            return rad;
        }

    }

    /**
     * 把IP拆分位int数组
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public static int[] getIntIPValue(String ip) throws Exception {
        String[] sip = ip.split("[.]");
        // if (sip.length != 4) {
        // throw new Exception("error IPAddress");
        // }
        int[] intIP = {Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]),
                Integer.parseInt(sip[3])};
        return intIP;
    }

    /**
     * 把byte类型IP地址转化位字符串
     *
     * @param address
     * @return
     * @throws Exception
     */
    public static String getStringIPValue(byte[] address) throws Exception {
        int first = oneByteToInteger(address[0]);
        int second = oneByteToInteger(address[1]);
        int third = oneByteToInteger(address[2]);
        int fourth = oneByteToInteger(address[3]);

        return first + "." + second + "." + third + "." + fourth;
    }

    /**
     * 合并字节数组
     *
     * @param first
     * @param rest
     * @return
     */
    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    /**
     * 合并字节数组
     *
     * @param rest
     * @return
     */
    public static byte[] concatAll(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    public static float byte2Float(byte[] bs) {
        return Float.intBitsToFloat(
                (((bs[3] & 0xFF) << 24) + ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
    }

    public static float byteBE2Float(byte[] bytes) {
        int l;
        l = bytes[0];
        l &= 0xff;
        l |= ((long) bytes[1] << 8);
        l &= 0xffff;
        l |= ((long) bytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) bytes[3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static int getCheckSum4JT808(byte[] bs, int start, int end) {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("getCheckSum4JT808 error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        int cs = bs[start];
        for (int i = start + 1; i < end; i++) {
            cs ^= bs[i];
        }
        return cs;
    }

    public static int getBitRange(int number, int start, int end) {
        if (start < 0)
            throw new IndexOutOfBoundsException("min index is 0,but start = " + start);
        if (end >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but end = " + end);

        return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE - (end - start + 1);
    }

    public static int getBitAt(int number, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("min index is 0,but " + index);
        if (index >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but " + index);

        return ((1 << index) & number) >> index;
    }

    public static int getBitAtS(int number, int index) {
        String s = Integer.toBinaryString(number);
        return Integer.parseInt(s.charAt(index) + "");
    }

    @Deprecated
    public static int getBitRangeS(int number, int start, int end) {
        String s = Integer.toBinaryString(number);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < Integer.SIZE) {
            sb.insert(0, "0");
        }
        String tmp = sb.reverse().substring(start, end + 1);
        sb = new StringBuilder(tmp);
        return Integer.parseInt(sb.reverse().toString(), 2);
    }

    /**
     * 将字节数组转化为16进制数据显示
     *
     * @param bytes
     * @return
     */
    public static String bytes2HexString(byte[] bytes) {
        return bytes2HexString(bytes, " ");
    }

    /**
     * 将字节数组转化为16进制数据显示
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        StringBuilder stringBuilder = new StringBuilder();
        String byteStr = Integer.toHexString(b & 0xFF);
        if (byteStr.length() == 1) {
            stringBuilder.append("0").append(byteStr);
        } else {
            stringBuilder.append(byteStr);
        }
        return stringBuilder.toString();
    }

    /**
     * 将字节数组转化为16进制数据显示
     *
     * @param bytes
     * @param regex
     * @return
     */
    public static String bytes2HexString(byte[] bytes, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bytesHexStr:[");
        for (int i = 0; i < bytes.length; i++) {
            String byteStr = Integer.toHexString(bytes[i] & 0xFF);
            if (byteStr.length() == 1) {
                stringBuilder.append("0").append(byteStr);
            } else {
                stringBuilder.append(byteStr);
            }
            if (i < bytes.length - 1) {
                stringBuilder.append(regex);
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * 将数字转化为16进制数据显示
     *
     * @param orderId
     * @return
     */
    public static String int2HexString(int orderId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        String byteStr = Integer.toHexString(orderId & 0xFF);
        if (byteStr.length() == 1) {
            stringBuilder.append("0").append(byteStr);
        } else {
            stringBuilder.append(byteStr);
        }
        return stringBuilder.toString();
    }

    /**
     * 计算CRC32校验码
     *
     * @param data  数据源
     * @param start 校验码开始索引
     * @param end   校验码结束索引
     * @return
     */
    public static byte[] getCrc32(byte[] data, int start, int end) {
        CRC32 crc32 = new CRC32();
        byte[] bytes = subBytes(data, start, (end - start));
        crc32.update(bytes);
        long value = crc32.getValue();
        byte[] crcBytes = longToBytes(value, 4);//四个字节的CRC32校验码
//        byte[] crcBytes = longToBytes(value, 2);
//        LogUtils.d(TAG, "CRC->" + bytes2HexString(crcBytes));
        return crcBytes;
    }

    /**
     * 计算CRC32校验码
     *
     * @param data     数据源
     * @param start    校验码开始索引
     * @param end      校验码结束索引
     * @param isLittle
     * @return
     */
    public static byte[] getCrc32(byte[] data, int start, int end, boolean isLittle) {
//        CRC32 crc32 = new CRC32();
        byte[] bytes = subBytes(data, start, (end - start));
//        crc32.update(bytes);
//        long value = crc32.getValue();
//        byte[] crcBytes = longToBytes(value,4, isLittle);//四个字节的CRC32校验码
        long crc321 = CRCUtils.getCrc32(bytes);
        byte[] crcBytes = longToBytes(crc321, 4, isLittle);
//        byte[] crcBytes = longToBytes(value,4, isLittle);//四个字节的CRC32校验码
//        byte[] crcBytes = longToBytes(value, 2);
//        LogUtils.d(TAG, "CRC->" + bytes2HexString(crcBytes));
        return crcBytes;
    }

    /**
     * 获取BCC校验码 版权声明：本文为CSDN博主「_风轻云淡_」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。 原文链接：https://blog.csdn.net/qq_16855077/article/details/84336936
     *
     * @param data
     * @param start 开始位置0
     * @param end   字节数组长度
     * @return
     */
    public static byte[] getBCC(byte[] data, int start, int end) {
        String ret = "";
        byte BCC[] = new byte[1];
        for (int i = start; i < data.length; i++) {
            if (i == end) {
                break;
            }
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        return BCC;
    }

    /**
     * 获取BCC校验码 版权声明：本文为CSDN博主「_风轻云淡_」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。 原文链接：https://blog.csdn.net/qq_16855077/article/details/84336936
     *
     * @param data
     * @param start 开始位置0
     * @param end   字节数组长度
     * @return
     */
    public static String getBCCString(byte[] data, int start, int end) {
        String ret = "";
        byte BCC[] = new byte[1];
        for (int i = start; i < data.length; i++) {
            if (i == end) {
                break;
            }
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        String hex = Integer.toHexString(BCC[0] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }

    /**
     * CRC校验
     *
     * @param orderId
     * @param array   不包含0x7e
     * @return
     */
    public static boolean checkCrcClient(int orderId, byte[] array) {
        //两个字节的CRC校验
//        byte[] crcBytes = DataUtils.subBytes(array, array.length - 2 - 1 , 2);
//        //截取需要计算crc的数据(去掉2字节的数据长度和末尾的2字节CRC校验码)
//        byte[] crcCalculate = getCrc32(array, 2, array.length - 2);
//        if (crcBytes[0] == crcCalculate[0] && crcBytes[1] == crcCalculate[1]) {
//            return true;
//        } else {
//            LogUtils.e(bytes2HexString(array));
//            LogUtils.e(int2HexString(orderId) + " 校验码不一致" + " crc:" + bytes2HexString(crcBytes) + " crcCalculate:" + bytes2HexString(crcCalculate));
//            return false;
//        }

        //四个字节的crc校验
        byte[] crcBytes = DataUtils.subBytes(array, array.length - 4 - 1, 4);
        //截取需要计算crc的数据(包括2字节的数据长度，去掉末尾的4字节CRC校验码)
        byte[] crcCalculate = getCrc32(array, 0, array.length - 4, true);
        if (crcCalculate.length == 4) {
            if (crcBytes[0] == crcCalculate[0] && crcBytes[1] == crcCalculate[1] && crcBytes[2] == crcCalculate[2] && crcBytes[3] == crcCalculate[3]) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
