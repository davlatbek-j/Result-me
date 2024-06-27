package uz.resultme.payload.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.service.OptionValue;
import uz.resultme.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionValueDTO
{
    Long id;

    String name;

    String httpTableUrl;

    public OptionValueDTO(OptionValue entity, String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.httpTableUrl = entity.getTableUrlUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.httpTableUrl = entity.getTableUrlRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported:" + lang);
        }
    }
}
