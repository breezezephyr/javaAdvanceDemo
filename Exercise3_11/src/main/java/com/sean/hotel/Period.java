package com.sean.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: xiappeng.cai
 * Date: 14-4-28
 * Time: 下午5:02
 */
public class Period implements Comparable<Period> {
    protected final Logger logger = LoggerFactory.getLogger(Period.class);
    private Date startDate;
    private Date endDate;
    private Integer price;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Period() {
    }

    public Period(String startDate, String endDate) {

        try {
            this.startDate = format.parse(startDate);
            this.endDate = format.parse(endDate);
        } catch (ParseException e) {
            logger.warn("parse error", e);
        }
    }

    public Period(Date startDate, Date endDate, Integer price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int compareTo(Period o) {
        return startDate.compareTo(o.getStartDate());
    }

    @Override
    public String toString() {
        return format.format(startDate) + "~" + format.format(endDate);
    }
}
