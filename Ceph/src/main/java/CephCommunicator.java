import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CephCommunicator {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String fileFolder = "Filelocation";
    private StorageUnit storageUnit;
    private int iterations;
    private AmazonS3 conn;
    private List<Bucket> buckets;

    public CephCommunicator(String inputEndpoint, String inputAccessKey, String inputSecretKey, StorageUnit inputStorageUnit, int inputIterations) {
        endpoint = inputEndpoint;
        accessKey = inputAccessKey;
        secretKey = inputSecretKey;
        storageUnit = inputStorageUnit;
        iterations = inputIterations;
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        conn = AmazonS3ClientBuilder.standard().withCredentials(
                new AWSStaticCredentialsProvider(credentials)).
                withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(endpoint, "EU")).
                build();

        buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName() + "\t" +
                    StringUtils.fromDate(bucket.getCreationDate()));
        }
    }

    public void writeFiles(String fileString) throws IOException {
        System.out.println();
        System.out.println("Write File");
        ArrayList<Double> writeList = new ArrayList<>();
        String fileLocation = fileFolder+fileString;
        long sumTime = 0;

        for (int i = 0; i < iterations; i++) {
            ByteArrayInputStream input = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(fileLocation)));
            long startTime = System.nanoTime();
            conn.putObject(buckets.get(0).getName(), fileString, input, new ObjectMetadata());
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            writeList.add((double) timeElapsed / 1000000);
            System.out.println((double) timeElapsed / 1000000 + " ms");
            sumTime += timeElapsed;
        }
        double avgTime = sumTime / iterations;
        storageUnit.saveWriteData(writeList, avgTime);
        System.out.println("Average Time: "+avgTime/1000000);
    }


    public void downloadFiles(String fileString, String fileName) throws IOException {
        System.out.println();
        System.out.println("Download File");
        ArrayList<Double> downloadList = new ArrayList<>();
        long sumTime = 0;
        for (int i = 0; i < iterations; i++) {
            File file = new File(fileName);
            long startTime = System.nanoTime();
            conn.getObject(new GetObjectRequest(buckets.get(0).getName(), fileString),file);
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            downloadList.add((double) timeElapsed / 1000000);
            System.out.println((double) timeElapsed / 1000000 + " ms");
            sumTime += timeElapsed;
            file.delete();
        }
        double avgTime = sumTime / iterations;
        storageUnit.saveDownloadData(downloadList, avgTime);
        System.out.println("Average Time: "+avgTime/1000000);
    }

    public void writeFile(String fileString) throws IOException {
        System.out.println();
        System.out.println("Write File");
        String fileLocation = fileFolder+fileString;
        ByteArrayInputStream input = new ByteArrayInputStream(FileUtils.readFileToByteArray(
                new File(fileLocation)));
        long startTime = System.nanoTime();
        conn.putObject(buckets.get(0).getName(), fileString, input, new ObjectMetadata());
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println((double) timeElapsed / 1000000 + " ms");
    }

    public void downloadFile(String fileString, String fileName) throws IOException {
        System.out.println();
        System.out.println("Download File");
        File file = new File(fileName);
        long startTime = System.nanoTime();
        conn.getObject(
                new GetObjectRequest(buckets.get(0).getName(), fileString),
                new File(fileName)
        );
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println((double) timeElapsed / 1000000 + " ms");
    }


}
