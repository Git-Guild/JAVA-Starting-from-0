//TODO: String Methods

public class L04_String {
    public static void main(String[] args) 
    {
        
/*
* Non-primitive/Reference data types = String, Arrays, Classes, Interface
*/

//! Strings

String name = "Mikky";
String nickname = "Mikky";
String fullname = " Yuvraj Sarathe ";
System.out.println("My name is " + name);
System.out.println(name.length()); //& length() method is used to get the length of the string
System.out.println(name.toUpperCase());

String gmail = "yuvrajsarathe07@gmail.com";
System.out.println(gmail.replace("yuvraj", "mikky"));
String username = gmail.substring(0, gmail.indexOf('@'));
System.out.println(username);
String domain = gmail.substring(gmail.indexOf('@') + 1);
System.out.println(domain);

String address = "Locate the New York City on the new world map.";
System.out.println(address.indexOf("world")); //& indexOf() method returns the index of the first occurrence of the specified character in the string
System.out.println(address.indexOf("new")); //& indexOf() method returns the index of the first occurrence of the specified character in the string
System.out.println(address.indexOf("world")); //& indexOf() method returns the index of the first occurrence of the specified character in the string

System.out.println(name.charAt(4));

if(name.equals(nickname))
{
    System.out.println("Names are equal");
}

System.out.println("Before: [" + fullname + "]");
System.out.println("After:  [" + fullname.trim() + "]"); //& trim() method is used to remove whitespace from the beginning and end of the string

System.out.println(name.concat(fullname)); //& concat() method is used to concatenate two strings

String txt = "We are the so called \"Vikings\" from the north."; //& \ is used to escape a character
System.out.println(txt);

String txt3 = "Hello\rWorld!";
System.out.println(txt3); //& \r is used to move the cursor to the beginning of the line

String txt4 = "Hello\fWorld!";
System.out.println(txt4); //& \f is used to form a page break

String txt5 = "Hello \\\\ World!";
System.out.println(txt5); //& \ is used to escape a character
        
        String dialogue = "The captain said, \"All hands on deck!\"";
        
        // Escaping backslashes for a Windows folder path
        String filePath = "C:\\Program Files\\Java\\jdk-21";

        System.out.println(dialogue);
        System.out.println(filePath);
    }
}
