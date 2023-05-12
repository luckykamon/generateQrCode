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

    /**
     * Génère un chemin de fichier unique en ajoutant un numéro à la fin du nom du fichier.
     * Le numéro est incrémenté jusqu'à ce qu'un chemin de fichier unique soit trouvé.
     *
     * @param chemin       Le répertoire où le fichier sera enregistré.
     * @param nomFichier   Le nom de base du fichier.
     * @param nomExtension L'extension du fichier (incluant le point).
     * @return Le chemin complet du nouveau fichier avec le numéro ajouté à la fin du nom du fichier.
     */
    public static String numberFichier(String chemin, String nomFichier, String nomExtension) {
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
    public static String creationQrCode(String filePath, String qrCodeData, int width, int height) throws Exception {
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height, hintMap);
        MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), Paths.get(filePath));

        return filePath;
    }

    /**
     * Crée un QrCode contenant qrCodeData en insérant une image et crée une image au format png
     *
     * @param filePath   Adresse où sera stocké l'image du QrCode
     * @param qrCodeData Texte que renverra le Qrcode une fois scanné
     * @param chemin_img Adresse de l'image à insérer dans le QrCode
     */
    public static String creationQrCodeAvecImageCenter(String filePath, String qrCodeData, String chemin_img) throws Exception {
        BufferedImage myPicture = ImageIO.read(new File(chemin_img)); //image a insérer
        int width = (myPicture.getWidth() + myPicture.getHeight()) * 2;
        int height = (myPicture.getWidth() + myPicture.getHeight()) * 2;

        //Création du QrCode
        creationQrCode(filePath, qrCodeData, width, height);

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

        return filePath;
    }

}
