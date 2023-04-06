package br.com.logisticadbcconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViagemCriadaDTO {

    // USUARIO
    private String email;
    private String nome;

    // ROTA
    private String partidaRota;
    private String destinoRota;

    // VIAGEM
    private String inicioViagem;
    private String fimViagem;
}
