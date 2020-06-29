package com.detector.detector.module;

import java.util.Arrays;
import java.util.List;

public class Validator {
    public static final List<String> BASE_NITROGENADA = Arrays.asList("A","T","C","G");
    public static final int VALID_SEQUENCE = 4;

    /**
     * Valida si un DNA es valido
     * @param dna DNA
     * @return (true/false)
     */
    public static boolean isValidDNA(String[] dna) {
        return dna.length == Arrays.stream(dna).filter(fragment -> isCorrectBaseAndLength(dna.length, fragment)).count();
    }

    /**
     * Valida que un fragmento cumpla con el tamanio y la base nitrogenada
     * @param lengthBaseDna Tamanio del array de DNA
     * @param dnaPart Fragmento del DNA
     * @return (true/false)
     */
    private static boolean isCorrectBaseAndLength(int lengthBaseDna, String dnaPart) {
        if (lengthBaseDna < VALID_SEQUENCE) return false;
        return lengthBaseDna == dnaPart.length() && isBaseNitrogenada(dnaPart);
    }

    /**
     * Valida si un fragmento del DNA cumple con la base nitrogenada
     * @param dnaPart Fragmento del DNA
     * @return (true/false)
     */
    private static boolean isBaseNitrogenada(String dnaPart) {
        return dnaPart.length() == Arrays.stream(dnaPart.split("")).filter(BASE_NITROGENADA::contains).count();
    }
}
