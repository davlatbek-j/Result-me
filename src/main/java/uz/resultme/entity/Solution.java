package uz.resultme.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Solution
{
    @Id
    @GeneratedValue
    Long id;

    String nameUz;

    String nameRu;

    @ElementCollection
    List<String> valueUz;

    @ElementCollection
    List<String> valueRu;
}
