package app.model.entity.equipment;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="equipments")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String location;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;
    @Column(nullable = false)
    private BigDecimal hourlyRate;

}
