public class Main {
    public static void main(String [] args){

     String path = "src/input/T1.txt";
     TPMMS tpmms=new TPMMS();
        try {
            double startTime = (double) System.currentTimeMillis();
            tpmms.phase1(path);
            path = "src/input/T2.txt";
            tpmms.phase1(path);
            double endTime = (double) System.currentTimeMillis();
            System.out.println("Processing time of Phase 1:" + (endTime - startTime) / 1000);
            tpmms.phase2();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
