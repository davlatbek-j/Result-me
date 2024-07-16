package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Background;
import uz.resultme.entity.Partner;
import uz.resultme.entity.Photo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerDTO {
    Long id;
    PhotoDTO photo;
    String partnerUrl;

    public PartnerDTO(Partner entity)
    {
        this.id = entity.getId();
        this.photo = new PhotoDTO(entity.getPhoto());
        this.partnerUrl = entity.getPartnerUrl();
    }
}
