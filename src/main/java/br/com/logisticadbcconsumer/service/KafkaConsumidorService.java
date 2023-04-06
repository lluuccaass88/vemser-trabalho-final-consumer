package br.com.logisticadbcconsumer.service;

import br.com.logisticadbcconsumer.dto.*;
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

            log.info("Partition " + partition +
                    "  | consumeEmailPossiveisClientes | Email: {} ", possiveisClientesDTO.getEmail());

        } catch (Exception e) {
            log.error("Error | consumeEmailPossiveisClientes");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"1"})},
            containerFactory = "listenerContainerFactory"
    )
    public void consumeEmailBoasVindas(@Payload String mensagem,
                                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition)
            throws JsonProcessingException {
        try {
            UsuarioBoasVindasDTO usuarioBoasVindasDTO = objectMapper.readValue(mensagem, UsuarioBoasVindasDTO.class);

            emailService.enviarEmailBoasVindas(usuarioBoasVindasDTO);

            log.info("Partition " + partition +
                    " | consumeEmailBoasVindas | Email: {} ", usuarioBoasVindasDTO.getEmail());

        } catch (Exception e) {
            log.error("Error | consumeEmailBoasVindas");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"2"})},
            containerFactory = "listenerContainerFactory"
    )
    public void consumeEmailRecuperarSenha(@Payload String mensagem,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition)
            throws JsonProcessingException {
        try {
            UsuarioRecuperaSenhaDTO usuarioRecuperaSenhaDTO = objectMapper.readValue(mensagem, UsuarioRecuperaSenhaDTO.class);

            emailService.enviarEmailRecuperarSenha(usuarioRecuperaSenhaDTO);

            log.info("Partition " + partition +
                    " | consumeEmailRecuperarSenha | Email: {} ", usuarioRecuperaSenhaDTO.getEmail());

        } catch (Exception e) {
            log.error("Error | consumeEmailRecuperarSenha");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"3"})},
            containerFactory = "listenerContainerFactory"
    )
    public void consumeEmailViagem(@Payload String mensagem,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition)
            throws JsonProcessingException {
        try {
            ViagemCriadaDTO viagemCriadaDTO = objectMapper.readValue(mensagem, ViagemCriadaDTO.class);

            emailService.enviarEmailViagem(viagemCriadaDTO);

            log.info("Partition " + partition +
                    " | consumeEmailViagem | Email: {} ", viagemCriadaDTO.getEmail());

        } catch (Exception e) {
            log.error("Error | consumeEmailViagem");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"4"})},
            containerFactory = "listenerContainerFactory"
    )
    public void consumeEmailAdminPossiveisClientes(@Payload String mensagem,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition)
            throws JsonProcessingException {
        try {

            emailService.enviarEmailAdminPossiveisClientes(mensagem);

            log.info("Partition " + partition + " | consumeEmailAdminPossiveisClientes");

        } catch (Exception e) {
            log.error("Error | consumeEmailAdminPossiveisClientes");
        }
    }
}
