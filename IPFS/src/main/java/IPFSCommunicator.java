import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class IPFSCommunicator {
    private String ip;
    private String fileFolder = "Filelocation";
    private StorageUnit storageUnit;
    private int iterations;

    public IPFSCommunicator(String inputIP, String inputPort, StorageUnit inputStorageUnit, int inputIterations) {
        ip = inputIP + ":" + inputPort;
        storageUnit = inputStorageUnit;
        iterations = inputIterations;
    }

    public void write(String fileString) {
        System.out.println("Write File");
        String fileLocation = fileFolder + fileString;
        File file = new File(fileLocation);
        String url = "http://" + ip + "/api/v0/add";
        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(file))
                .build();

        HttpPost request = new HttpPost(url);
        request.setEntity(entity);


        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download(String fileHash) {
        System.out.println("Download File");
        String url = "http://" + ip + "/api/v0/cat?arg=" + fileHash;
        HttpEntity entity = MultipartEntityBuilder.create()
                .build();

        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        HttpClient client = HttpClientBuilder.create().build();
        try {
            long startTime = System.nanoTime();
            HttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println((double) timeElapsed / 1000000 + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadTest(String fileHash) {
        System.out.println("Download Files");
        ArrayList<Double> downloadList = new ArrayList<>();
        long sumTime = 0;
        String url = "http://" + ip + "/api/v0/cat?arg=" + fileHash;
        HttpEntity entity = MultipartEntityBuilder.create()
                .build();

        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        HttpClient client = HttpClientBuilder.create().build();
        try {
            long startTime = System.nanoTime();
            HttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println((double) timeElapsed / 1000000 + " ms");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < iterations; i++) {
            try {
                long startTime = System.nanoTime();
                HttpResponse response = client.execute(request);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isReader);
                StringBuilder sb = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                downloadList.add((double) timeElapsed / 1000000);
                System.out.println((double) timeElapsed / 1000000 + " ms");
                sumTime += timeElapsed;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        double avgTime = sumTime / iterations;
        storageUnit.saveDownloadData(downloadList, avgTime);
        System.out.println("Average Time: " + avgTime / 1000000);

    }

    public void writeTest(String fileSize) {
        System.out.println("Write File");
        String url = "http://" + ip + "/api/v0/add";

        ArrayList<Double> writeList = new ArrayList<>();
        long sumTime = 0;

        try {
            for (int i = 0; i < iterations; i++) {
                String[] args = new String[]{"/bin/bash", "-c", "base64 /dev/urandom | head -c " + fileSize + " > file.txt"};
                Process process = new ProcessBuilder(args).start();
                CompletableFuture<Process> processCompletableFuture = process.onExit();
                processCompletableFuture.join();
                process.destroy();
                byte[] bytes;
                File file = new File("file.txt");
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    bytes = fileInputStream.readAllBytes();

                }
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addPart("file", new ByteArrayBody(bytes, i + file.getName()))
                        .build();

                HttpPost request = new HttpPost(url);
                request.setEntity(entity);

                HttpClient client = HttpClientBuilder.create().build();
                long startTime = System.nanoTime();
                HttpResponse response = client.execute(request);
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                System.out.println((double) timeElapsed / 1000000 + " ms");
                writeList.add((double) timeElapsed / 1000000);
                sumTime += timeElapsed;

                InputStream inputStream = response.getEntity().getContent();
                InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }

                file.delete();
            }

            double avgTime = sumTime / iterations;
            storageUnit.saveWriteData(writeList, avgTime);
            System.out.println("Average Time: " + avgTime / 1000000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
