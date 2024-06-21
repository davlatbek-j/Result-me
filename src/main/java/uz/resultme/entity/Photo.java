package uz.resultme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.payload.PhotoDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String filepath;

    String httpUrl;

    String type;

    public Photo(String name, String filepath, String httpUrl)
    {
        this.name = name;
        this.filepath = filepath;
        this.httpUrl = httpUrl;
    }

    public Photo(PhotoDTO dto)
    {
        this.id = dto.getId();
        this.httpUrl = dto.getHttpUrl();
    }

}
