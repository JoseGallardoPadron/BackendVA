package pe.edu.vallegrande.VaccineAplication.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Constructor con @Autowired (Spring Boot inyectará KafkaTemplate automáticamente)
    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Método para enviar mensajes a Kafka
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Mensaje enviado a Kafka: " + message);
    }
}

