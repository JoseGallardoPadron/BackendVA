package pe.du.vallegrande.Vaccine.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.du.vallegrande.Vaccine.model.VaccineModel;
import pe.du.vallegrande.Vaccine.model.VaccineDetailModel;
import pe.du.vallegrande.Vaccine.repository.VaccineRepository;
import pe.du.vallegrande.Vaccine.repository.VaccineDetailRepository;
import pe.du.vallegrande.Vaccine.services.VaccineServices;
import pe.du.vallegrande.Vaccine.services.VaccineDetailServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class VaccineServiceTests {

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private VaccineDetailRepository vaccineDetailRepository;

    @InjectMocks
    private VaccineServices vaccineService;

    @InjectMocks
    private VaccineDetailServices vaccineDetailService;

    @Test
    void testCreateVaccine() {
        VaccineModel vaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        when(vaccineRepository.save(any(VaccineModel.class))).thenReturn(Mono.just(vaccine));

        Mono<VaccineModel> result = vaccineService.createVaccine(vaccine);

        assertDoesNotThrow(() -> result.block());
        assertEquals("COVID-19", result.block().getNameVaccine());
        verify(vaccineRepository, times(1)).save(any(VaccineModel.class));

        // Mensaje de confirmación
        System.out.println("✅ Vacuna creada correctamente: " + result.block().getNameVaccine());
    }

    @Test
    void testGetAllVaccines() {
        VaccineModel vaccine1 = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        VaccineModel vaccine2 = new VaccineModel(2L, "Influenza", "Virus atenuado", "Protección contra gripe", "A");

        when(vaccineRepository.findAll()).thenReturn(Flux.just(vaccine1, vaccine2));

        Flux<VaccineModel> result = vaccineService.getAllVaccines();

        assertDoesNotThrow(() -> result.collectList().block());
        assertEquals(2, result.collectList().block().size());
        verify(vaccineRepository, times(1)).findAll();

        // Mensaje de confirmación
        System.out.println("✅ Se encontraron " + result.collectList().block().size() + " vacunas en el sistema.");
    }

    @Test
    void testDeactivateVaccine() {
        VaccineModel vaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        
        when(vaccineRepository.findById(1L)).thenReturn(Mono.just(vaccine));
        when(vaccineRepository.save(any(VaccineModel.class))).thenReturn(Mono.just(vaccine));
    
        Mono<VaccineModel> result = vaccineService.deactivateVaccine(1L);
    
        assertNotNull(result);
        assertEquals("I", result.block().getActive());
        assertEquals(1L, result.block().getVaccine_id());  // Corregido
        verify(vaccineRepository, times(1)).save(any(VaccineModel.class));
    }
    

    @Test
    void testCreateVaccineDetail() {
        VaccineDetailModel detail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(detail));

        Mono<VaccineDetailModel> result = vaccineDetailService.createVaccineDetail(detail);

        assertDoesNotThrow(() -> result.block());
        assertEquals(new BigDecimal("100.0"), result.block().getPrice());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));

        // Mensaje de confirmación
        System.out.println("✅ Detalle de vacuna creado correctamente con precio: " + result.block().getPrice());
    }

    @Test
void testDeleteVaccineDetail() {
    VaccineDetailModel detail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
    when(vaccineDetailRepository.findById(1L)).thenReturn(Mono.just(detail));
    when(vaccineDetailRepository.delete(any(VaccineDetailModel.class))).thenReturn(Mono.empty());

    Mono<Void> result = vaccineDetailService.deleteVaccineDetail(1L);

    assertDoesNotThrow(() -> result.block());
    verify(vaccineDetailRepository, times(1)).delete(any(VaccineDetailModel.class));

    // ✅ Mensaje de confirmación corregido
    System.out.println("✅ Detalle de vacuna eliminado correctamente con ID: " + detail.getVaccineDetailId());
}

}
