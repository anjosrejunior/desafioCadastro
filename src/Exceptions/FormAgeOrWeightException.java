package Exceptions;

import java.util.regex.*;

public class FormAgeOrWeightException extends RuntimeException {
    public FormAgeOrWeightException(String message) {
        super(message);
    }

    public static boolean regexOnlyNumberError(String age){
        boolean isCorretAge;
        Pattern pattern = Pattern.compile("[^0-9 ,.]");
        Matcher matcher = pattern.matcher(age);

        if (matcher.find()) {
            return isCorretAge = true;
        }
        return isCorretAge = false;
    }
}
