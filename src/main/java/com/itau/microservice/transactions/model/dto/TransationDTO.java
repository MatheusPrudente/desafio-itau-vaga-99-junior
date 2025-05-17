package com.itau.microservice.transactions.model.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@NotNull(message = "Transação não informada")
public class TransationDTO {

  @NotNull(message = "O número não pode ser nulo.")
  @Min(value = 0, message = "A transação não pode ter valor negativo")
  private Double valor;

  @NotNull(message = "A data não pode ser nula.")
  @Past(message = "A transação não pode acontecer em datas futuras")
  private OffsetDateTime dataHora;

  public TransationDTO() {
  }

  public TransationDTO(Double valor, OffsetDateTime dataHora) {
    this.valor = valor;
    this.dataHora = dataHora;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public OffsetDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(OffsetDateTime dataHora) {
    this.dataHora = dataHora;
  }

}
