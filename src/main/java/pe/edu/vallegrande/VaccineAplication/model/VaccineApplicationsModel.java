package pe.edu.vallegrande.VaccineAplication.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("vaccine_applications") // Asegúrate de que coincide con el nombre en la BD
public class VaccineApplicationsModel {

    @Id
    @Column("application_id")
    private Long applicationId;

    @Column("application_date")
    private LocalDate applicationDate;

    @Column("vaccine_id")
    private Long vaccineId;

    @Column("shed_id")
    private Long shedId;

    @Column("amount")
    private Integer amount;

    @Column("cost_application")
    private BigDecimal costApplication;

    @Column("email")
    private String email;

    @Column("date_registration")
    private LocalDate dateRegistration;

    @Column("quantity_birds")
    private Integer quantityBirds;

    @Column("active")
    private String active; // O usa `Boolean active;` si en la BD es tipo boolean

    private String vaccineName; // Para almacenar el nombre de la vacuna
    private String shedName;
}
