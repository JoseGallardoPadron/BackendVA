package pe.edu.vallegrande.VaccineAplication.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "mi-topic", groupId = "mi-grupo")
    public void listen(String message) {
        System.out.println("Mensaje recibido: " + message);
    }
}

