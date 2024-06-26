package uz.resultme.payload.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.service.ServiceOption;
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

    String tableUrlUz;

    String tableUrlRu;

    public ServiceOptionDTO(ServiceOption entity , String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.value = entity.getValueUz();
                this.tableUrlUz = entity.getTableUrlUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.value = entity.getValueRu();
                this.tableUrlRu = entity.getTableUrlRu();
                break;
            }
            default:
            {
                throw new LanguageNotSupportException("Language not supported: " + lang);
            }
        }

    }

}
