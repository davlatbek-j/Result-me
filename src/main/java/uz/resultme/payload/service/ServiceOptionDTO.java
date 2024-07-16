package uz.resultme.payload.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.service.ServiceOption;
import uz.resultme.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOptionDTO
{
    Long id;

    String name;

    List<OptionValueDTO> optionValue;

    public ServiceOptionDTO(ServiceOption entity, String lang)
    {
        this.id = entity.getId();
        this.optionValue = new ArrayList<>();
        entity.getOptionValue().forEach(i -> this.optionValue.add(new OptionValueDTO(i, lang)));
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                break;
            }
            default:
            {
                throw new LanguageNotSupportException("Language not supported: " + lang);
            }
        }

    }

}
