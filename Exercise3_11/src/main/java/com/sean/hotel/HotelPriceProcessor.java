package com.sean.hotel;

import com.google.common.base.Charsets;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 以下格式 2013-10-01～2013-10-02 100表示2013-10-01入住，2013-10-02离店，入住一天的价格是￥100。
 * 现有文件中包含多行这样的日期价格段，请将其合并，合并的规则包括：
 * 1)价格相同，日期段相邻或者重叠的需要合并
 * 2)相同日期的价格已后面录入的为准
 * 例子1：
 * 2013-08-01～2013-08-31 300
 * 2013-08-25～2013-09-30 300
 * 合并后就是
 * 2013-08-01～2013-09-30 300
 * 例子2：
 * 2013-08-01～2013-12-31 300
 * 2013-10-01～2013-10-07 500
 * 合并之后就是
 * 2013-08-01～2013-10-01 300
 * 2013-10-01～2013-10-07 500
 * 2013-10-07～2013-12-31 300
 * Created with IntelliJ IDEA.
 * Author: xiappeng.cai
 * Date: 14-4-28
 * Time: 下午4:58
 */
public class HotelPriceProcessor {
    private static final Logger logger = LoggerFactory.getLogger(HotelPriceProcessor.class);

    /**
     * 读取data文件，获取不同时间的价位映射
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public Multimap<Integer, Period> getHotelPriceInfo(String filename) throws IOException {
        Multimap<Integer, Period> hotelPrice = TreeMultimap.create();
        URL url = Resources.getResource(filename);
        List<String> lines = Resources.readLines(url, Charsets.UTF_8);
        for (String line : lines) {
            if (line.trim() != "") {
                String[] priceOfDate = line.split(" ");
                int price = Integer.parseInt(priceOfDate[1]);
                String[] dates = priceOfDate[0].split("～");
                Period period = new Period(dates[0], dates[1]);
                hotelPrice.put(price, period);
            }
        }
        return hotelPrice;
    }

    /**
     * 对获取的数据进行处理
     *
     * @param hotelPrice
     * @return
     */
    public List<Period> priceOfDateProcess(Multimap<Integer, Period> hotelPrice) {
        if (hotelPrice != null) {
            if (hotelPrice.keySet().size() == 1) {
                for (int price : hotelPrice.keySet()) {   //如果价位没有波动，直接merge返回即可
                    List<Period> periods = new ArrayList<Period>(hotelPrice.get(price));
                    Collections.sort(periods);
                    return merge(periods, price);
                }
            } else {   //如果价位有波动，对同一价位的进行merge，不同价位的进行拆分
                List<Period> periods = splitDate(hotelPrice);
                return periods;
            }
        } else {
            logger.info("{}");
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 对传入的List进行merge
     *
     * @param periods
     * @param price
     * @return
     */
    private List<Period> merge(List<Period> periods, int price) {
        Period tempPeriod;
        List<Period> periodList = new ArrayList<Period>();
        tempPeriod = periods.get(0);
        tempPeriod.setPrice(price);
        for (Period period : periods) {
            if (tempPeriod.getEndDate().getTime() >= period.getStartDate().getTime()) {
                tempPeriod.setEndDate(period.getEndDate());
            } else {
                periodList.add(tempPeriod);
            }
        }
        periodList.add(tempPeriod);
        return periodList;
    }

    /**
     * 对传入的List merge后拆分
     *
     * @param hotelPrice
     * @return
     */
    private List<Period> splitDate(Multimap<Integer, Period> hotelPrice) {
        List<Period> tempList = new ArrayList<Period>();
        for (int price : hotelPrice.keySet()) {
            List<Period> periods = new ArrayList<Period>(hotelPrice.get(price));
            tempList.addAll(merge(periods, price));
        }
        return split(tempList);
    }

    /**
     * 递归调用拆分方法
     *
     * @param tempList
     * @return
     */
    private List<Period> split(List<Period> tempList) {
        Collections.sort(tempList); //按照开始时间排序
        for (int i = 1; i < tempList.size(); i++) {
            Period earliest = tempList.get(i - 1);
            Period after = tempList.get(i);
            if (!earliest.getPrice().equals(after.getPrice())) {
                if (after.getEndDate().getTime() < earliest.getEndDate().getTime()) { //后段时间段在前段时间中间，将前段时间拆分成两段
                    Period former = new Period(earliest.getStartDate(), after.getStartDate(), earliest.getPrice());
                    Period end = new Period(after.getEndDate(), earliest.getEndDate(), earliest.getPrice());
                    tempList.remove(earliest);
                    tempList.add(former);
                    tempList.add(end);
                    split(tempList);
                } else if (after.getEndDate().getTime() == earliest.getEndDate().getTime()) { //后段时间在前段时间endDate有部分重叠，截断前段时间即可
                    earliest.setEndDate(after.getStartDate());
                    tempList.remove(earliest);
                    tempList.add(earliest);
                    split(tempList);
                }
            } else {
                continue;
            }
        }
        return tempList;
    }

    public static void main(String[] args) {
        try {
            HotelPriceProcessor hotelPriceProcessor = new HotelPriceProcessor();
            Multimap<Integer, Period> hotelPrice = hotelPriceProcessor.getHotelPriceInfo("data.txt");
            List<Period> periodList = hotelPriceProcessor.priceOfDateProcess(hotelPrice);
            if (periodList.equals(Collections.EMPTY_LIST))
                logger.info("data was mess up, please check the input");
            else {
                for (Period p : periodList) {
                    System.out.println(p.toString() + " " + p.getPrice());
                }
            }
        } catch (IOException e) {
            logger.warn("input file read error", e);
        }


    }
}
