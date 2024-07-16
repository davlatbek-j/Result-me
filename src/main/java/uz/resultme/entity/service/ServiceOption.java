package uz.resultme.entity.service;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class ServiceOption
{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<OptionValue> optionValue;
}
