package uz.resultme.entity.service;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Photo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class MyTable
{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    Long id;

    String name;

    int x;

    int y;

    @OneToMany(cascade = CascadeType.ALL)
    List<MyRow> row;
}


