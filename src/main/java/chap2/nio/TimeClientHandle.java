package chap2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel channel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doConnection() throws IOException {
        if (channel.connect(new InetSocketAddress(host, port))) {
            channel.register(selector, SelectionKey.OP_READ);
            doWrite(channel);
        } else {
            channel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel channel) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuff = ByteBuffer.allocate(req.length);
        writeBuff.put(req);
        writeBuff.flip();
        channel.write(writeBuff);

    }

    public void run() {
        try {
            doConnection();
            while (!stop) {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    handInput(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){
                    sc.register(selector,SelectionKey.OP_READ);
                    doWrite(sc);
                }
            }
            if(key.isReadable()){
                ByteBuffer read = ByteBuffer.allocate(1024);
                int readNumbers = channel.read(read);
                if(readNumbers > 0){
                    read.flip();
                    byte [] bytes = new byte[read.remaining()];
                    read.get(bytes);
                    String info = new String(bytes,"UTF-8");
                    System.out.println("Now is : "+info);
                    this.stop = true;
                }else if( readNumbers < 0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }
}
