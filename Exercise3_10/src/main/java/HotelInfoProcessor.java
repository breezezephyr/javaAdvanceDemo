import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.io.Closer;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入：给定一个hotelinfo文件，文件格式如下：
 * shanghai_city_7208 上海全季酒店淮海路店
 * shanghai_city_14744 锦江之星上海金山城市沙滩店
 * jinan_2794 章丘市大众旅馆
 * carmel_ca_5 Carmel River Inn
 * 要求如下：
 * 1. 输出一个文件，和hotelinfo格式一样，但是按照酒店代号进行降序排序
 * 2. 输出一个文件，两列，第一列是城市代号，第二列是这个城市下的酒店数，但是按照酒店数进行降序排序
 * Author: xiappeng.cai
 * Date: 14-4-25
 * Time: 上午11:03
 */
public class HotelInfoProcessor {
    private Map<String, Integer> numOrder;  //按照酒店代号进行降序排序
    private Multimap<String, String> localOrder; //按照城市下的酒店数进行降序排序
    Map<String, String> hotelInfo;
    private Closer closer = Closer.create();
    protected final static Logger logger = LoggerFactory.getLogger(HotelInfoProcessor.class);

    /**
     * 按行读取hotelInfo.txt
     *
     * @param filename
     * @return
     * @throws Exception
     */

    public List<String> getContent(String filename) throws Exception {
        if (filename == null)
            throw new FileNotFoundException();
        URL url = Resources.getResource(filename);
        return Resources.readLines(url, Charset.forName("UTF-8"));
    }

    /**
     * 将需要排序的数据放入到各自的Collection容器中
     *
     * @param content
     */
    public void getOrderMaps(List<String> content) {
        hotelInfo = Maps.newHashMap();
        numOrder = Maps.newHashMap();
        localOrder = HashMultimap.create();
        Pattern HotelId = Pattern.compile("(\\w+_\\w+)_(\\d+)");

        for (String temp : content) {
            String[] str = temp.split("\t");
            hotelInfo.put(str[0].trim(), str[1].trim());
        }
        Set<String> hotelIdentifier = hotelInfo.keySet();
        for (String hotelId : hotelIdentifier) {
            Matcher idMatcher = HotelId.matcher(hotelId);
            if (idMatcher.find()) {
                localOrder.put(idMatcher.group(1), hotelId);
                numOrder.put(hotelId, Integer.parseInt(idMatcher.group(2)));
            }
        }
    }

    /**
     * 输出按照酒店ID排序后的文件
     *
     * @throws Exception
     */
    public void IdOrderOutput() throws Exception {

        List<Map.Entry<String, Integer>> orderList = new ArrayList<Map.Entry<String, Integer>>(numOrder.entrySet());
        Collections.sort(orderList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        OutputStreamWriter streamWriter;
        try {
            streamWriter = closer.register(new OutputStreamWriter(
              new FileOutputStream("result.txt"), "UTF-8"));
            for (Map.Entry<String, Integer> entry : orderList) {
                String line = entry.getKey() + "\t" + hotelInfo.get(entry.getKey());
                streamWriter.write(line + "\n");
            }
        } finally {
            closer.close();
        }
    }

    /**
     * 输出按照地区酒店数排序后的文件
     *
     * @throws Exception
     */
    public void LocalOrderOut() throws Exception {
        Set<String> locals = localOrder.keySet();
        Map<String, Integer> localHotelNum = Maps.newHashMap();
        for (String local : locals) {
            Collection hotelIds = localOrder.get(local);
            localHotelNum.put(local, hotelIds.size());
        }
        List<Map.Entry<String, Integer>> localList = new ArrayList<Map.Entry<String, Integer>>(localHotelNum.entrySet());
        Collections.sort(localList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        OutputStreamWriter streamWriter;
        try {
            streamWriter = closer.register(new OutputStreamWriter(
              new FileOutputStream("LocalResult.txt"), "UTF-8"));
            for (Map.Entry<String, Integer> entry : localList) {
                String line = entry.getKey() + "\t" + entry.getValue();
                streamWriter.write(line + "\n");
            }
        } finally {
            closer.close();
        }
    }


    public static void main(String[] args) {
        HotelInfoProcessor hotelInfoProcessor = new HotelInfoProcessor();
        try {
            List<String> content = hotelInfoProcessor.getContent("hotelinfo.txt");
            hotelInfoProcessor.getOrderMaps(content);
            hotelInfoProcessor.IdOrderOutput();
            hotelInfoProcessor.LocalOrderOut();
        } catch (Exception e) {
            logger.warn("File IO Exception", e);
        }


    }
}
