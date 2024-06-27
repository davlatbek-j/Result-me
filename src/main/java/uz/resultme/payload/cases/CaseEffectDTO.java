package uz.resultme.payload.cases;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.cases.Effect;
import uz.resultme.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseEffectDTO
{
    Long id;

    String value;

    String effectDescription;

    public CaseEffectDTO(Effect entity, String lang)
    {
        this.id = entity.getId();
        this.value = entity.getValue();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.effectDescription = entity.getEffectDescriptionUz();
                break;
            }
            case "ru":
            {
                this.effectDescription = entity.getEffectDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
