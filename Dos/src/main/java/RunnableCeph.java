import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.util.StringUtils;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

class RunnableCeph implements Runnable {
    private Thread t;
    private String threadName;
    private String fileString;
    String endpoint;
    private String XaccessKey = "WDXEVFR67JNILZPJW4E2";
    private String XsecretKey = "zHPebmlqdMBG1UmKcUoJNcpVYRMcU52EZCKC17TF";
    private AmazonS3 conn;
    private List<Bucket> buckets;
    public RunnableCeph(String name, String inputEndpoint,String  inputFileString) {
        threadName = name;
        fileString = inputFileString;
        endpoint = inputEndpoint;
        System.out.println("Creating " +  threadName );


    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            AWSCredentials credentials = new BasicAWSCredentials(XaccessKey, XsecretKey);
            ClientConfiguration asd = new ClientConfiguration();
            asd.setConnectionTimeout(500);
            conn = AmazonS3ClientBuilder.standard().withCredentials(
                    new AWSStaticCredentialsProvider(credentials)).
                    withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endpoint, "EU")).withClientConfiguration(asd).
                    build();
            try {
                buckets = conn.listBuckets();
                for (Bucket bucket : buckets) {
                    System.out.println(bucket.getName() + "\t" +
                            StringUtils.fromDate(bucket.getCreationDate()));
                }
                while (true) {
                    File file = new File("asd"+threadName+".txt");
                    if(buckets != null ) {
                        System.out.println("ASD");
                        conn.getObject(new GetObjectRequest(buckets.get(0).getName(), fileString), file);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
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
