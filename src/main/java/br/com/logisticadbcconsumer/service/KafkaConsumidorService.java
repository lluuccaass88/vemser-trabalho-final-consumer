package br.com.logisticadbcconsumer.service;

import br.com.logisticadbcconsumer.dto.PossiveisClientesDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumidorService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"0"})},
            containerFactory = "listenerContainerFactory"
    )
    public void consumeEmailPossiveisClientes(@Payload String mensagem,
                                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition)
            throws JsonProcessingException {
        try {
            PossiveisClientesDTO possiveisClientesDTO = objectMapper.readValue(mensagem, PossiveisClientesDTO.class);

            emailService.enviarEmailPossivelCliente(possiveisClientesDTO);

            log.info("Nome: {} Email: {} ", possiveisClientesDTO.getNome(), possiveisClientesDTO.getEmail());
            log.info("consumi: " + mensagem);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Error consumeEmailPossiveisClientes");
        }
    }
}
