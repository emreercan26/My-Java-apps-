/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting;


import java.io.Serializable; // Önerilen, ancak zorunlu değil


public class Student implements Comparable<Student>, Serializable {

    private final long ID;
    private final String name;
    private final String surname;
    private final int age;
    private final int atdYear;

    /**
     sınıfının kurucu metodu (Constructor).
    
     **/
    public Student(long ID, String name, String surname, int age, int atdYear) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.atdYear = atdYear;
    }

    /**
     * Comparable arayüzünden gelen metot.
     * nasıl karşılaştıracağını tanımlar.
     * ID'ye göre artan (ascending) sırada sıralama yapılır.
     * Long.compare(a, b) şu sonuçları döndürür:
     * - Negatif: a < b
     * - Sıfır: a == b
     * - Pozitif: a > b
     */
    @Override
    public int compareTo(Student other) {
        return Long.compare(this.ID, other.ID);
    }

    /**
     Dosya çıktısı için istenen formatı (ID Name Surname Age AttendanceYear) sağlar.
     */
    @Override
    public String toString() {
        return String.format("%d %s %s %d %d", ID, name, surname, age, atdYear);
    }

    //  getter metotları
    public long getID() { return ID; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getAge() { return age; }
    public int getAtdYear() { return atdYear; }

    
}
