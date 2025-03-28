package pe.du.vallegrande.Vaccine.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pe.du.vallegrande.Vaccine.model.VaccineDetailModel;; // Cambia a VaccineModel



public interface VaccineDetailRepository extends ReactiveCrudRepository<VaccineDetailModel, Long> { // Cambia el nombre de la interfaz

}
