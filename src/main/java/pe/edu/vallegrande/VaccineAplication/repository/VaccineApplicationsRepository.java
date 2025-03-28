package pe.edu.vallegrande.VaccineAplication.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pe.edu.vallegrande.VaccineAplication.model.VaccineApplicationsModel; // Cambia a VaccineModel




public interface VaccineApplicationsRepository extends ReactiveCrudRepository<VaccineApplicationsModel, Long> {

}