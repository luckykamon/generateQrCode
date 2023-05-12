package main.java.com.lucasbodin.generateQrCode;

import static main.java.com.lucasbodin.generateQrCode.Create_QR.*;

public class exemple {
    public static void main(String[] args) {
        String chemin = "./img/output/";
        String nomFichier = "qr_code_exemple_";
        String nomExtension = ".png";
        String cheminImgUse = "./img/input/logo_CAP_2023_v2_rogner_transparent.png";

        try {
            System.out.println(creationQrCode(numberFichier(chemin, nomFichier, nomExtension), "QrCode d'exemple", 300, 300) + " réussi");
        } catch (Exception e) {
            System.out.println("Génération de QrCode d'exemple echoué");
            System.err.println(e.getMessage());
        }
        try {
            System.out.println(creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/etape_0", cheminImgUse)+ " réussi");
        } catch (Exception e) {
            System.out.println("Génération de courir-a-plusieurs.site/etape_0 echoué");
            System.err.println(e.getMessage());
        }
    }
}
