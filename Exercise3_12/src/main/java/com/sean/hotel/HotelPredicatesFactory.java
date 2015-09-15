package com.sean.hotel;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.RangeSet;

import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-8 Time: 下午5:06
 */
public class HotelPredicatesFactory {
    static List<Predicate<Hotel>> hotelsPredicates;

    public static List<Predicate<Hotel>> init(Request request) {
        hotelsPredicates = Lists.newArrayList();
        hotelsPredicates.add(namePredicate(request.getName()));
        hotelsPredicates.add(addressPredicate(request.getAddress()));
        hotelsPredicates.add(brandPredicate(request.getBrand()));
        hotelsPredicates.add(pricePredicate(request.getPrice()));
        hotelsPredicates.add(starPredicate(request.getStar()));
        hotelsPredicates.add(areaPredicate(request.getArea()));
        return hotelsPredicates;
    }

    public static Predicate<Hotel> namePredicate(final String name) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                return (name.equals("") && name == null) || input.getName().contains(name);
            }
        };
    }

    public static Predicate<Hotel> areaPredicate(final String area) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                return (area.equals("") && area == null) || input.getAddress().contains(area);
            }
        };
    }

    public static Predicate<Hotel> addressPredicate(final String address) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                return (address.equals("") && address == null) || input.getName().contains(address);
            }
        };
    }

    public static Predicate<Hotel> starPredicate(final Iterable stars) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                if (stars.iterator().hasNext()) {
                    List star = (List) stars;
                    return star.contains(input.getProperties().get("star"));
                } else
                    return true;

            }
        };
    }

    public static Predicate<Hotel> brandPredicate(final Iterable brands) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                if (brands.iterator().hasNext()) {
                    List star = (List) brands;
                    return star.contains(input.getName());
                } else
                    return true;
            }
        };
    }

    public static Predicate<Hotel> pricePredicate(final RangeSet range) {
        return new Predicate<Hotel>() {
            @Override
            public boolean apply(Hotel input) {
                return range.isEmpty() || range.contains(input.getPrice());
            }
        };
    }
}
