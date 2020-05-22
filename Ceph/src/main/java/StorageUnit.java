import java.util.ArrayList;

public class StorageUnit {
    private  ArrayList<ArrayList<Double>> writeTestData;
    private ArrayList<Double> avgWriteTestData;
    private  ArrayList<ArrayList<Double>> downloadTestData;
    private ArrayList<Double> avgDownloadTestData;

    public StorageUnit(){
        writeTestData = new ArrayList<>();
        avgWriteTestData = new ArrayList<>();
        downloadTestData = new ArrayList<>();
        avgDownloadTestData = new ArrayList<>();
    }
    public void saveWriteData(ArrayList<Double> arrayList, double avgTime){
        writeTestData.add(arrayList);
        avgWriteTestData.add(avgTime);
    }
    public void saveDownloadData(ArrayList<Double> arrayList, double avgTime){
        downloadTestData.add(arrayList);
        avgDownloadTestData.add(avgTime);
    }

    public ArrayList<ArrayList<Double>> getWriteTestData(){
        return writeTestData;
    }
    public ArrayList<Double> getAvgWriteTestData(){
        return avgWriteTestData;
    }

    public ArrayList<ArrayList<Double>> getDownloadTestData(){
        return downloadTestData;
    }
    public ArrayList<Double> getAvgDownloadTestData(){
        return avgDownloadTestData;
    }
}
