package uz.resultme.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.resultme.payload.BackgroundDTO;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Background
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    Photo photo;

    public Background(Photo photo)
    {
        this.photo = photo;
    }

    public Background(BackgroundDTO dto)
    {
        this.id = dto.getId();
        this.photo = new Photo(dto.getPhoto());
    }
}
