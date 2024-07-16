package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Entrance;
import uz.resultme.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntranceDTO
{
    Long id;

    String title;

    String description;

    Boolean active;

    public EntranceDTO(Entrance entity, String lang)
    {
        this.id = entity.getId();
        this.active = entity.getActive();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title= entity.getTitleUz();
                this.description= entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title= entity.getTitleRu();
                this.description= entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
