package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Article;
import uz.resultme.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDTO
{
    Long id;

    String title;

    String theme;

    List<PlanDTO> plan;

    PhotoDTO mainPhoto;

    List<PhotoDTO> gallery;

    Boolean active;

    public ArticleDTO(Article entity, String lang)
    {
        this.id = entity.getId();
        this.mainPhoto = new PhotoDTO(entity.getMainPhoto());
        this.gallery = new ArrayList<>();
        entity.getGallery().forEach(i -> this.gallery.add(new PhotoDTO(i)));
        this.active = entity.getActive();

        switch (lang.toLowerCase())
        {
            case "en":
            {
                this.title = entity.getTitleUz();
                this.theme = entity.getThemeUz();
                this.plan = new ArrayList<>();
                entity.getPlan().forEach(i -> this.plan.add(new PlanDTO(i, "uz")));
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.theme = entity.getThemeRu();
                this.plan = new ArrayList<>();
                entity.getPlan().forEach(i -> this.plan.add(new PlanDTO(i, "ru")));
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
