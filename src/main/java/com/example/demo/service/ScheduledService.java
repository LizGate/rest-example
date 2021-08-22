package com.example.demo.service;

import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class ScheduledService {

    @Autowired
    private OrderRepository orderRepository;


    @Scheduled(fixedDelay = 5000)
    public void check(){
        orderRepository.updateRandomStatusOrder();
    }

    /*

    @Scheduled(cron = "0 0 0 * * *",zone = "Europe/Istanbul")
    public void zRaporu(){
        orderRepository.dailyProfitReports();
    }

    */
}
