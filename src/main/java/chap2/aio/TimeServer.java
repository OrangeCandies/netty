package chap2.aio;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        AsyTimeServerHandler timeServerHandler = new AsyTimeServerHandler(port);
        new Thread(()->{
            System.out.println("Hello");
        }).start();
    }
}
