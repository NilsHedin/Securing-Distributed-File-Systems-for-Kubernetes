
//
public class Main {
    private String ip = "255.255.255.255";
    public static void main(String[] args) {
        Main main = new Main();

    }

    // 10kb: Qma3GqBby1H7BbnfmK3iFLVtxefx124E2nW1jw7Nb1Pzdc
    // 1mb: QmZgFEZqAST7mRdvtVSdPdYXAyeNLmjtbcGmS69xFgXkQF
    // 10mb: QmZkY2fGt7vt436qtMczyE2XEkaMFeU67ZdapdtjgucLYz
    // 100mb: QmXLcNegoF36rkAw6VZykmTasRefa5Fy9sMo7u8YqtyFQr
    public Main() {
        StorageUnit storageUnit = new StorageUnit();
        DataAnalyzer dataAnalyzer = new DataAnalyzer(storageUnit);
        IPFSCommunicator ipfsCommunicator = new IPFSCommunicator(ip, "9095", storageUnit, 100);
        ipfsCommunicator.downloadTest("QmZkY2fGt7vt436qtMczyE2XEkaMFeU67ZdapdtjgucLYz");
        ipfsCommunicator.writeTest("10000000");
        dataAnalyzer.printEverything("Results.json");
    }

}

