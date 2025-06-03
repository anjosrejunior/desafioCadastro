package Exceptions;

import java.util.regex.*;

public class FormNameException extends Exception {

    public FormNameException(String message) {
        super(message);
    }
    public static boolean regexNameError(String name){
        boolean isSpecialCaracter;
        Pattern pattern = Pattern.compile("[^A-Za-záàãâéêíóôõúçÁÀÃÂÉÊÍÓÔÕÚÇ ]");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            return isSpecialCaracter = true;
        }
        return isSpecialCaracter = false;
    }
}
