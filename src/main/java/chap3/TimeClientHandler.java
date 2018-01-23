package chap3;

import chap2.nio.TimeClientHandle;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {



    private static final Logger LOGGER = Logger.getLogger(TimeClientHandle.class.getName());
    private final ByteBuf firstMessage;
    private byte [] req;

    public TimeClientHandler(){
        req = ("Query Time Order"+System.getProperty("line.separator")).getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String reqs = (String)msg;
        System.out.println("now is "+reqs);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for(int i=0;i<100;i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
