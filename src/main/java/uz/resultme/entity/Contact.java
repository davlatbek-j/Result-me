package uz.resultme.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer orderNum;
    String instagram;
    String telegram;
    String facebook;
   /* @OneToMany
    List<String> phone;*/
    @OneToMany
    List<Phone> phones;
    String location;

}
