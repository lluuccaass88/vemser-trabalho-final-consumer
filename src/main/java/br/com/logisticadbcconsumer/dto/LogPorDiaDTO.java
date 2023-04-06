package br.com.logisticadbcconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogPorDiaDTO {
    private List<LogDTO> listDTo;
}
