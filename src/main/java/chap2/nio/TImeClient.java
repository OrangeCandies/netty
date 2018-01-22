package chap2.nio;

public class TImeClient {
    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            new Thread(new TimeClientHandle("127.0.0.1",8080),"Time-client"+i).start();
        }
    }
}
