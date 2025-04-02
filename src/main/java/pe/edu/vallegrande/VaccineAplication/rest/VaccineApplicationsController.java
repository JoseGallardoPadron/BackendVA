package pe.edu.vallegrande.VaccineAplication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.VaccineAplication.model.VaccineApplicationsModel;
import pe.edu.vallegrande.VaccineAplication.repository.VaccineApplicationsRepository;
import pe.edu.vallegrande.VaccineAplication.services.VaccineApplicationsServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vaccine-applications") // Ruta base para el controlador
public class VaccineApplicationsController {

    @Autowired
    private VaccineApplicationsRepository vaccineApplicationsRepository;

    @Autowired
    private VaccineApplicationsServices vaccineApplicationsServices; // Inyecta el servicio


    @PostMapping("/create")
    public Mono<ResponseEntity<VaccineApplicationsModel>> createApplication(@RequestBody VaccineApplicationsModel application) {
        return vaccineApplicationsServices.createApplication(application);
    }

    // Listar todas las aplicaciones de vacunas
    @GetMapping
    public Flux<VaccineApplicationsModel> getAllApplications() {
        return vaccineApplicationsRepository.findAll();
    }

    // Obtener una aplicación de vacuna por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VaccineApplicationsModel>> getApplicationById(@PathVariable Long id) {
        return vaccineApplicationsRepository.findById(id)
                .map(application -> ResponseEntity.ok(application))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Actualizar una aplicación de vacuna
    @PutMapping("/{id}")
    public Mono<ResponseEntity<VaccineApplicationsModel>> updateApplication(@PathVariable Long id, @RequestBody VaccineApplicationsModel application) {
        application.setApplicationId(id); // Asegúrate de que el ID sea el correcto
        return vaccineApplicationsRepository.save(application)
                .map(updatedApplication -> ResponseEntity.ok(updatedApplication))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Eliminar (inactivar) una vacuna
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<VaccineApplicationsModel>> deactivateApplication(@PathVariable Long id) {
        return vaccineApplicationsServices.deactivateApplication(id) // Llamada al servicio que cambia el estado
                .map(deactivateApplication -> ResponseEntity.ok(deactivateApplication)) // Retorna la vacuna con estado "I"
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Si no se encuentra la vacuna, retorna Not Found
    }

    // Activar (revertir a activo) una vacuna
    @PatchMapping("/activate/{id}") // Método PATCH para actualizar parcialmente
    public Mono<ResponseEntity<VaccineApplicationsModel>> activateApplication(@PathVariable Long id) {
        return vaccineApplicationsServices.activateApplication(id) // Llamada al servicio que cambia el estado
                .map(activateApplication -> ResponseEntity.ok(activateApplication)) // Retorna la vacuna con estado "A"
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Si no se encuentra la vacuna, retorna Not Found
    }
    

}
