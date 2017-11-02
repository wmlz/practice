import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class IdentifyPattern {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\wmlz-note\\Desktop\\BABA.csv";
        File file = new File(path);
        Scanner infile = new Scanner(file);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //用sdf把string转换成为date
        int lineNum = getFileLineNum(path);    //读取文件总行数
        int dayNum = lineNum - 1;             //总行数减去第一行表头得到天数
        DayData[] list = new DayData[dayNum];  //创建一个数组储存数据
        infile.nextLine();    //跳过第一行的表头
        //给数组赋值
        for (int i = 0; i < dayNum; i++) {
            String str = infile.nextLine();
            String[] row = str.split(",");
            Date date = sdf.parse(row[0]);
            DayData abc = new DayData(date, Double.parseDouble(row[1]), Double.parseDouble(row[2]), Double.parseDouble(row[3]), Double.parseDouble(row[4]));
            list[i] = abc;
        }
        //把所有股价上涨的日期打印出来
        for (int i = 0; i < dayNum; i++) {
            if (list[i].isYang())
                System.out.println(sdf.format(list[i].getDate()));
        }
        infile.close();
    }

    //写一个读取文件总行数的方法
    public static int getFileLineNum(String str) throws IOException {
        File file = new File(str);
        Scanner infile = new Scanner(file);
        int lineNum = 0;
        while (infile.hasNextLine()) {
            infile.nextLine();
            lineNum++;
        }
        return lineNum;
    }
}
