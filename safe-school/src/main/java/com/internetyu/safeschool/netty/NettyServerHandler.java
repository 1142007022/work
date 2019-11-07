package com.internetyu.safeschool.netty;

import com.internetyu.safeschool.mq.Sender;
import com.internetyu.safeschool.netty.packet.SafeSchoolPacket;
import com.internetyu.safeschool.service.TransmitService;
import com.internetyu.safeschool.util.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000610:54
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    TransmitService transmitService = SpringUtils.getBean(TransmitService.class);

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("收到连接:{}", channelHandlerContext);
        SpringUtils.getBean(Sender.class).send("direct", "test");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println(transmitService);
        //todo packet分发处理 同时传过去channelHandlerContext以回复
        SafeSchoolPacket safeSchoolPacket = (SafeSchoolPacket) msg;
        log.info("服务器收到消息: {}", safeSchoolPacket);
        channelHandlerContext.write(safeSchoolPacket);
        channelHandlerContext.flush();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();
        channelHandlerContext.close();
        log.error("连接异常: {}" + cause.getMessage());
    }

}
