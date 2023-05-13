package main.java.com.lucasbodin.generateQrCode;


import com.lucasbodin.generateQrCode.BodyQrCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static main.java.com.lucasbodin.generateQrCode.Create_QR.*;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
public class controller {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index"); // Nom du fichier HTML sans l'extension
        return modelAndView;
//        return "Documentation can be found here: <a href=\"https://documenter.getpostman.com/view/12914728/2s93ecwAaw\" target=\"blank\">https://documenter.getpostman.com/view/12914728/2s93ecwAaw</a>";
    }

    @PostMapping("/qrcode")
    public String qrcode(@RequestBody BodyQrCode body) {
        String bodyText = body.text;

        String chemin = "./img/output/";
        String nomFichier = "generateQrCode_";
        String nomExtension = ".png";

        String cheminFichier = numberFichier(chemin, nomFichier, nomExtension);

        try {
            System.out.println(creationQrCode(cheminFichier, bodyText, 300, 300) + " réussi");
            return cheminFichier.replace(chemin, "");
        } catch (Exception e) {
            System.out.println("Génération de " + cheminFichier + " echoué");
            System.err.println(e.getMessage());
            return "Génération de QrCode d'exemple echoué";
        }
    }

    @PostMapping("/uploadQrCode")
    public String uploadQrCode(@RequestParam("text") String text, @RequestParam("file") MultipartFile file) {
        String chemin = "./img/input/";
        String nomFichier = "uploadedQrCode_";
        String nomExtension = ".png";

        try {
            String cheminFichier = numberFichier(chemin, nomFichier, nomExtension);

            // Sauvegarder le fichier dans le dossier spécifié
            byte[] bytes = file.getBytes();
            Path path = Paths.get(cheminFichier);
            Files.write(path, bytes);

            String cheminQrCode = numberFichier("./img/output/", "generateQrCode_", ".png");

            try {
                System.out.println(creationQrCodeAvecImageCenter(cheminQrCode, text, cheminFichier) + " réussi");
            } catch (Exception e) {
                System.out.println("Génération de " + cheminQrCode + " echoué");
                System.err.println(e.getMessage());
                return "Génération de QrCode d'exemple echoué";
            }

            return cheminQrCode.replace("./img/output/", "");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'upload du QrCode");
            e.printStackTrace();
            return "Erreur lors de l'upload du QrCode";
        }
    }

    @GetMapping("/getQrCode/{name}.png")
    public ResponseEntity<byte[]> getQrCode(@PathVariable("name") String name) throws IOException {
        String imagePath = "./img/output/" + name + ".png";
        Path path = Paths.get(imagePath);
        File imageFile = path.toFile();

        if (imageFile.exists()) {
            try (InputStream inputStream = new FileInputStream(imageFile)) {
                byte[] imageBytes = FileCopyUtils.copyToByteArray(inputStream);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        }

        // Si l'image n'existe pas, renvoyer une erreur 404
        return ResponseEntity.notFound().build();
    }

}

