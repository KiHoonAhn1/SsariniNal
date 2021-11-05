//package com.playssafy.playssafy.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.persistence.Cacheable;
//
//@RestController
//@RequestMapping("/redis")
//public class RedisController {
//    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
//
//    @GetMapping()
//    @Cacheable(value = "user")
//    public String get(@RequestParam(value = "id") String id){
//
//    }
//}
