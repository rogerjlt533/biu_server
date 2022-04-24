package com.zuosuo.treehole.tool;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressTool {

    public static Map<String, String> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
//        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        Map<String, String> row = new HashMap<>();
        while (m.find()) {
//            row = new LinkedHashMap<String, String>();
            province = m.group("province");
            if (province != null) {
                row.put("province",  province.trim());
            }
            city = m.group("city");
            if (city != null) {
                row.put("city",  city.trim());
            }
            county = m.group("county");
            if (county != null) {
                row.put("county",  county.trim());
            }
            town = m.group("town");
            if (town != null) {
                row.put("town",  town.trim());
            }
            village = m.group("village");
            if (village != null) {
                row.put("village",  village.trim());
            }
//            table.add(row);
        }
        return row;
    }
}
