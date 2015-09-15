import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai DateTime: 14-5-14 Time: 下午3:38
 */
public class IterableExample {
    public static void main(String[] args) {
        Iterable<DateTime> dates = caculate(2014);
        Iterable<DateTime> allFridays = Iterables.filter(dates, new Predicate<DateTime>() {
            @Override
            public boolean apply(DateTime input) {
                Calendar calendar = new GregorianCalendar();
                LocalDate startDate = LocalDate.fromCalendarFields(calendar);
                LocalDate date = startDate;
                return calendar.get((Calendar.DATE)) == Calendar.FRIDAY;
            }
        });
        Iterable<String> dateString = Iterables.transform(allFridays, new Function<DateTime, String>() {
            @Override
            public String apply(DateTime input) {
                return new SimpleDateFormat("yyyy-MM-dd").format(input);
            }
        });
    }

    /**
     * Iterable 有缓存， Iterator 遍历一次就释放了
     * 
     * @param i
     * @return
     */
    private static Iterable<DateTime> caculate(int i) {
        return new Iterable<DateTime>() {
            @Override
            public Iterator<DateTime> iterator() {
                return new Iterator<DateTime>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public DateTime next() {
                        return null;
                    }

                    @Override
                    public void remove() {

                    }
                };
            }
        };
    }
}
