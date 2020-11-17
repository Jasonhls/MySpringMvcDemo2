package com.cn.rpc.loadbalance;

import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:27
 **/
public interface LoadBalance {
    String select(List<String> repos);
}
