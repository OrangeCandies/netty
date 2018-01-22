package chap2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexrTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel channel;
    private volatile boolean stop;


    public MultiplexrTimeServer(int port) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port), 1024);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Multi-plexr-timer-server-begin-with-port-" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        stop = true;
    }

    private void handInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);

            }

            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBuffers = sc.read(byteBuffer);
                if (readBuffers > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String info = new String(bytes, "UTF-8");
                    System.out.println("Get oreder:" + info);
                    String sendInfo = "QUERY TIME ORDER".equalsIgnoreCase(info) ? new java.util.Date(System.currentTimeMillis()).toString() : "Bad requset";
                    doWrite(sc, sendInfo);
                } else if (readBuffers < 0) {
                    sc.close();
                    key.cancel();
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String sendInfo) throws IOException {
        if (sendInfo != null && sendInfo.length() > 0) {
            byte[] bytes = sendInfo.getBytes();
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
            allocate.put(bytes);
            allocate.flip();
            sc.write(allocate);
        }
    }


    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                SelectionKey key = null;
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    handInput(key);

                    if (key != null) {
                        key.cancel();
                        if (key.channel() != null){
                            key.channel().close();

                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
