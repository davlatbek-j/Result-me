package uz.resultme.entity.cases;

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
public class CaseResult
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 400)
    String titleUz;

    @Column(length = 400)
    String titleRu;

    @ElementCollection
    List<String> valueUz;

    @ElementCollection
    List<String> valueRu;
}
