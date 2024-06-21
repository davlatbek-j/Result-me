package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.CaseEffect;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseEffectDTO
{
    Long id;

    String value;

    String effectDescription;

    public CaseEffectDTO(CaseEffect entity, String lang)
    {
        this.id = entity.getId();
        this.value = entity.getValue();
        switch (lang)
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
        }
    }
}
