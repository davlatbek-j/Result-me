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
    String nameUz;

    String nameRu;

    List<String> valueUz;

    List<String> valueRu;

    public ServiceOptionDTO(ServiceOption entity)
    {
        this.nameUz = entity.getNameUz();
        this.nameRu = entity.getNameRu();
        this.valueUz = entity.getValueUz();
        this.valueRu = entity.getValueRu();
    }

}
