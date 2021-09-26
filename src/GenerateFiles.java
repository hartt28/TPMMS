import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateFiles {

    Set<String> set = new HashSet<>();
    Random rand=new Random();
    public String[] fname = {"Lenox", "JiaoYang", "MasiWei", "Shiri", "harry", "sware", "smile", "Good", "Gaga", "Decorte"};
    String[] lname = {"Chen", "Li", "Hello", "Dave", "Soni", "Cole", "Singh", "Gordon", "Makki", "Lady"};
    String[] items = {"Heat pump, model X2000", "laptop", "phone", "keyboard", "camera", "ipad", "plump", "pen", "cup", "printer"};
    String[] dates = {"2020-12-21", "2012-12-09", "2009-09-09", "2020-08-29", "2021-01-01", "2002-12-31", "2020-01-13", "2019-09-23", "2020-04-30", "2012-12-24", "2013-12-12"};
    //id and sin
    public String generateNum(int len) {

        StringBuilder cardNnumer= new StringBuilder();
        for(int a=0;a<len;a++){
            if (a == 0) {
                int cur = rand.nextInt(10);
                while (cur == 0) {
                    cur = rand.nextInt(10);
                }
                cardNnumer.append(cur);
            } else {
                cardNnumer.append(rand.nextInt(10)); //生成6位数字
            }
        }
        String res = cardNnumer.toString();
        if (set.contains(res)) {
            return generateNum(len);
        }
        return res;
    }

    public String generateName() {
        StringBuilder sb = new StringBuilder();
        sb.append(fname[rand.nextInt(10)]);
        sb.append(" ");
        sb.append(lname[rand.nextInt(10)]);
        for (int i = sb.length(); i < 30; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public int generateGender() {
        return rand.nextInt(2);
    }

    public String generateItem() {
        StringBuilder sb = new StringBuilder();
        sb.append(items[rand.nextInt(10)]);
        for (int i = sb.length(); i < 45; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public String generateQuantity() {

        StringBuilder sb = new StringBuilder();
        int x = rand.nextInt(6);
        int ran =  x == 0 ? 1 : x;
        int rest = 7 - ran;
        for(int i = 0; i < rest; i++) {
            sb.append(" ");
        }
        for (int i = 0; i < ran; i++) {
            if (i == 0) {
                int cur = rand.nextInt(10);
                while (cur == 0) {
                    cur = rand.nextInt(10);
                }
                sb.append(cur);
            } else {
                sb.append(rand.nextInt(10));
            }
        }
        return sb.toString();
    }

    public String getDate() {
        return dates[rand.nextInt(11)];
    }
}
