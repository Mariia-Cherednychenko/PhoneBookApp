package ua.ithlillel.dnipro.Cherednychenko.contacts;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Contact {
    private String name;
    private  String phone;
    private  String email;


    private  enum type{
        PHONE,
        EMAIL
    }

   public Contact(String name, String contactType) {
        this.name = name;

        if (contactTypeCheck(contactType).equals(type.EMAIL)){
            this.email=contactType;
        }
        else{
            setPhone(contactType);
        }
   }

    public Contact(String name, String contactType1, String contactType2) {
        this.name = name;

        if (contactTypeCheck(contactType1).equals(type.EMAIL)){
            this.email=contactType1;
            setPhone(contactType2);
        }
        else {
            this.email=contactType2;
            setPhone(contactType1);
        }
    }

    private type contactTypeCheck(String contactType){
        Pattern patternEmail = Pattern.compile("(\\w+)@(\\w+).([A-Za-z]+)");

        if(patternEmail.matcher(contactType).matches()){
            return type.EMAIL;
        }
       else{
           return type.PHONE;
        }

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
                Objects.equals(email, contact.email) &&
                Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone);
    }

    @Override
    public String toString() {

        String phoneInfo="";
        String emailInfo="";

        if(phone!=null) {
            phoneInfo = String.format("%s[phone: %s]\n",
                    name,
                    phone);
        }
        if(email!=null) {
            emailInfo = String.format("%s[email: %s]\n",
                    name,
                    email);
        }

        return  String.format("%s%s",
                phoneInfo,
                emailInfo);
    }
}
