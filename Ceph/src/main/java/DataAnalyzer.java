import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataAnalyzer {
    private JSONObject jsonObject;
    private StorageUnit storageUnit;
    public DataAnalyzer(StorageUnit inputStorageUnit){

        jsonObject = new JSONObject();
        storageUnit = inputStorageUnit;
    }

    public void printEverything(String inputFileName){
        printAvgWriteRes();
        printAvgDownloadRes();

        String fileName = inputFileName;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            jsonObject.write(writer);
            writer.write("\n");
        } catch (Exception ex) {
            System.err.println("Couldn't write jsonResult\n"
                    + ex.getMessage());
        }
        System.out.println(jsonObject.toString());
    }


    public void printAvgWriteRes(){
        ArrayList<Double> avgReadRes = storageUnit.getAvgWriteTestData();
        JSONArray jsArray = new JSONArray();
        for (Double avg: avgReadRes) {
            jsArray.put(avg/1000000);
        }
        jsonObject.put("AvgWriteResult",jsArray);
        jsonObject.put("WriteResult",storageUnit.getWriteTestData());
    }

    public void printAvgDownloadRes(){
        ArrayList<Double> avgReadRes = storageUnit.getAvgDownloadTestData();
        JSONArray jsArray = new JSONArray();
        for (Double avg: avgReadRes) {
            jsArray.put(avg/1000000);
        }
        jsonObject.put("AvgDownloadResult",jsArray);
        jsonObject.put("DownloadResult",storageUnit.getDownloadTestData());
    }
}
