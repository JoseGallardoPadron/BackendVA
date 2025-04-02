package pe.edu.vallegrande.VaccineAplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.VaccineAplication.model.VaccineApplicationsModel;
import pe.edu.vallegrande.VaccineAplication.repository.VaccineApplicationsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VaccineApplicationsServices {

    @Autowired
    private VaccineApplicationsRepository vaccineApplicationsRepository;
    
    
    

    private final WebClient webClient;

    public VaccineApplicationsServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://bug-free-waddle-vxvgr7v6gv4h6qvv-8080.app.github.dev/vaccines").build(); 
    }

    // 🔹 Obtener una vacuna desde otro microservicio con WebClient
    public Mono<VaccineApplicationsModel> getVaccineById(Long vaccineId) {
        return webClient.get()
                .uri("/{id}", vaccineId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> 
                    Mono.error(new RuntimeException("Vacuna no encontrada"))
                )
                .onStatus(status -> status.is5xxServerError(), response -> 
                    Mono.error(new RuntimeException("Error en el servidor de vacunas"))
                )
                .bodyToMono(VaccineApplicationsModel.class)
                .onErrorResume(e -> {
                    System.err.println("Error en la solicitud de vacuna: " + e.getMessage());
                    return Mono.empty(); // Devuelve vacío en caso de error
                });
    }

    // 🔹 Crear una aplicación de vacuna
    public Mono<ResponseEntity<VaccineApplicationsModel>> createApplication(VaccineApplicationsModel application) {
        return getVaccineById(application.getVaccineId())
                .flatMap(vaccine -> {
                    if (vaccine == null) {
                        return Mono.just(ResponseEntity.badRequest().build());
                    }
                    return vaccineApplicationsRepository.save(application)
                            .map(savedApplication -> {
                                return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
                            });
                });
    }

    // 🔹 Listar todas las aplicaciones de vacunas
    public Flux<VaccineApplicationsModel> getAllApplications() {
        return vaccineApplicationsRepository.findAll();
    }

    // 🔹 Obtener una aplicación de vacuna por ID
    public Mono<VaccineApplicationsModel> getApplicationById(Long id) {
        return vaccineApplicationsRepository.findById(id);
    }

    // 🔹 Actualizar una aplicación de vacuna
    public Mono<VaccineApplicationsModel> updateApplication(Long id, VaccineApplicationsModel application) {
        application.setApplicationId(id);
        return vaccineApplicationsRepository.save(application);
    }

    // 🔹 Eliminar (inactivar) una aplicación de vacuna
    public Mono<VaccineApplicationsModel> deactivateApplication(Long id) {
        return vaccineApplicationsRepository.findById(id)
            .flatMap(existingApplication -> {
                existingApplication.setActive("I");
                return vaccineApplicationsRepository.save(existingApplication);
            });
    }

    // 🔹 Activar una aplicación de vacuna
    public Mono<VaccineApplicationsModel> activateApplication(Long id) {
        return vaccineApplicationsRepository.findById(id)
            .flatMap(existingApplication -> {
                existingApplication.setActive("A");
                return vaccineApplicationsRepository.save(existingApplication);
            });
    }
}
