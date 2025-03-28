package pe.du.vallegrande.Vaccine.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("vaccineDetail") // Nombre de la tabla en la base de datos
public class VaccineDetailModel {

    @Id
    private Long vaccineDetailId;

    @Column("vaccine_id") // Especifica el nombre exacto de la columna
    private Long vaccineId;

    @Column("amount_ml") // Especifica el nombre exacto de la columna
    private BigDecimal amountMl;

    @Column("dose_amount") // Especifica el nombre exacto de la columna
    private Integer doseAmount;

    @Column("price") // Especifica el nombre exacto de la columna
    private BigDecimal price;

    @Column("manufacturing_date") // Especifica el nombre exacto de la columna
    private LocalDate manufacturingDate;

    @Column("expiration_date") // Especifica el nombre exacto de la columna
    private LocalDate expirationDate;

    @Column("stock") // Especifica el nombre exacto de la columna
    private Integer stock;
}
