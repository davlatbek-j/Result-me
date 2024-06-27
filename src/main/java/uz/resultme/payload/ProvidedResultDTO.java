package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.ProvidedResult;
import uz.resultme.payload.cases.CaseEffectDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvidedResultDTO
{
    Long id;

    String title;

    List<CaseEffectDTO> effect;

    Boolean active;


    public ProvidedResultDTO(ProvidedResult entity, String lang)
    {
        this.id = entity.getId();
        this.active = entity.getActive();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Language not supported: " + lang);
            }
        }
        this.effect = new ArrayList<>();
        entity.getEffect().forEach(i -> this.effect.add(new CaseEffectDTO(i, lang)));
    }
}
