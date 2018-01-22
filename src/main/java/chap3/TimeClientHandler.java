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

    public TimeClientHandler(){
        byte [] bytes = "Query Time Order".getBytes();
        firstMessage = Unpooled.buffer(bytes.length);
        firstMessage.writeBytes(bytes);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String reqs = new String(req,"utf-8");
        System.out.println("now is "+reqs);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
