package uz.resultme.entity.service;

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
public class OptionValue
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String tableUrlUz;

    String tableUrlRu;
}

