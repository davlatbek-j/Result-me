package uz.resultme.payload;

import jakarta.persistence.ElementCollection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.ServiceOption;
import uz.resultme.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOptionDTO
{
    Long id;

    String name;

    List<String> value;

    public ServiceOptionDTO(ServiceOption entity , String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.value = entity.getValueUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.value = entity.getValueRu();
                break;
            }
            default:
            {
                throw new LanguageNotSupportException("Language not supported: " + lang);
            }
        }

    }

}
