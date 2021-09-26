import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class TPMMS {
    public static int fcount = 0;
    public static int lcount = 0;
    public static int sublistSize = 0;
    public  TreeMap<Integer, String> buffer = new TreeMap<>();
    boolean finish ;
    public  FileWriter fw1;
    public  int count=0;
    public double startTime2;

    {
        try {
            fw1 = new FileWriter("src/Output/output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PrintWriter pw1 = new PrintWriter(fw1);


    public void phase1(String path) throws Exception {

        FileReader folder = new FileReader(path);
        BufferedReader br = new BufferedReader(folder);
        //There are M buffers available
        //Divide the input file into chunks of M blocks each
        //get buffer size--how many lines to read    【
        sublistSize = (int) ((getAvailableMemory() / 110 / 9) * 1);
        //get file chuck
        //double startTime = (double) System.currentTimeMillis();
        //add real data into sublist
        //System.out.println("sublist or chunk size is " + sublistSize);
        List<String> sublist = new ArrayList<>();
        String line = null;
        //int count = 0;
        while ((line = br.readLine()) != null) {
            //每一行每一行读
            sublist.add(line);
            lcount++;
            //every chunck written into sublist
            if (sublist.size() == sublistSize) {
                //清空sublist
                fcount++;
                String addr = "src/Temp/temp" + fcount + ".txt";
                generateFiles(addr, sublist);
                sublist = new ArrayList<>();

            }
        }
        if (sublist.size() > 0) {
            //System.out.println("----------");
            fcount++;
            String addr = "src/Temp/temp" + fcount + ".txt";
            generateFiles(addr, sublist);
            sublist = new ArrayList<>();

        }

        br.close();
        folder.close();
        System.gc();

    }

    private void generateFiles(String address, List<String> sublist) throws Exception {

        FileWriter fw = new FileWriter(address);
        PrintWriter pw = new PrintWriter(fw);
        List<String> subliist = sublist.stream()
                .sorted(Comparator.comparing(s -> Integer.parseInt(s.substring(0, 8))))
                .collect(Collectors.toList());
        for (String str : subliist) {
            pw.println(str);
        }
        pw.close();
        fw.close();

    }

    public int getAvailableMemory() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        /// System.out.println((runtime.maxMemory() - usedMemory));
        return (int) (runtime.maxMemory() - usedMemory);
    }

    public void phase2() throws IOException, ParseException {
        Runtime.getRuntime().gc();
        startTime2 = (double) System.currentTimeMillis();
        //get all files from Temp
        File folder = new File("src/Temp/");
        System.out.println("Number of sublist created :" + fcount);
        System.out.println("Number of lines tgt read :" + lcount);
        sublistSize = (int) ((20 * 1024 * 1024 / 110 / 9) * 9);
        ArrayList<BufferedReader> listOfFiles = new ArrayList<>();
        //ArrayList<String> compaerBuffer = new ArrayList<>();//集齐九个写出去
        FileReader fr;

        //get all files in the same buffered reader
        for (int i = 0; i < fcount; i++) {
            //get all files in the same buffered reader
            fr = new FileReader(new File("./src/Temp/temp" + (i + 1) + ".txt"));
            listOfFiles.add(new BufferedReader(fr));
            //在里面放进去两个  比较
            //compaerBuffer.add(listOfFiles.get(i).readLine());
        }

        //build multiple file block
        List<List<String>> fileBlockList = new ArrayList<>();
        for(int i=0;i<fcount;i++)
        {
            ArrayList<String> myGroup = new ArrayList<>();
            fileBlockList.add(myGroup);
        }
        //先读九个  只是读第一个
        //for(int i = 0; i < sublistSize; i+=9){
            for(int j= 0 ; j<fcount;j++) {
                for (int k = 0; k < 9; k++) {
                    fileBlockList.get(j).add(listOfFiles.get(j).readLine());
                }
            }
             merge(fileBlockList,listOfFiles);
            }
       // }

        public void merge(List<List<String>> fileBlockList,ArrayList<BufferedReader> listOfFiles) throws ParseException, IOException {

            int minId = Integer.parseInt(fileBlockList.get(0).get(0).substring(0,8));
            //System.out.println("first min id is"+minId);
            int minIndex = 0;

            for(int i = 0; i < fcount ;i++){
                if(fileBlockList.get(i).get(0)!=null) {

                    int pointer = Integer.parseInt(fileBlockList.get(i).get(0).substring(0, 8));
                    //System.out.println("第一部分的 pointer "+i+" :"+pointer);
                    if(pointer<minId){
                        minId = pointer;
                        minIndex= i;
                    }
            }else{
                    break;
                }}
            String needed = fileBlockList.get(minIndex).get(0);
            buffer.put(minId, needed.substring(0,93)+"   "+needed.substring(93,100) +"   "+needed.substring(100)+ "  " + 1);
            fileBlockList.get(minIndex).remove(0);
            //System.out.println("-------1----------");
           // 先去第一个为最小

            boolean skip = false;
            while(finish==false){
            //随便选一个temp id，如果为null就选下一个
            //System.out.println("--------2---------");
            int tempId = 0;
            int  tempIndex = -1;
            while(tempId== 0) {
                for (int i = 0; i < fcount; i++) {

                    if (fileBlockList.get(i).size() == 0 || fileBlockList.get(i).get(0) == null) {
                        continue;
                    }
                    tempId = Integer.parseInt(fileBlockList.get(i).get(0).substring(0, 8));
                    tempIndex = i;
                }}

            //System.out.println("-------3----------");
            //然后sublist相比较
            for(int i = 0; i < fcount ;i++){
                //里面元素不是0就进去  要不然就空转
                if (fileBlockList.get(i).size() == 0 || fileBlockList.get(i).get(0) == null) {
                   continue;
                }else{

                    int pointer = Integer.parseInt(fileBlockList.get(i).get(0).substring(0, 8));

                    if(pointer == minId) {
                        genarateeSublist(pointer, fileBlockList.get(i).get(0));//id, newStr
                        fileBlockList.get(i).remove(0);
                        checkRemoveConditions(fileBlockList,listOfFiles,i);
                        skip = true;
                    }

                   // System.out.println("----------4-------");
                    if(pointer < tempId){
                        tempId = pointer;
                        tempIndex = i;
                    }
                }
            }
            //check
            if(skip == false) {
                if (buffer.size() == 9) {

                    clearBuffer();
                }
                if (tempId != 0 && tempIndex != -1) {
                    //System.out.println("---------5--------");
                    minId = tempId;//999999938
                    minIndex = tempIndex;
                    needed = fileBlockList.get(minIndex).get(0);
                    buffer.put(minId, needed.substring(0,93)+"   "+needed.substring(93,100) +"   "+needed.substring(100)+ "  " + 1);
                    fileBlockList.get(minIndex).remove(0);
                    checkRemoveConditions(fileBlockList, listOfFiles, minIndex);
                }
            }else{
                skip = false;
            }
        }
    }

    public void checkRemoveConditions(List<List<String>> fileBlockList,ArrayList<BufferedReader> listOfFiles, int index) throws IOException {
       //如果fileblock list那一行空了  就直接重新读

        if(fileBlockList.get(index).size() ==0){
            readNewblock(fileBlockList,listOfFiles,index);
        }

        for(List<String> fileblock:fileBlockList){
            if(fileblock .size()>0){
                finish = false;
                break;
            }
            finish = true;
            clearBuffer();
        }
    }
    public void readNewblock(List<List<String>> fileBlockList,ArrayList<BufferedReader> listOfFiles,int index) throws IOException {

        for(int i = 0; i < 9; i++){
            String str = listOfFiles.get(index).readLine();
            if(str!=null){
                fileBlockList.get(index).add(str);
            }else{
                break;
            }
        }
    }

    public void genarateeSublist(int id,String newStr) throws ParseException {

        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String oldStr = buffer.get(id);
        int position = oldStr.length();
        //System.out.println("old str is " +oldStr +"olStr 's length is " + oldStr.length());
        int noOfOrders = Integer.parseInt(oldStr.substring(position-1));
        noOfOrders = noOfOrders+1;
            int totalQuantity = Integer.parseInt((oldStr.substring(93, position-14)).trim()) + Integer.parseInt((newStr.substring(93, 100)).trim());

            Date dateNew = sdf.parse(newStr.substring(100, 110));
            Date dateOld = sdf.parse(oldStr.substring(position-13, position-3));

            String finalStr = "";
            boolean newS = false;
            if (dateNew.after(dateOld)) {
                finalStr = newStr;
                position = newStr.length();
                newS = true;
            } else {
                finalStr = oldStr;
            }

            int length = String.valueOf(totalQuantity).length();
            int neededSpace = 10 - length;
            String space = "";
            while (neededSpace > 0) {
                space = space + " ";
                neededSpace--;
            }
            if(newS == false) {
                buffer.replace(id, finalStr.substring(0, 93) + space + totalQuantity + "   " + finalStr.substring(position - 13, position - 3) + "  " + noOfOrders);
            }else{
                buffer.replace(id, finalStr.substring(0, 93) + space + totalQuantity + "   " + finalStr.substring(100,110) + "  " + noOfOrders);
            }

    }


    public void clearBuffer() throws IOException {

            for(String str: buffer.values()){
                pw1.println(str);

            }
             count++;

            //System.out.println("一个print结束！！！");
            buffer.clear();

            //output every element in the sublist
        if(finish==true){
            //System.out.println("");
            pw1.close();
            fw1.close();

            double endTime2 = (double) System.currentTimeMillis();
            //System.out.println("io that counted by myself is " + count);
            System.out.println("Processing time of Phase 2:" + (endTime2 - startTime2) / 1000);
            double io = Math.ceil(lcount /9) * 3+count;
            System.out.println("Total io is:" + io);
            purgeDirectory(new File("src/Temp"));
            System.out.println("temp file deleted");
            System.out.println("done");
            //System.out.println(count);
            System.exit(0);
        }
    }

         void purgeDirectory(File dir) {
            for (File file: dir.listFiles()) {
               if (file.isDirectory())
                  purgeDirectory(file);
               file.delete();
         }
         }
}




