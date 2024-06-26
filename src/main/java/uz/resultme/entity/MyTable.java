package uz.resultme.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MyTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String range;

    String sheetId;

    String httpUrl;

    String lang;

}
