package com.chillyfacts.com;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.chillyfacts.com.Create_QR.*;

public class exemple {
    public static void main(String[] args) {
        String chemin = "./img/output/";
        String nomFichier = "qr_code_2023_etape_";
        String nomExtension = ".png";
        String cheminImgUse = "./img/input/logo_CAP_2023_v2_rogner_transparent.png";

        creationQrCode(numberFichier(chemin, nomFichier, nomExtension), "QrCode d'exemple", 300, 300);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/etape_0", cheminImgUse);

    }
}
