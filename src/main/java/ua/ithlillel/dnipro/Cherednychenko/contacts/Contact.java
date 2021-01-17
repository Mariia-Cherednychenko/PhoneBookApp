package ua.ithlillel.dnipro.Cherednychenko.contacts;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Contact {
    private String name;
    private String phone;

   public Contact(String name, String phone) {
        this.name = name;
        setPhone(phone);
   }

    private void setPhone(String phoneInput) {
        String phoneShow = phoneInput.replaceAll("\\D", "");
        Pattern pattern1=Pattern.compile("380\\d{9}");
        Pattern pattern2=Pattern.compile("80\\d{9}");
        Pattern pattern3=Pattern.compile("0\\d{9}");
        if (pattern1.matcher(phoneShow).matches()){
            phone = "+"+ phoneShow.substring(0,2)+ " "+ phoneShow.substring(2,5)+" "+
                    phoneShow.substring(5,8)+" "+ phoneShow.substring(8);
        }
        else if (pattern2.matcher(phoneShow).matches()){
            phone = "+3"+ phoneShow.charAt(0)+ " "+ phoneShow.substring(1,4)+" "+
                    phoneShow.substring(4,7)+" "+ phoneShow.substring(7);
        }
        else if (pattern3.matcher(phoneShow).matches()){
            phone = "+38 " + phoneShow.substring(0,3)+" "+
                    phoneShow.substring(3,6)+" "+ phoneShow.substring(6);
        }
    }

      @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
}
