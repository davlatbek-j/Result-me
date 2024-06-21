package uz.resultme.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Background;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BackgroundDTO
{
    Long id;
    PhotoDTO photo;

    public BackgroundDTO(Background entity)
    {
        this.id = entity.getId();
        this.photo = new PhotoDTO(entity.getPhoto());
    }
}
