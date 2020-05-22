import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static Main main;
    private int nrOfThreads = 100;
    String cephIp = "http://255.255.255.255:80";
    String ipfsIp = "255.255.255.255:9095";

    public static void main(String[] args) {
        main = new Main();

    }

    // asd: QmcSxBqg8KzGW6w5ix2MjcfAXjx3wh8TT7RwNhGD8nSBQk
    // 1mb: QmZgFEZqAST7mRdvtVSdPdYXAyeNLmjtbcGmS69xFgXkQF
    // 10mb: QmZkY2fGt7vt436qtMczyE2XEkaMFeU67ZdapdtjgucLYz
    // 100mb: QmXLcNegoF36rkAw6VZykmTasRefa5Fy9sMo7u8YqtyFQr
    public Main() {

        for (int i = 0; i < nrOfThreads; i++) {
            RunnableIPFS R = new RunnableIPFS( "Thread-"+i,ipfsIp, "QmZgFEZqAST7mRdvtVSdPdYXAyeNLmjtbcGmS69xFgXkQF");
            R.start();
        }

/*        for (int i = 0; i < nrOfThreads; i++){
            RunnableCeph R = new RunnableCeph( "Thread-"+i,cephIp,"1mb.txt");
            R.start();
        }*/
    }
}

