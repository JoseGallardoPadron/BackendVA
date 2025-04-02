package pe.edu.vallegrande.VaccineAplication.services;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "str-topic", groupId = "my-group")  // 🔹 Asegúrate de que coincida con el group-id
    public void listen(ConsumerRecord<String, String> record) {
        log.info("📩 Mensaje recibido: {}", record.value());
    }
}
