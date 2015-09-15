package com.sean.calendar;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 题目要求：输入年份，如2014，打印出该年万年历，以及标识出当天日期。类似于linux下的cal -y结果。
 *
 * @author xiaopeng.cai
 * @date:2014.4.21
 */
public class PrintCalender {
    static GregorianCalendar gregorianCal = new GregorianCalendar();
    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /**
     * 获得当前月份1号星期数
     *
     * @param year
     * @param month
     * @return
     */
    public int getFirstDayOfMonth(int year, int month) {
        gregorianCal.set(year, month, 1);
        int first_day_of_Month = (int) (gregorianCal.get(Calendar.DAY_OF_WEEK));
        return first_day_of_Month;
    }

    /**
     * 获取当前月份的最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public int getMaxDaysOfMonth(int year, int month) {
        gregorianCal.set(year, month, 1);
        int maxDay = gregorianCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 打印全年的万年历
     *
     * @param year
     */
    public void printMonths(int year) {
        //获取当前日期
        Calendar currentDate = Calendar.getInstance();
        boolean isCurrentYear = (currentDate.get(Calendar.YEAR) == year);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int day_of_week;
        for (int m = 0; m <= 11; m++) {
            boolean isCurrentMonth = (month == m);
            System.out.println(months[m]);
            System.out.println("Sun   Mon   Tus   Wed   Thr   Fri   Sat");
            day_of_week = getFirstDayOfMonth(year, m);
            for (int j = 1; j < day_of_week; j++) {
                System.out.print("      ");
            }
            int maxDaysOfMonth = getMaxDaysOfMonth(year, m);
            for (int d = 1; d <= maxDaysOfMonth; d++) {
                boolean isCurrentDay = (day == d);
                if (d < 10) {
                    if (isCurrentYear && isCurrentMonth && isCurrentDay)
                        System.out.print("|" + "0" + d + "|  "); //标识出当天日期
                    else
                        System.out.print(" " + "0" + d + "   ");
                } else {
                    if (isCurrentYear && isCurrentMonth && isCurrentDay)
                        System.out.print("|" + d + "|  ");
                    else
                        System.out.print(" " + d + "   ");
                }
                day_of_week = (day_of_week + 1) % 7;     // 判断下一天的day_of_week
                if (day_of_week == 1)                // Java Calender 定义Sunday Day_of_week = 1,在Sunday换行
                    System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.print("Input Year :");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int year = Integer.parseInt(str);
        System.out.println("\n             Calendar " + year + "           ");
        new PrintCalender().printMonths(year);
    }
}
