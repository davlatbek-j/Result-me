package uz.resultme.payload;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.ServiceEntity;
import uz.resultme.entity.ServiceOption;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceEntityDTO
{
    Long id;

    String nameUz;

    String nameRu;

    List<ServiceOptionDTO> option;

    PhotoDTO icon;

    Boolean active;

    public ServiceEntityDTO(ServiceEntity entity, String lang)
    {
        this.id = entity.getId();
        this.nameUz = entity.getNameUz();
        this.nameRu = entity.getNameRu();

        this.option = new ArrayList<>();
        entity.getOption().forEach(i -> this.option.add(new ServiceOptionDTO(i, lang)));

        this.icon = new PhotoDTO(entity.getIcon());
        this.active = entity.getActive();
    }

}
