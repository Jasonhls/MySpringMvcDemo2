package com.cn.rpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:27
 **/
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String select(List<String> repos) {
        int len = repos.size();
        Random random = new Random();
        return repos.get(random.nextInt(len));
    }
}
