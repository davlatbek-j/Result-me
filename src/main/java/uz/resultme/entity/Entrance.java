package uz.resultme.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Entrance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;
    String titleRu;

    String descriptionUz;
    String descriptionRu;

    Boolean active;
}
