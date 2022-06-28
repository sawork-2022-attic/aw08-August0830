package com.micropos.posdelivery;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
// @RequestMapping(value = "/delivery")
public class DeliveryController{
    public static final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    // @PostMapping(value = "/",consumes="text/plain")
    @RequestMapping(value = "/delivery/")//,consumes="text/plain"
    public ResponseEntity<Boolean> delivery(@RequestParam("orderInfo") String orderInfo){
        log.info(orderInfo);
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }
}
