package uz.resultme.payload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.resultme.entity.Case;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseDTO
{
    Long id;

    String title;

    String description;

    String name;

    String about;

    List<String> request;

    List<CaseEffectDTO> effect;

    List<CaseResultDTO> caseResult;

    PhotoDTO mainPhoto;

    List<PhotoDTO> gallery;

    Boolean active;

    public CaseDTO(Case aCase, String lang)
    {
        this.id = aCase.getId();
        this.mainPhoto = new PhotoDTO(aCase.getMainPhoto());
        this.gallery = new ArrayList<>();
        aCase.getGallery().forEach(i -> this.gallery.add(new PhotoDTO(i)));
        this.active = aCase.getActive();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = aCase.getTitleUz();
                this.description = aCase.getDescriptionUz();
                this.name = aCase.getNameUz();
                this.about = aCase.getAboutUz();

                this.request = new ArrayList<>();
                this.request.addAll(aCase.getRequestUz());

                this.effect = new ArrayList<>();
                aCase.getEffect().forEach(i -> this.effect.add(new CaseEffectDTO(i, "uz")));

                this.caseResult = new ArrayList<>();
                aCase.getCaseResult().forEach(i -> this.caseResult.add(new CaseResultDTO(i, "uz")));
                break;
            }
            case "ru":
            {
                this.title = aCase.getTitleRu();
                this.description = aCase.getDescriptionRu();
                this.name = aCase.getNameRu();
                this.about = aCase.getAboutRu();

                this.request = new ArrayList<>();
                this.request.addAll(aCase.getRequestRu());

                this.effect = new ArrayList<>();
                aCase.getEffect().forEach(i -> this.effect.add(new CaseEffectDTO(i, "ru")));

                this.caseResult = new ArrayList<>();
                aCase.getCaseResult().forEach(i -> this.caseResult.add(new CaseResultDTO(i, "ru")));
                break;
            }
            default:
            {
                throw new RuntimeException("Invalid language: " + lang);
            }
        }

    }
}