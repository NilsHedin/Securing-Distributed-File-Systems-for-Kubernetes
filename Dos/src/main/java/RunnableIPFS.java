import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;

class RunnableIPFS implements Runnable {
    private Thread t;
    private String threadName;
    String ip;
    String fileHash;
    public RunnableIPFS( String name, String inputIp, String inputFileHash) {
        ip = inputIp;
        threadName = name;
        fileHash = inputFileHash;
        System.out.println("Creating " +  threadName );

    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            String url = "http://" + ip + "/api/v0/cat?arg=" + fileHash;
            HttpEntity entity = MultipartEntityBuilder.create()
                    .build();
            int CONNECTION_TIMEOUT_MS = 2 * 1000; // Timeout in millis.
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
                    .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                    .setSocketTimeout(CONNECTION_TIMEOUT_MS)
                    .build();

            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            request.setConfig(requestConfig);
            HttpClient client = HttpClientBuilder.create().build();
            while (true) {
                try {
                    System.out.println("Another one");
                    HttpResponse response = client.execute(request);
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamReader isReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isReader);
                    StringBuilder sb = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                    }
                }catch (Exception e){
                    System.out.println("Timeout");
                }
            }
        } catch (Exception e) {
            System.out.println("Thread " +  threadName + " interrupted.");
            e.printStackTrace();
        }


        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
