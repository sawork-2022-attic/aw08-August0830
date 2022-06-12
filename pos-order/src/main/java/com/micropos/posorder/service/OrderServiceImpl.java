package com.micropos.posorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private StreamBridge streamBridge;

    public static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
	public boolean placeOrder(String orderInfo){
        String[] info = orderInfo.split("\\|");
        int cnt=0;
        String addr = null;
        for(String str : info){
            if(str.startsWith("addr:")){
                addr = new StringBuilder(str.substring(4)).toString();
            }
            else
                cnt++;
        }
        String deliveryInfo = String.format("%d|%s",cnt,addr);
        log.info("place order {}", deliveryInfo);
        return streamBridge.send("deliver-order",deliveryInfo);
    }

    @Override
    public String generateOrder(String orderInfo)
    {
        String[] info = orderInfo.split("\\|");
        int cnt=0;
        String addr = null;
        for(String str : info){
            if(str.startsWith("addr:")){
                addr = new StringBuilder(str.substring(4)).toString();
            }
            else
                cnt++;
        }
        String deliveryInfo = String.format("%d|%s",cnt,addr);
        log.info("place order {}", deliveryInfo);
        return deliveryInfo;
    }
}
