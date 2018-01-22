package chap3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf read = (ByteBuf) msg;
        byte[] bytes = new byte[read.readableBytes()];
        read.readBytes(bytes);
        String body = new String(bytes,"utf-8");
        System.out.println("Receiver message :"+body);
        String resp = "Query time order".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"Bad request";
        ByteBuf response =  Unpooled.copiedBuffer(resp.getBytes());
        ctx.write(response);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
