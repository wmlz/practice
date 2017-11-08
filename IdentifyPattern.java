import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class IdentifyPattern {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\wmlz-note\\Desktop\\AAPL.csv";
        File file = new File(path);
        Scanner infile = new Scanner(file);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //用sdf把string转换成为date
        //用dialogbox接收用户输入的起止日期
        String startDate = JOptionPane.showInputDialog("Please enter the start date with the yyyy-MM-dd format");
        String endDate = JOptionPane.showInputDialog("Please enter the end date with the yyyy-MM-dd format");
        //把日期从str转换成Date
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        while (start.after(end)) {
            JOptionPane.showMessageDialog(null, "The date you entered is wrong, please enter again");
            //用dialogbox接收用户输入的起止日期
            startDate = JOptionPane.showInputDialog("Please enter the start date with the yyyy-MM-dd format");
            endDate = JOptionPane.showInputDialog("Please enter the end date with the yyyy-MM-dd format");
            //把日期从str转换成Date
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        }

        int lineNum = getFileLineNum(path);    //读取文件总行数
        int dayNum = lineNum - 1;             //总行数减去第一行表头得到天数
        DayData[] list = new DayData[dayNum];  //创建一个数组储存数据
        infile.nextLine();    //跳过第一行的表头
        //把文件数据存入数组
        for (int i = 0; i < dayNum; i++) {
            String str = infile.nextLine();
            String[] row = str.split(",");
            Date date = sdf.parse(row[0]);
            DayData abc = new DayData(date, Double.parseDouble(row[1]), Double.parseDouble(row[2]), Double.parseDouble(row[3]), Double.parseDouble(row[4]));
            list[i] = abc;
        }
        //遍历整个数组，找出开始日和结束日的index
        int startIndex = -1;  //初始化下标为-1
        int endIndex = -1;
        for (int i = 0; i < dayNum; i++) {
            if (list[i].getDate().compareTo(start) == 0)     //如果可以直接找到
                startIndex = i;
            else if (list[i].getDate().before(start) && list[i + 1].getDate().after(start)) //如果用户输入的日期当天不交易但是在文件日期范围之中
                startIndex = i + 1;
            if (list[i].getDate().compareTo(end) == 0)
                endIndex = i;
            else if (list[i].getDate().before(end) && list[i + 1].getDate().after(end))
                endIndex = i;
        }
        if (startIndex == -1 && endIndex == -1)
            JOptionPane.showMessageDialog(null, "The start and end date you input is wrong");
        else if (startIndex == -1)
            JOptionPane.showMessageDialog(null, "The start date you input is wrong");
        else if (endIndex == -1)
            JOptionPane.showMessageDialog(null, "The end date you input is wrong");

        System.out.println("Please enter a number to select the pattern you want to identify");
        System.out.println("1: Hammer");
        System.out.println("2: Three White Soldiers");
        System.out.println("3: Golden Cross days");
        Scanner input = new Scanner(System.in);
        int patterNum = input.nextInt();


        if (!(startIndex == -1 || endIndex == -1)) {
            //找锤子线
            if (patterNum == 1) {
                boolean hasHammer = false;
                for (int i = startIndex; i <= endIndex; i++) {
                    if (list[i].isHammer()) {
                        hasHammer = true;
                        System.out.println("Pattern Found:  " + sdf.format(list[i].getDate()));
                    }
                }
                if (!hasHammer)
                    System.out.println("Pattern NOT Found");
            }
            //找出三白兵
            if (patterNum == 2) {
                boolean hasTWS = false;
                for (int i = startIndex; i <= (endIndex - 2); i++) {
                    if (list[i].isYang() && list[i + 1].isYang() && list[i + 2].isYang())  //判断三连阳
                        if (list[i + 1].getClose() >= list[i].getClose() && list[i + 1].getClose() <= list[i + 2].getClose())   //每天的收盘价高于前一天的收盘价
                            //判断开盘价的前一天实体的上50%
                            if (list[i + 1].getOpen() >= 0.5 * (list[i].getOpen() + list[i].getClose()) && list[i + 1].getOpen() <= list[i].getClose() && list[i + 2].getOpen() >= 0.5 * (list[i + 1].getOpen() + list[i + 1].getClose()) && list[i + 2].getOpen() <= list[i + 1].getClose())
                            //每天的最高价接近收盘价（可以删除）
                            //        if(list[i].getHigh()/list[i].getClose()<=0.2 && list[i+1].getHigh()/list[i+1].getClose()<=1.2 && list[i+2].getHigh()/list[i+2].getClose()<=1.2)
                            {
                                hasTWS = true;
                                System.out.println("Pattern Found:  " + sdf.format(list[i + 2].getDate()));
                            }
                }
                if (!hasTWS)
                    System.out.println("Pattern NOT Found");
            }
            /*找出金叉
            if(patterNum==3){

            }
            */
        }
        infile.close();
        input.close();
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
