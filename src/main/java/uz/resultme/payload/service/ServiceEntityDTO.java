package uz.resultme.payload.service;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.service.ServiceEntity;
import uz.resultme.exception.LanguageNotSupportException;
import uz.resultme.payload.PhotoDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceEntityDTO
{
    Long id;

    String name;

    List<ServiceOptionDTO> option;

    PhotoDTO icon;

    Boolean active;

    public ServiceEntityDTO(ServiceEntity entity, String lang)
    {
        this.id = entity.getId();
        this.icon = new PhotoDTO(entity.getIcon());
        this.active = entity.getActive();

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

        this.option = new ArrayList<>();
        entity.getOption().forEach(i -> this.option.add(new ServiceOptionDTO(i, lang)));

    }

}
