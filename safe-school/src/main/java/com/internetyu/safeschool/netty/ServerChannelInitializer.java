package com.internetyu.safeschool.netty;

import com.internetyu.safeschool.netty.coder.SafeSchoolDecoder;
import com.internetyu.safeschool.netty.coder.SafeSchoolEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000610:57
 */
public class ServerChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        //添加编解码
        ch.pipeline().addLast("decoder", new SafeSchoolDecoder());
        ch.pipeline().addLast("encoder", new SafeSchoolEncoder());
        ch.pipeline().addLast(new NettyServerHandler());
    }
}
