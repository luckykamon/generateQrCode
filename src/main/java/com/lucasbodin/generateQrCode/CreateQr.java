package com.lucasbodin.generateqrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.awt.Color.black;


public class CreateQr {

    public static String generateRandomString(int min, int max) {
        int length = (new Random()).nextInt(max - min + 1) + min;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

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
        String number = generateRandomString(1,3);
        String url = chemin + nomFichier + nomExtension;
        File f = new File(url);
        while (f.exists() && !f.isDirectory()) {
            url = chemin + nomFichier + number + nomExtension;
            f = new File(url);
            number = generateRandomString(1,3);
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

    public static BufferedImage resizeImage(BufferedImage originalImage, int maxWidth, int maxHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calculer les nouvelles dimensions proportionnelles
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        if (originalWidth > maxWidth || originalHeight > maxHeight) {
            double aspectRatio = (double) originalWidth / originalHeight;

            if (aspectRatio > 1) {
                newWidth = maxWidth;
                newHeight = (int) (newWidth / aspectRatio);
            } else {
                newHeight = maxHeight;
                newWidth = (int) (newHeight * aspectRatio);
            }
        }

        // Créer une nouvelle image BufferedImage avec les nouvelles dimensions
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();

        // Redimensionner l'image en utilisant la méthode de dessin avec interpolation
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        return resizedImage;
    }

    /**
     * Crée un QrCode contenant qrCodeData en insérant une image et crée une image au format png
     *
     * @param filePath   Adresse où sera stocké l'image du QrCode
     * @param qrCodeData Texte que renverra le Qrcode une fois scanné
     * @param chemin_img Adresse de l'image à insérer dans le QrCode
     */
    public static String creationQrCodeAvecImageCenter(String filePath, String qrCodeData, String chemin_img, String colorString) throws Exception {
        BufferedImage myPicture = resizeImage(ImageIO.read(new File(chemin_img)), 300, 300); //image a insérer

        int width = (myPicture.getWidth() + myPicture.getHeight()) * 2;
        int height = (myPicture.getWidth() + myPicture.getHeight()) * 2;

        //Création du QrCode
        creationQrCode(filePath, qrCodeData, width, height);
        System.out.println("width : " + width + " height : " + height);

        //Ajout de l'image dans le QrCode
        File qrCodeFile = new File(filePath);
        BufferedImage qrCodeGray = ImageIO.read(qrCodeFile);
        ColorConvertOp op = new ColorConvertOp(null);
        BufferedImage qrCode = new BufferedImage(qrCodeGray.getWidth(), qrCodeGray.getHeight(), BufferedImage.TYPE_INT_RGB); // créer une nouvelle image en couleur
        op.filter(qrCodeGray, qrCode);
        int rgb;
        Color personaliseColor = Color.decode(colorString); // new color
        for (int y = 0; y < qrCode.getHeight(); y++) {
            for (int x = 0; x < qrCode.getWidth(); x++) {
                rgb = qrCode.getRGB(x, y);
                if (new Color(rgb).equals(black)) {
                    qrCode.setRGB(x, y, personaliseColor.getRGB());
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

    public static String creationQrCodeAvecImageCenter(String filePath, String qrCodeData, String chemin_img) throws Exception {
        return creationQrCodeAvecImageCenter(filePath, qrCodeData, chemin_img, "#000000");
    }

}
