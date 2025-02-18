import java.io.Serializable;
import java.security.NoSuchAlgorithmException;


public class Quiniela implements Serializable {
    private static final long UID = 200L; 
    private String quiniela;
    private String hashQuiniela;
    public static String quinielaValida = "2X12X1X22X112XX";

    public Quiniela() throws NoSuchAlgorithmException{
        quiniela = MetodosEstaticos.generarQuinielaAleatoria();
        hashQuiniela = MetodosEstaticos.generarHashMD5(quiniela);
    }
    
    public String getHashQuiniela(){
        return hashQuiniela;
    }

    public String getQuiniela(){
        return quiniela;
    }

    public void setQuiniela(String datos) throws NoSuchAlgorithmException{
        this.quiniela = datos;
        this.hashQuiniela = MetodosEstaticos.generarHashMD5(quiniela);
    }

    public static String getHashQuinielaValida() throws NoSuchAlgorithmException{
        String hashQuinielaValida = MetodosEstaticos.generarHashMD5(quinielaValida);
        return hashQuinielaValida;
    }

    public String toString(){
        try {
            return "Quiniela: " + quiniela + 
                   "\nHash de la Quiniela: " + hashQuiniela + 
                   "\nQuiniela válida: " + quinielaValida + 
                   "\nHash Quiniela Válida: " + getHashQuinielaValida();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "No se ha podido generar el toString de la quiniela Válida.";
        }
    }

}
