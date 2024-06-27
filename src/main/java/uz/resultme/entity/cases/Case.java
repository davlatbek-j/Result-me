package uz.resultme.entity.cases;

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
@Table(name = "cases")
public class Case
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String descriptionUz;

    String descriptionRu;

    String nameUz;

    String nameRu;

    String aboutUz;

    String aboutRu;

    @ElementCollection
    List<String> requestUz;

    @ElementCollection
    List<String> requestRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Effect> effect;

    @OneToMany(cascade = CascadeType.ALL)
    List<CaseResult> caseResult;

    @OneToOne(cascade = CascadeType.ALL)
    Photo mainPhoto;

    @OneToMany(cascade = CascadeType.ALL)
    List<Photo> gallery;

    Boolean active;
}
