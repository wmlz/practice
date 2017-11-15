import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class IS5311_Group_06 {
    public static void main(String[] args) throws ParseException,FileNotFoundException{
        String path = "C:\\Users\\wmlz-note\\Desktop\\AAPL.csv";
        File file = new File(path);
        Scanner infile = new Scanner(file);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //use sdf to parse String to Date
        //use dialogbox to receive the start date and end date from user
        String startDate = JOptionPane.showInputDialog("Please enter the start date with the yyyy-MM-dd format");
        String endDate = JOptionPane.showInputDialog("Please enter the end date with the yyyy-MM-dd format");
        //parse String to Date
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        while (start.after(end)) {
            JOptionPane.showMessageDialog(null, "The date you entered is wrong, please enter again");
            //use dialogbox to receive the start date and end date from user
            startDate = JOptionPane.showInputDialog("Please enter the start date with the yyyy-MM-dd format");
            endDate = JOptionPane.showInputDialog("Please enter the end date with the yyyy-MM-dd format");
            //parse String to Date
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        }

        int lineNum = 0;
        try {
            lineNum = getFileLineNum(path);    //read the total line number of the file
        } catch (FileNotFoundException e) {
            System.out.println("The file can NOT found");
        }

        int dayNum = lineNum - 1;             //the line number -1 is the total day number
        DayData[] list = new DayData[dayNum];  //create a array to store the data
        infile.nextLine();    //skip the first line of the file
        //store the data to the array
        for (int i = 0; i < list.length; i++) {
            String str = infile.nextLine();
            String[] row = str.split(",");
            Date date = sdf.parse(row[0]);
            DayData abc = new DayData(date, Double.parseDouble(row[1]), Double.parseDouble(row[2]), Double.parseDouble(row[3]), Double.parseDouble(row[4]));
            list[i] = abc;
        }
        //create two arrays to store the 20day average and 50day average
        DayData[] ma20 = new DayData[dayNum];
        DayData[] ma50 = new DayData[dayNum];
        for (int i = 19; i < ma20.length; i++) {
            double sum20 = 0;
            for (int j = i; j >= (i - 19); j--)
                sum20 += list[j].getClose();
            double average20 = sum20 / 20;
            DayData abc = new DayData(null, 0, 0, 0, average20);
            ma20[i] = abc;
        }

        for (int i = 49; i < ma50.length; i++) {
            double sum50 = 0;
            for (int j = i; j >= (i - 49); j--)
                sum50 += list[j].getClose();
            double average50 = sum50 / 50;
            DayData abc = new DayData(null, 0, 0, 0, average50);
            ma50[i] = abc;

        }


        //identify the index of the start date and end date
        int startIndex = -1;  //initialize the index
        int endIndex = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i].getDate().compareTo(start) == 0)     //if the date can be identified directly
                startIndex = i;
            if (list[i].getDate().compareTo(end) == 0)
                endIndex = i;
        }

        if (startIndex == -1) {
            for (int i = 0; i < dayNum - 1; i++) {
                if (list[i].getDate().before(start) && list[i + 1].getDate().after(start)) //if the date user input is in the file range but cannot be identified
                    startIndex = i + 1;
            }
        }

        if (endIndex == -1) {
            for (int i = 0; i < dayNum - 1; i++) {
                if (list[i].getDate().before(end) && list[i + 1].getDate().after(end))
                    endIndex = i;
            }
        }

        if (startIndex == -1 && endIndex == -1)
            JOptionPane.showMessageDialog(null, "The start and end date you input are out of the data, please run the program again.");
        else if (startIndex == -1)
            JOptionPane.showMessageDialog(null, "The start date you input is out of the data, please run the program again.");
        else if (endIndex == -1)
            JOptionPane.showMessageDialog(null, "The end date you input is out of the data, please run the program again.");

        if (!(startIndex == -1 || endIndex == -1)) {
            //user choose pattern number
            System.out.println("Please enter a number to select the pattern you want to identify");
            System.out.println("1: Hammer");
            System.out.println("2: Three White Soldiers");
            System.out.println("3: Golden Cross days");
            Scanner input = new Scanner(System.in);
            int patternNum = input.nextInt();

            while (!(patternNum == 1 || patternNum == 2 || patternNum == 3)) {
                System.out.println("The pattern number you entered is wrong, please enter again.");
                patternNum = input.nextInt();
            }
            //identify Hammer pattern
            if (patternNum == 1) {
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
            //identify Three While Soldiers pattern
            if (patternNum == 2) {
                boolean hasTWS = false;
                for (int i = startIndex; i <= (endIndex - 2); i++) {
                    if (list[i].isYang() && list[i + 1].isYang() && list[i + 2].isYang())  //judge whether is 3 successive rising day
                        if (list[i + 1].getClose() >= list[i].getClose() && list[i + 1].getClose() <= list[i + 2].getClose())   //everyday's close price is higher than the day before
                            //judge the whether the open price is in the upper 50% of the body of the day before
                            if (list[i + 1].getOpen() >= 0.5 * (list[i].getOpen() + list[i].getClose()) && list[i + 1].getOpen() <= list[i].getClose() && list[i + 2].getOpen() >= 0.5 * (list[i + 1].getOpen() + list[i + 1].getClose()) && list[i + 2].getOpen() <= list[i + 1].getClose())
                                //judge wheter the upper wick lenth is smaller than 50% of the body lenth
                                if ((list[i].getHigh() - list[i].getClose()) / (list[i].getClose() - list[i].getOpen()) <= 0.5 && (list[i + 1].getHigh() - list[i + 1].getClose()) / (list[i + 1].getClose() - list[i + 1].getOpen()) <= 0.5 && (list[i + 2].getHigh() - list[i + 2].getClose()) / (list[i + 2].getClose() - list[i + 2].getOpen()) <= 0.5) {
                                    hasTWS = true;
                                    System.out.println("Pattern Found:  " + sdf.format(list[i + 2].getDate()));
                                }
                }
                if (!hasTWS)
                    System.out.println("Pattern NOT Found");
            }
            //identify Golden Cross
            if (patternNum == 3) {
                boolean hasGoldenCross = false;
                if (startIndex < 49)
                    startIndex = 49;
                for (int i = startIndex; i <= endIndex; i++) {
                    if (ma20[i].getClose() - ma50[i].getClose() < 0 && ma20[i + 1].getClose() - ma50[i + 1].getClose() > 0) {
                        hasGoldenCross = true;
                        System.out.println("Pattern Found:  " + sdf.format(list[i + 1].getDate()));
                    }
                }
                if (!hasGoldenCross)
                    System.out.println("Pattern NOT Found");
            }
            input.close();
        }
        infile.close();
    }

    //a method to identify the total line number of a file
    private static int getFileLineNum(String str) throws FileNotFoundException {
        File file = new File(str);
        Scanner infile = new Scanner(file);
        int lineNum = 0;
        while (infile.hasNextLine()) {
            infile.nextLine();
            lineNum++;
        }
        infile.close();
        return lineNum;
    }
}
