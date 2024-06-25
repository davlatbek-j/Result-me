package uz.resultme.payload.cases;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.cases.CaseResult;
import uz.resultme.exception.LanguageNotSupportException;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseResultDTO
{
    Long id;

    String title;

    List<String> value;

    public CaseResultDTO(CaseResult entity, String lang)
    {
        this.id = entity.getId();

        switch (lang)
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                this.value = entity.getValueUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.value = entity.getValueRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not support: " + lang);
        }
    }
}
