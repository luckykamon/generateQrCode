package com.chillyfacts.com;

import java.awt.*;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static java.awt.Color.black;


public class Create_QR {
    public static void main(String[] args) {

        String chemin = "/Users/lucas/Desktop/personal_projects.nosync/generateQrCode/img/output/";
        String nomFichier = "qr_code_2023_etape_";
        String nomExtension = ".png";
        String cheminImgUse = "/Users/lucas/Desktop/personal_projects.nosync/generateQrCode/img/input/logo_CAP_2023_v2_rogner_transparent.png";

//        creationQrCode(numberFichier(chemin, nomFichier, nomExtension), "QrCode d'exemple", 300, 300);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "QrCode d'exemple avec image", cheminImgUse);

        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/etape_0", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/LYUpKr6Jwp5J", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/MFCJd5deG2LZ", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/PLb8tfxH6Ngj", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/4HIQ7L8B1Ppx", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/DcB423kQ8MW2", cheminImgUse);
        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/Z2pkN69lkPTC", cheminImgUse);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/lB3JBiZ1Q8Pc", cheminImgUse);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/p8KDBcYKZq1d", cheminImgUse);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/Nl6z6sfAU4qW", cheminImgUse);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/Aztd2XY5Tx7C", cheminImgUse);
//        creationQrCodeAvecImageCenter(numberFichier(chemin, nomFichier, nomExtension), "courir-a-plusieurs.site/6M0xLTp5fp9q", cheminImgUse);
    }

    private static String numberFichier(String chemin, String nomFichier, String nomExtension) {
        int number = 0;
        String url = chemin + nomFichier + number + nomExtension;
        File f = new File(url);
        while (f.exists() && !f.isDirectory()) {
            number++;
            url = chemin + nomFichier + number + nomExtension;
            f = new File(url);
        }
        return url;
    }

    /**
     * Crée un QrCode contenant qrCodeData et crée une image au format png
     *
     * @param filePath   Adresse où sera stocké le fichier
     * @param qrCodeData Texte que renverra le Qrcode une fois scanné
     * @param width      Largeur du QrCode
     * @param height     Hauteur du QrCode
     */
    public static void creationQrCode(String filePath, String qrCodeData, int width, int height) {
        try {
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height, hintMap);
            MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), Paths.get(filePath));

            System.out.println("QR Code image created successfully!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Crée un QrCode contenant qrCodeData en insérant une image et crée une image au format png
     *
     * @param filePath   Adresse où sera stocké l'image du QrCode
     * @param qrCodeData Texte que renverra le Qrcode une fois scanné
     * @param chemin_img Adresse de l'image à insérer dans le QrCode
     */
    public static void creationQrCodeAvecImageCenter(String filePath, String qrCodeData, String chemin_img) {

        try {
            BufferedImage myPicture = ImageIO.read(new File(chemin_img)); //image a insérer
            int width = (myPicture.getWidth() + myPicture.getHeight()) * 2;
            int height = (myPicture.getWidth() + myPicture.getHeight()) * 2;

            //Création du QrCode
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, width, height, hintMap);
            MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
                    Paths.get(filePath));

            //Ajout de l'image dans le QrCode
            File qrCodeFile = new File(filePath);
            BufferedImage qrCodeGray = ImageIO.read(qrCodeFile);
            ColorConvertOp op = new ColorConvertOp(null);
            BufferedImage qrCode = new BufferedImage(qrCodeGray.getWidth(), qrCodeGray.getHeight(), BufferedImage.TYPE_INT_RGB); // créer une nouvelle image en couleur
            op.filter(qrCodeGray, qrCode);
            int rgb;
            Color pink_logo_cap = Color.decode("#DAA1CA"); // new color
            for (int y = 0; y < qrCode.getHeight(); y++) {
                for (int x = 0; x < qrCode.getWidth(); x++) {
                    rgb = qrCode.getRGB(x, y);
                    if (new Color(rgb).equals(black)) {
                        qrCode.setRGB(x, y, pink_logo_cap.getRGB());
                    }
                }
            }
            for (int i = 0; i < myPicture.getWidth(); i++) {
                for (int j = 0; j < myPicture.getHeight(); j++) {
                    rgb = myPicture.getRGB(i, j);
                    if (rgb != 0) { //Si ce n'est pas transparent
                        qrCode.setRGB(i + qrCode.getWidth() / 2 - myPicture.getWidth() / 2, j + qrCode.getHeight() / 2 - myPicture.getHeight() / 2, rgb);
                    }
                }
            }
            if (!qrCodeFile.delete()) {
                System.out.println("Une erreur est survenu lors de la suppresion d'un fichier temporaire");
            }
            ImageIO.write(qrCode, "png", new File(filePath));

            System.out.println("QR Code image created successfully! " + filePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
