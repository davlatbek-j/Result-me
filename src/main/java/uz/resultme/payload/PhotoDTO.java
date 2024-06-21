package uz.resultme.payload;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Photo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotoDTO
{
    Long id;
    String httpUrl;
    String type;

    public PhotoDTO(Photo entity)
    {
        this.id = entity.getId();
        this.httpUrl = entity.getHttpUrl();
        this.type = entity.getType();
    }

}
