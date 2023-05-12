package main.java.com.lucasbodin.generateQrCode;


import com.lucasbodin.generateQrCode.BodyQrCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import static main.java.com.lucasbodin.generateQrCode.Create_QR.creationQrCode;
import static main.java.com.lucasbodin.generateQrCode.Create_QR.numberFichier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class controller {

    @PostMapping("/qrcode")
    public String qrcode(@RequestBody BodyQrCode body) {
        String bodyText = body.text;

        String chemin = "./img/output/";
        String nomFichier = "generateQrCode_";
        String nomExtension = ".png";

        try {
            String cheminFichier = numberFichier(chemin, nomFichier, nomExtension);
            System.out.println(creationQrCode(cheminFichier, bodyText, 300, 300) + " réussi");
            return cheminFichier.replace(chemin, "");
        } catch (Exception e) {
            System.out.println("Génération de QrCode d'exemple echoué");
            System.err.println(e.getMessage());
            return "Génération de QrCode d'exemple echoué";
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

