package uz.resultme.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Contact;
import uz.resultme.entity.Phone;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter

public class ContactDto {
    Long id;
    Integer orderNum;
    String instagram;
    String telegram;
    String facebook;
    List<Phone> phone;
    String location;

    public ContactDto(Contact contact){
        this.id=contact.getId();
        this.orderNum=contact.getOrderNum();
        this.facebook=contact.getFacebook();
        this.instagram=contact.getInstagram();
        this.telegram=contact.getTelegram();
        this.phone=contact.getPhones();
        this.location=contact.getAddress();
    }

}
