package uz.resultme.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.resultme.entity.Contact;
import uz.resultme.exception.LanguageNotSupportException;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter

public class ContactDto
{
    Long id;
    String instagram;
    String telegram;
    String facebook;
    List<String> phone;
    String location;

    public ContactDto(Contact entity, String lang)
    {
        this.id = entity.getId();
        this.facebook = entity.getFacebook();
        this.instagram = entity.getInstagram();
        this.telegram = entity.getTelegram();
        this.phone = entity.getPhone();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.location = entity.getAddressUz();
                break;
            }
            case "ru":
            {
                this.location = entity.getAddressRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not support:" + lang);
        }
    }

}
