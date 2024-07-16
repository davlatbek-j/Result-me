package uz.resultme.entity.cases;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Effect
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String value;

    String effectDescriptionUz;

    String effectDescriptionRu;
}
