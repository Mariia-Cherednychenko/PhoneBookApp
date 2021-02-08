package contact_book.Cherednychenko.entities;

import lombok.Data;

@Data
public class Contact {

    private Integer id;
    private String name;
    private String value;
    private contactType type;

    public enum contactType {EMAIL, PHONE}


    //EnumMap<Contact.contactType, String> contactType=new EnumMap<Contact.contactType, String>(Contact.contactType.class);

  /* public String getContact(Contact.contactType TYPE){
       return contactType.get(TYPE);
   }

    public Contact(String name, String contactType) {
        this.name = name;

        if (contactTypeCheck(contactType).equals(Contact.contactType.EMAIL)){
            this.contactType.put(Contact.contactType.EMAIL,contactType);
        }
        else{
            setPhone(contactType);
        }
   }

    public Contact(String name, String contactType1, String contactType2) {
        this.name = name;

        if (contactTypeCheck(contactType1).equals(Contact.contactType.EMAIL)){
            contactType.put(Contact.contactType.EMAIL,contactType1);
            setPhone(contactType2);
        }
        else {
            contactType.put(Contact.contactType.EMAIL,contactType2);
            setPhone(contactType1);
        }
    }

    private Contact.contactType contactTypeCheck(String contactType){
        Pattern patternEmail = Pattern.compile("(\\w+)@(\\w+).([A-Za-z]+)");

        if(patternEmail.matcher(contactType).matches()){
            return Contact.contactType.EMAIL;
        }
       else{
           return Contact.contactType.PHONE;
        }

    }

    private void setPhone(String phoneInput) {
        String phoneShow = phoneInput.replaceAll("\\D", "");
        Pattern pattern1=Pattern.compile("380\\d{9}");
        Pattern pattern2=Pattern.compile("80\\d{9}");
        Pattern pattern3=Pattern.compile("0\\d{9}");
        String phone="";
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
        contactType.put(Contact.contactType.PHONE,phone);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(contactType, contact.contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contactType);
    }

    @Override
    public String toString() {

        String phoneInfo="";
        String emailInfo="";

        if(contactType.get(Contact.contactType.PHONE)!=null) {
            phoneInfo = String.format("%s[phone: %s]\n",
                    name,
                    contactType.get(Contact.contactType.PHONE));
        }
        if(contactType.get(Contact.contactType.EMAIL)!=null) {
            emailInfo = String.format("%s[email: %s]\n",
                    name,
                    contactType.get(Contact.contactType.EMAIL));
        }

        return  String.format("%s%s",
                phoneInfo,
                emailInfo);
    }*/
}
