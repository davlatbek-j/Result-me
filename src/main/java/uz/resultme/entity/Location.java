package uz.resultme.entity;


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
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String url;

    Boolean active;
}
