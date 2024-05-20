package com.lucasbodin.generateqrcode;

import java.util.Arrays;

import static com.lucasbodin.generateqrcode.CreateQr.creationQrCodeAvecImageCenter;
import static com.lucasbodin.generateqrcode.CreateQr.numberFichier;

public class Main {

    public static void main(String[] args) {
        String chemin = "./img/output/";
        String nomFichier = "qr_code_2023_etape_";
        String nomExtension = ".png";
        String cheminImgUse = "./img/input/logo_CAP_2023_v2_rogner_transparent.png";

        qrCodeEtape2023(chemin, nomFichier, nomExtension, cheminImgUse);
    }

    private static void qrCodeEtape2023(String chemin, String nomFichier, String nomExtension, String cheminImgUse) {
        String[] etapes = {"etape_0", "LYUpKr6Jwp5J", "MFCJd5deG2LZ", "PLb8tfxH6Ngj", "4HIQ7L8B1Ppx", "DcB423kQ8MW2", "Z2pkN69lkPTC", "lB3JBiZ1Q8Pc", "p8KDBcYKZq1d", "Nl6z6sfAU4qW", "Aztd2XY5Tx7C", "6M0xLTp5fp9q"};
        // Pour chaque élément de etapes, ajouter "courir-a-plusieurs.site/" devant
        etapes = Arrays.stream(etapes).map(s -> "courir-a-plusieurs.site/" + s).toArray(String[]::new);
        qrCodeEtapes(chemin, nomFichier, nomExtension, cheminImgUse, etapes);
    }

    private static void qrCodeEtapes(String chemin, String nomFichier, String nomExtension, String cheminImgUse, String[] etapes) {
        for (String etape : etapes) {
            try {
                System.out.println("Génération de " + etape + " au chemin " + creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), etape, cheminImgUse) + " réussi");
            } catch (Exception e) {
                System.out.println("Génération de " + etape + " echoué");
                System.err.println(e.getMessage());
            }
        }
    }

}
