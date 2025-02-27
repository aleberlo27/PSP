import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public class Quiniela implements Serializable {
    private static final long UID = 20L; 
    private String nombre;
    private String quiniela;
    private String hashQuiniela;
    public static String quinielaValida = "2X12X1X22X112XX";

    public Quiniela() throws NoSuchAlgorithmException{
        quiniela = MetodosEstaticos.generarQuinielaAleatoria();
        this.nombre = MetodosEstaticos.generarNombreAleatorio();
        //hashQuiniela = MetodosEstaticos.generarHashMD5(quiniela); 
    }
    public String getNombre(){
        return this.nombre;
    }
    
    public String getHashQuiniela(){
        return hashQuiniela;
    }

    public String getQuiniela(){
        return quiniela;
    }

    public void setHashQuiniela(String datos) throws NoSuchAlgorithmException{
        this.quiniela = datos;
        this.hashQuiniela = MetodosEstaticos.generarHashMD5(quiniela);
    }

    public String getHashQuinielaValida() throws NoSuchAlgorithmException{
        String hashQuinielaValida = MetodosEstaticos.generarHashMD5(quinielaValida);
        return hashQuinielaValida;
    }

    public boolean esQuinielaValida() {
        return quiniela.matches("[12X]{15}");
    }

    public String toString(){
        try {
            return "\nQuiniela válida: " + quinielaValida + 
                   "\nHash Quiniela Válida: " + getHashQuinielaValida();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "No se ha podido generar el toString de la quiniela Válida.";
        }
    }
}
