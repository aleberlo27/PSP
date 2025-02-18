import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MetodosEstaticos {
    public static String generarHashMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(input.getBytes());

        // Convertir los bytes a formato hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
      
    public static String generarQuinielaAleatoria() {
        String opciones = "12X";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            sb.append(opciones.charAt(rand.nextInt(3)));
        }
        return sb.toString();
    }

}
