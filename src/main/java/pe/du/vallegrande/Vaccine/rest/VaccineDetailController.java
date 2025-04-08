package pe.du.vallegrande.Vaccine.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.du.vallegrande.Vaccine.model.VaccineDetailModel;
import pe.du.vallegrande.Vaccine.repository.VaccineDetailRepository;
import pe.du.vallegrande.Vaccine.services.VaccineDetailServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vaccines/details") // Ruta base para el controlador de detalles de vacunas
public class VaccineDetailController {

    @Autowired
    private VaccineDetailRepository vaccineDetailRepository;

    @SuppressWarnings("unused")
    @Autowired
    private VaccineDetailServices vaccineDetailServices;

    // Crear un nuevo detalle de vacuna
    @PostMapping("/create")
    public Mono<ResponseEntity<VaccineDetailModel>> createVaccineDetail(@RequestBody VaccineDetailModel vaccineDetail) {
        return vaccineDetailRepository.save(vaccineDetail)
                .map(savedDetail -> ResponseEntity.status(HttpStatus.CREATED).body(savedDetail))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    // Listar todos los detalles de vacunas
    @GetMapping
    public Flux<VaccineDetailModel> getAllVaccineDetails() {
        return vaccineDetailRepository.findAll();
    }

    // Obtener un detalle de vacuna por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VaccineDetailModel>> getVaccineDetailById(@PathVariable Long id) {
        return vaccineDetailRepository.findById(id)
                .map(vaccineDetail -> ResponseEntity.ok(vaccineDetail))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Actualizar un detalle de vacuna
    @PutMapping("/{id}")
    public Mono<ResponseEntity<VaccineDetailModel>> updateVaccineDetail(@PathVariable Long id, @RequestBody VaccineDetailModel vaccineDetail) {
        vaccineDetail.setVaccineDetailId(id); // Asegúrate de que el ID sea el correcto
        return vaccineDetailRepository.save(vaccineDetail)
                .map(updatedDetail -> ResponseEntity.ok(updatedDetail))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Eliminar (inactivar) un detalle de vacuna
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteVaccineDetail(@PathVariable Long id) {
        return vaccineDetailRepository.findById(id)
                .flatMap(existingDetail -> vaccineDetailRepository.delete(existingDetail)
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
