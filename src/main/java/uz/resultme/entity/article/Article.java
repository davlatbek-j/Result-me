package uz.resultme.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Photo;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Article
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;
    String titleRu;

    String themeUz;
    String themeRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Plan> plan;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Photo mainPhoto;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Photo bodyPhoto;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    List<Photo> gallery;

    Boolean active;
}
