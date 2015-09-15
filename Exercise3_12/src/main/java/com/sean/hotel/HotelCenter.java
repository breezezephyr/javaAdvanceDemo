package com.sean.hotel;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-4-28 Time: 下午5:02
 */
public class HotelCenter {
    protected final Logger logger = LoggerFactory.getLogger(HotelCenter.class);

    /**
     * 读取paramter文件，获取request
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public Request getRequestBody(String filename) throws IOException {
        URL url = Resources.getResource(filename);
        String lines = Resources.toString(url, Charsets.UTF_8);
        return new Request(lines);
    }

    /**
     * 读取Hotel文件，获取全部的Hotel
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public List<Hotel> getAllHotel(String filename) throws IOException {
        List<Hotel> hotelList = Lists.newArrayList();
        URL url = Resources.getResource(filename);
        List<String> lines = Resources.readLines(url, Charsets.UTF_8);
        for (String line : lines) {
            Map<String, String> properties = Maps.newHashMap();
            String[] hotelInfo = line.split(" ");
            Hotel hotel = new Hotel(hotelInfo[0], hotelInfo[1], Integer.parseInt(hotelInfo[2]));
            properties.put("star", hotelInfo[3]);
            properties.put("city", hotelInfo[4]);
            hotel.setProperties(properties);

            hotelList.add(hotel);
        }
        return hotelList;
    }

    /**
     * 获取当前城市的所有hotel
     * 
     * @param city
     * @return
     */
    public List<Hotel> getHotelList(String city) {
        List<Hotel> hotelList = Lists.newArrayList();
        try {
            List<Hotel> hotels = getAllHotel("hotel");
            for (Hotel hotel : hotels) {
                String ci = hotel.getProperties().get("city");
                if (hotel.getProperties().get("city").equals(city)) {
                    hotelList.add(hotel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotelList;
    }

    /**
     * 根据Request body的内容过滤，排序，分页后返回要求的List<Hotel>
     * 
     * @param request
     * @param hotels
     * @return
     */
    public List<Hotel> searchHotel(Request request, List<Hotel> hotels) {
        Collection<Hotel> result = Lists.newArrayList();
        List<Predicate<Hotel>> predicates = HotelPredicatesFactory.init(request);
        for (Predicate predicate : predicates) {
            result = Collections2.filter(hotels, predicate);
        }
        List<Hotel> matchedHotels = new ArrayList<Hotel>(result);
        Collections.sort(matchedHotels, new OrderComparator(request.getSort(), request.getOrder()));
        Range<Integer> page = request.getPage();
        if (page.upperEndpoint().compareTo(matchedHotels.size()) < 0) {
            result = matchedHotels.subList(page.lowerEndpoint(), page.upperEndpoint());
        }
        return matchedHotels;
    }

    /**
     * 内部比较类，传入排序参数
     */
    private class OrderComparator implements Comparator<Hotel> {
        String order;
        String sort;

        public OrderComparator(String sort, String order) {
            this.sort = sort;
            this.order = order;

        }

        public int compare(Hotel o1, Hotel o2) {
            if (order.equals("asc")) {
                if (sort.equals("price"))
                    return o1.getPrice() - o2.getPrice();
                else
                    return o1.getProperties().get(order).compareTo(o1.getProperties().get(order));
            } else {
                if (sort.equals("price"))
                    return o2.getPrice() - o1.getPrice();
                else
                    return o2.getProperties().get(order).compareTo(o1.getProperties().get(order));
            }
        }
    }


    public static void main(String[] args) {
        HotelCenter hotelCenter = new HotelCenter();
        try {
            Request r = hotelCenter.getRequestBody("parameter");
            List<Hotel> hotels = hotelCenter.getHotelList(r.getCity());
            System.out.println(r);
            hotels = hotelCenter.searchHotel(r, hotels);
            System.out.println(hotels.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
