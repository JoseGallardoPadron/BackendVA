package pe.du.vallegrande.Vaccine.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.du.vallegrande.Vaccine.services.KafkaProducerService;

@RestController
public class KafkaProducerController {
    private final KafkaProducerService producerService;

    public KafkaProducerController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage("mi-topic", message);
        return "Mensaje enviado: " + message;
    }
}

