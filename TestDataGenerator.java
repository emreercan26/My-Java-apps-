/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sorting;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


 
public class TestDataGenerator {

    //  15 haneli ID aralığı
    private static final long MIN_ID = 100_000_000_000_000L;
    private static final long MAX_ID = 999_999_999_999_999L;

    // Rastgele isim ve soyisim listeleri 
    private static final String[] NAMES = {"Ali", "Ayşe", "Burak", "Can", "Deniz", "Ece", "Furkan", "Gizem", "Hasan", "Irmak"};
    private static final String[] SURNAMES = {"Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Öztürk", "Yıldız", "Ateş", "Güneş", "Aksoy"};

    /**
      rastgele veri dosyalarını oluşturur.
      sizes Oluşturulacak kayıt sayılarını içeren dizi (Örn: {10000, 100000, 1000000}).
    **/
    public static void generateAll(int[] sizes) {
        for (int N : sizes) {
            String filePath = "students_" + N + ".txt";
            System.out.println("Generating " + N + " records to " + filePath + "...");
            try {
                generateFile(filePath, N);
                System.out.println("File " + filePath + " created successfully.");
            } catch (IOException e) {
                System.err.println("FATAL ERROR: Could not generate file " + filePath + ": " + e.getMessage());
            }
        }
    }

    /**
        filePath Yazılacak dosyanın yolu.
        count    Kayıt sayısı (N).
     
     */
    private static void generateFile(String filePath, int count) throws IOException {

        //  dosya yazma için BufferedWriter.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // 1. İlk satıra kayıt sayısını yaz (N)
            writer.write(String.valueOf(count));
            writer.newLine();

            Random rand = ThreadLocalRandom.current();

            // 2. N adet rastgele kayıt oluştur ve yaz
            for (int i = 0; i < count; i++) {
                // Rastgele 15 haneli ID üretme 
                long randomID = ThreadLocalRandom.current().nextLong(MIN_ID, MAX_ID + 1);

                // Rastgele isim, soyisim, yaş ve yıl seçimi
                String name = NAMES[rand.nextInt(NAMES.length)];
                String surname = SURNAMES[rand.nextInt(SURNAMES.length)];
                int age = rand.nextInt(45 - 18 + 1) + 18; // 18 ile 45 arası
                int atdYear = rand.nextInt(2025 - 2000 + 1) + 2000; // 2000 ile 2025 arası

                //  ID Name Surname Age AttendanceYear 
                String recordLine = String.format("%d %s %s %d %d", randomID, name, surname, age, atdYear);

                writer.write(recordLine);
                writer.newLine();
            }

        } 
    }

    public static void main(String[] args) {
        // Bu metot doğrudan çağrılmayacak, EMRE_ERCAN_HW1 tarafından kullanılacaktır.
    }


}

