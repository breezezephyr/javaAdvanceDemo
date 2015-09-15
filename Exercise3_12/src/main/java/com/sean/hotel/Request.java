package com.sean.hotel;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.*;

import java.util.*;

/**
 * 构造参数类，用于存放request字符串中的数据 Author: xiappeng.cai Date: 14-4-29 Time: 下午1:28
 */
public class Request {
    private String city;
    private String name;
    private String address;
    // 过滤参数中，酒店的牌子可以是多个
    private Iterable<String> brand;
    // 过滤参数中，可以选择不同的星级，对应前端的multiCheckbox
    private Iterable<String> star;
    private String area;
    private String sort;
    // price是一个区间
    private RangeSet price;
    private String order;
    // page也是一个区间
    private Range<Integer> page;

    public Request() {
    }

    public Request(String requestBody) {

        String[] parameters = requestBody.split("&");
        // 将参数切成字符串后放入map中
        Map<String, String> requestParameter = Maps.newHashMap();
        requestParameter = Splitter.on("&").withKeyValueSeparator("=").split(requestBody);
        // 将Map转化成RequestBody
        this.city = requestParameter.get("city");
        this.name = requestParameter.get("name");
        this.address = requestParameter.get("address");
        if (requestParameter.get("brand") != null) {
            String parameter = requestParameter.get("brand");
            this.brand = Splitter.on(",").split(parameter);
        }
        if (requestParameter.get("star") != null) {
            String parameter = requestParameter.get("star");
            this.star = Splitter.on(",").split(parameter);
        }
        if (requestParameter.get("price") != null) {
            RangeSet priceRanges = TreeRangeSet.create();
            String parameter = requestParameter.get("price");
            if (parameter.indexOf(",") > -1) {
                for (String priceArea : parameter.split(","))
                    priceRanges.add(parseToRange(priceArea.split("-")));
            } else
                priceRanges.add(parseToRange(parameter.split("-")));
            this.price = priceRanges;
        }
        this.area = requestParameter.get("area");
        // 当参数缺失时，使用默认参数
        if (requestParameter.get("sort") != null)
            this.sort = requestParameter.get("sort");
        else
            this.sort = "price";

        if (requestParameter.get("order") != null)
            this.order = requestParameter.get("order");
        else
            this.order = "asc";

        if (requestParameter.get("page") != null && !requestParameter.get("page").equals(""))
            this.page = parseToRange(requestParameter.get("page").split("-"));
        else
            this.page = Range.closed(0, 20);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(RangeSet price) {
        this.price = price;
    }

    public RangeSet getPrice() {

        return price;
    }

    public Range getPage() {
        return page;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Request{" + "city='" + city + '\'' + ", name='" + name + '\'' + ", address='" + address + '\''
                + ", brand=" + brand + ", star=" + star + ", area='" + area + '\'' + ", sort='" + sort + '\''
                + ", price=" + price + ", order='" + order + '\'' + ", page=" + page + '}';
    }

    public void setBrand(Iterable<String> brand) {
        this.brand = brand;
    }

    public void setStar(Iterable<String> star) {
        this.star = star;
    }

    public void setPage(Range<Integer> page) {
        this.page = page;
    }

    public Iterable<String> getBrand() {
        return brand;
    }

    public Iterable<String> getStar() {
        return star;
    }

    // 将传入参数String[] 转换为 Range
    private Range parseToRange(String[] strings) {
        Preconditions.checkNotNull(strings);
        Integer[] result = { 0, 0 };
        result[0] = Integer.parseInt(strings[0]);
        result[1] = Integer.parseInt(strings[1]);
        if (result[0] > result[1]) {
            int temp = result[0];
            result[0] = result[1];
            result[1] = temp;
        }
        Range<Integer> range = Range.closed(result[0], result[1]);
        return range;
    }
}
