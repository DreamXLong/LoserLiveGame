package com.xlong.loserlivegame;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by SLP on 2017/3/17.
 */

public class Arithmetic {
    // 放大倍数
    private static final int mulriple = 1000000;
    private String key;

    public String pay(List<HashMap<String, Double>> lists) {
        int lastScope = 0;
        // 洗牌，打乱奖品次序
        Collections.shuffle(lists);
        Map<String, int[]> prizeScopes = new HashMap<String, int[]>();
        for (Map<String, Double> list : lists) {
            Set<Map.Entry<String, Double>> prizeId = list.entrySet();
            for (Map.Entry<String, Double> m : prizeId) {
                key = m.getKey();
            }
            double value = list.get(key);
            // 划分区间
            int currentScope = lastScope + new BigDecimal(value).multiply(new BigDecimal(mulriple)).intValue();
            prizeScopes.put(key, new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }

        // 获取1-1000000之间的一个随机数
        int luckyNumber = new Random().nextInt(mulriple);
        String luckyPrizeId = null;
        // 查找随机数所在的区间
        if ((null != prizeScopes) && !prizeScopes.isEmpty()) {
            Set<Map.Entry<String, int[]>> entrySets = prizeScopes.entrySet();
            for (Map.Entry<String, int[]> m : entrySets) {
                String key = m.getKey();
                if (luckyNumber >= m.getValue()[0] && luckyNumber <= m.getValue()[1]) {
                    luckyPrizeId = key;
                    break;
                }
            }
        }

        return luckyPrizeId;
    }
}
