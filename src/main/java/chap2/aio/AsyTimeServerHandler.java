package chap2.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyTimeServerHandler implements Runnable{

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel serverSocketChannel;


    public AsyTimeServerHandler(int port){
        this.port = port;
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("server bind with port: "+port);
            //

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept(){
       // serverSocketChannel.accept(this,new AsyTimeServerHandler(port));
    }
}
