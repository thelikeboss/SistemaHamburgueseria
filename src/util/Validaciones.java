package util;

import java.util.regex.Pattern;

public class Validaciones {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern TELEFONO_PATTERN = 
        Pattern.compile("^[0-9]{10,15}$");
    
    public static boolean validarEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean validarTelefono(String telefono) {
        return telefono != null && TELEFONO_PATTERN.matcher(telefono).matches();
    }
    
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    public static boolean validarNumeroPositivo(String numero) {
        try {
            double valor = Double.parseDouble(numero);
            return valor >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean validarEnteroPositivo(String numero) {
        try {
            int valor = Integer.parseInt(numero);
            return valor >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}