package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Plan;
import uz.resultme.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanDTO
{
    Long id;

    String name;

    String text;

    public PlanDTO(Plan entity, String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.text = entity.getTextUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.text = entity.getTextRu();
                break;
            }
            default:
            {
                throw new LanguageNotSupportException("Language not supported: " + lang);
            }
        }
    }
}
