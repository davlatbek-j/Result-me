package uz.resultme.payload;

import jakarta.persistence.ElementCollection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.ServiceOption;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOptionDTO
{
    Long id;

    String nameUz;

    String nameRu;

    List<String> valueUz;

    List<String> valueRu;

    public ServiceOptionDTO(ServiceOption entity , String lang)
    {
        this.id = entity.getId();
        switch (lang)
        {
            case "uz":
            {
                this.nameUz = entity.getNameUz();
                this.valueUz = entity.getValueUz();
                break;
            }
            case "ru":
            {
                this.nameRu = entity.getNameRu();
                this.valueRu = entity.getValueRu();
                break;
            }

        }

    }

}
