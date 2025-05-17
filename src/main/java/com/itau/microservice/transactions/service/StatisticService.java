package com.itau.microservice.transactions.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.microservice.transactions.model.dto.StatisticDTO;
import com.itau.microservice.transactions.model.entity.StatisticModel;
import com.itau.microservice.transactions.model.entity.TransationModel;

@Service
public class StatisticService {

  @Autowired
  private TransationService transationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Logger logger;

  public ResponseEntity<StatisticDTO> getStatistics(Integer seconds) {
    logger.info("getStatistics called. seconds: {}", seconds);

    OffsetDateTime now = OffsetDateTime.now();
    logger.debug("Current timestamp: {}", now);

    List<TransationModel> transationModels = transationService.getAllTransations();
    logger.debug("Total number of transactions: {}", transationModels.size());

    StatisticDTO statisticDTO = new StatisticDTO(0, 0.0, 0.0, 0.0, 0.0);

    List<TransationModel> filteredTransactions = transationModels.stream()
        .filter(transation -> Duration.between(transation.getDataHora(), now).getSeconds() <= seconds)
        .collect(Collectors.toList());

    logger.debug("Number of filtered transactions (within {} seconds): {}", seconds, filteredTransactions.size());

    if (!filteredTransactions.isEmpty()) {
      logger.info("Calculating statistics for filtered transactions...");

      DoubleSummaryStatistics doubleSummaryStatistics = filteredTransactions.stream()
          .mapToDouble(transation -> transation.getValor())
          .summaryStatistics();

      logger.debug("Summary statistics: Count: {}, Sum: {}, Average: {}, Min: {}, Max: {}",
          doubleSummaryStatistics.getCount(),
          doubleSummaryStatistics.getSum(),
          doubleSummaryStatistics.getAverage(),
          doubleSummaryStatistics.getMin(),
          doubleSummaryStatistics.getMax());

      StatisticModel statisticModel = new StatisticModel(
          doubleSummaryStatistics.getCount(),
          doubleSummaryStatistics.getSum(),
          doubleSummaryStatistics.getAverage(),
          doubleSummaryStatistics.getMin(),
          doubleSummaryStatistics.getMax());

      statisticDTO = objectMapper.convertValue(statisticModel, StatisticDTO.class);
      logger.info("Statistics calculated successfully.");
    } else {
      logger.warn("No transactions found within the last {} seconds.", seconds);
    }

    return ResponseEntity.ok().body(statisticDTO);
  }
}
