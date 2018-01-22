package chap2.nio;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexrTimeServer server = new MultiplexrTimeServer(port);
        new Thread(server,"NIO-Server-001").start();
    }
}
