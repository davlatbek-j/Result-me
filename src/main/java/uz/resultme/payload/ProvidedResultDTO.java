package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.ProvidedResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvidedResultDTO
{
    Long id;

    String name;

    String description;

    Boolean active;

    public ProvidedResultDTO(ProvidedResult entity,String lang)
    {
        this.id = entity.getId();
        this.name = entity.getName();
        this.active = entity.getActive();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.description= entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.description= entity.getDescriptionRu();
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Language not supported: " + lang);
            }
        }
    }
}
