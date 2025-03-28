package pe.du.vallegrande.Vaccine;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import pe.du.vallegrande.Vaccine.model.VaccineModel;
import pe.du.vallegrande.Vaccine.model.VaccineDetailModel;
import pe.du.vallegrande.Vaccine.repository.VaccineRepository;
import pe.du.vallegrande.Vaccine.repository.VaccineDetailRepository;
import pe.du.vallegrande.Vaccine.services.VaccineServices;
import pe.du.vallegrande.Vaccine.services.VaccineDetailServices;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class VaccineTests {

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private VaccineDetailRepository vaccineDetailRepository;

    @InjectMocks
    private VaccineServices vaccineService;

    @InjectMocks
    private VaccineDetailServices vaccineDetailService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGuardarVacuna() {
        VaccineModel vaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        when(vaccineRepository.save(any(VaccineModel.class))).thenReturn(Mono.just(vaccine));

        Mono<VaccineModel> savedVaccine = vaccineService.createVaccine(vaccine);

        StepVerifier.create(savedVaccine)
                .assertNext(v -> {
                    assertNotNull(v);
                    assertEquals("COVID-19", v.getNameVaccine());
                })
                .verifyComplete();

        verify(vaccineRepository, times(1)).save(any(VaccineModel.class));
    }

    @Test
    public void testObtenerVacunaPorId() {
        VaccineModel vaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        when(vaccineRepository.findById(1L)).thenReturn(Mono.just(vaccine));

        Mono<VaccineModel> foundVaccine = vaccineService.getVaccineById(1L);

        StepVerifier.create(foundVaccine)
                .assertNext(v -> assertEquals("COVID-19", v.getNameVaccine()))
                .verifyComplete();
    }

    @Test
    public void testActualizarVacuna() {
        VaccineModel existingVaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        VaccineModel updatedVaccine = new VaccineModel(1L, "COVID-19 Moderna", "ARN", "Protección avanzada", "A");

        when(vaccineRepository.findById(1L)).thenReturn(Mono.just(existingVaccine));
        when(vaccineRepository.save(any(VaccineModel.class))).thenReturn(Mono.just(updatedVaccine));

        Mono<VaccineModel> result = vaccineService.updateVaccine(1L, updatedVaccine);

        StepVerifier.create(result)
                .assertNext(v -> assertEquals("COVID-19 Moderna", v.getNameVaccine()))
                .verifyComplete();

        verify(vaccineRepository, times(1)).save(any(VaccineModel.class));
    }

    @Test
    public void testEliminarVacuna() {
        VaccineModel vaccine = new VaccineModel(1L, "COVID-19", "ARN", "Protección contra COVID", "A");
        when(vaccineRepository.findById(1L)).thenReturn(Mono.just(vaccine));
        when(vaccineRepository.save(any(VaccineModel.class))).thenReturn(Mono.just(vaccine));

        Mono<VaccineModel> result = vaccineService.deactivateVaccine(1L);

        StepVerifier.create(result)
                .assertNext(v -> assertEquals("I", v.getActive()))
                .verifyComplete();

        verify(vaccineRepository, times(1)).save(any(VaccineModel.class));
    }

    @Test
    public void testCrearDetalleVacuna() {
        VaccineDetailModel detail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(detail));

        Mono<VaccineDetailModel> result = vaccineDetailService.createVaccineDetail(detail);

        StepVerifier.create(result)
                .assertNext(d -> assertEquals(new BigDecimal("100.0"), d.getPrice()))
                .verifyComplete();

        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

    @Test
    public void testObtenerDetalleVacunaPorId() {
        VaccineDetailModel detail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
        when(vaccineDetailRepository.findById(1L)).thenReturn(Mono.just(detail));

        Mono<VaccineDetailModel> result = vaccineDetailService.getVaccineDetailById(1L);

        StepVerifier.create(result)
                .assertNext(d -> assertEquals(new BigDecimal("100.0"), d.getPrice()))
                .verifyComplete();
    }

    @Test
    public void testActualizarDetalleVacuna() {
        VaccineDetailModel existingDetail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
        VaccineDetailModel updatedDetail = new VaccineDetailModel(1L, 1L, new BigDecimal("60.0"), 3, new BigDecimal("180.0"), LocalDate.now(), LocalDate.now().plusDays(365), 15);

        when(vaccineDetailRepository.findById(1L)).thenReturn(Mono.just(existingDetail));
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(updatedDetail));

        Mono<VaccineDetailModel> result = vaccineDetailService.updateVaccineDetail(1L, updatedDetail);

        StepVerifier.create(result)
                .assertNext(d -> assertEquals(new BigDecimal("180.0"), d.getPrice()))
                .verifyComplete();

        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

    @Test
    public void testEliminarDetalleVacuna() {
        VaccineDetailModel detail = new VaccineDetailModel(1L, 1L, new BigDecimal("50.0"), 2, new BigDecimal("100.0"), LocalDate.now(), LocalDate.now().plusDays(365), 10);
        when(vaccineDetailRepository.findById(1L)).thenReturn(Mono.just(detail));
        when(vaccineDetailRepository.delete(any(VaccineDetailModel.class))).thenReturn(Mono.empty());

        Mono<Void> result = vaccineDetailService.deleteVaccineDetail(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(vaccineDetailRepository, times(1)).delete(any(VaccineDetailModel.class));
    }
}
