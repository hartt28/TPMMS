import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

public class Test {

    public static void main(String[] args) throws ParseException, FileNotFoundException {

        Runtime rt = Runtime.getRuntime();
        //System.out.println(rt.maxMemory());


        GenerateFiles gf = new GenerateFiles();
        PrintWriter pw = new PrintWriter(new File("src/input/T1.txt"));
        for (int i = 0; i < 500000; i++) {
            gen(gf, pw);
        }
        pw.close();

        gf.set.clear();
        PrintWriter pw2 = new PrintWriter(new File("src/input/T2.txt"));
        for (int i = 0; i < 1000000; i++) {
            gen(gf, pw2);
        }
        pw2.close();


    }

    private static void gen(GenerateFiles gf, PrintWriter pw2) {
        pw2.print(gf.generateNum(8));
        pw2.print(gf.generateName());
        pw2.print(gf.generateGender());
        pw2.print(gf.generateNum(9));
        pw2.print(gf.generateItem());
        pw2.print(gf.generateQuantity());
        pw2.println(gf.getDate());
    }
}
