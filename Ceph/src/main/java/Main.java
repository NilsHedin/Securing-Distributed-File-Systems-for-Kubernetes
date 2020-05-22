
import java.io.*;
import java.util.Scanner;


public class Main {
    public static Main main;
    private String accessKey = "31TIWBVOD8PUV6G925RU";
    private String secretKey = "Si5yD2B4vmOR5EgdGMp7NnAbhBlohyjjAZOO9EBh";
    private String endpoint = "http://255.255.255.255:80";
    private CephCommunicator cephCommunicator;
    private int outerIterations = 10;
    public static void main(String[] args)  {
        main = new Main();

    }
    public Main(){

        StorageUnit storageUnit = new StorageUnit();
        DataAnalyzer dataAnalyzer = new DataAnalyzer(storageUnit);
        cephCommunicator = new CephCommunicator(endpoint,accessKey,secretKey,storageUnit,10);
        writeTest("10mb.txt");
        downloadTest("10mb.txt", "file.txt");
        dataAnalyzer.printEverything("Result.json");

    }


    private void writeTest(String file){
        for (int i = 0; i < outerIterations; i++) {
            try {
                cephCommunicator.writeFiles(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadTest(String file, String fileName){
        for (int i = 0; i < outerIterations; i++) {
            try {
                cephCommunicator.downloadFiles(file,fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}