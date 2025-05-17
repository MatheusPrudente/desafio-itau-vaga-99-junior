package com.itau.microservice.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.itau.microservice.transactions.model.dto.StatisticDTO;
import com.itau.microservice.transactions.service.StatisticService;
import org.slf4j.Logger;

@Controller
@RequestMapping("/estatistica")
public class StatisticController {

  @Autowired
  private StatisticService statisticService;

  @Autowired
  private Logger logger;

  @GetMapping()
  public ResponseEntity<StatisticDTO> getStatistics(@RequestParam(defaultValue = "60") int seconds) {
    logger.info("GET /estatistica endpoint called.");

    //Integer seconds = 60;
    logger.debug("Calling StatisticService.getStatistics with seconds: {}", seconds);

    ResponseEntity<StatisticDTO> response = statisticService.getStatistics(seconds);

    if (response.getStatusCode().is2xxSuccessful()) {
      logger.info("Statistics successfully retrieved: {}", response.getBody());
    } else {
      logger.error("Failed to retrieve statistics. Response: {}", response.getStatusCode());
    }

    return response;
  }
}
