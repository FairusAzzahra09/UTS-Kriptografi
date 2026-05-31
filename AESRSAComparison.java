import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class AESRSAComparison {

    public static void main(String[] args) {
        try {
            String plaintext = "Ini adalah pesan untuk diuji";

            // ================= AES =================
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey aesKey = keyGen.generateKey();

            Cipher aesCipher = Cipher.getInstance("AES");

            long startAES = System.nanoTime();

            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] aesCiphertext = aesCipher.doFinal(plaintext.getBytes());

            long endAES = System.nanoTime();

            // ================= RSA =================
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            Cipher rsaCipher = Cipher.getInstance("RSA");

            long startRSA = System.nanoTime();

            rsaCipher.init(Cipher.ENCRYPT_MODE,
                    keyPair.getPublic());

            byte[] rsaCiphertext =
                    rsaCipher.doFinal(plaintext.getBytes());

            long endRSA = System.nanoTime();

            // ================= OUTPUT =================
            System.out.println("===== HASIL PENGUJIAN =====");

            System.out.println("\nPlaintext:");
            System.out.println(plaintext);

            System.out.println("\nAES Ciphertext:");
            System.out.println(
                    Base64.getEncoder().encodeToString(aesCiphertext));

            System.out.println("Ukuran Ciphertext AES: "
                    + aesCiphertext.length + " byte");

            System.out.println("Waktu Enkripsi AES: "
                    + (endAES - startAES) + " ns");

            System.out.println("\nRSA Ciphertext:");
            System.out.println(
                    Base64.getEncoder().encodeToString(rsaCiphertext));

            System.out.println("Ukuran Ciphertext RSA: "
                    + rsaCiphertext.length + " byte");

            System.out.println("Waktu Enkripsi RSA: "
                    + (endRSA - startRSA) + " ns");

            System.out.println("\n===== ANALISIS =====");

            if ((endAES - startAES) < (endRSA - startRSA)) {
                System.out.println("AES lebih cepat daripada RSA.");
            } else {
                System.out.println("RSA lebih cepat daripada AES.");
            }

            if (aesCiphertext.length < rsaCiphertext.length) {
                System.out.println(
                        "Ciphertext AES lebih kecil daripada RSA.");
            } else {
                System.out.println(
                        "Ciphertext RSA lebih kecil daripada AES.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}