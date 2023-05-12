package main.java.com.lucasbodin.generateQrCode.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static main.java.com.lucasbodin.generateQrCode.Create_QR.creationQrCode;
import static main.java.com.lucasbodin.generateQrCode.Create_QR.numberFichier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class HelloController {

    @PostMapping("/qrcode")
    public String hello(@RequestBody String body) {
        System.out.println("Request Body: " + body);
        String chemin = "./img/output/";
        String nomFichier = "generateQrCode_";
        String nomExtension = ".png";

        try {
            System.out.println(creationQrCode(numberFichier(chemin, nomFichier, nomExtension), "QrCode d'exemple v2", 300, 300) + " réussi");
        } catch (Exception e) {
            System.out.println("Génération de QrCode d'exemple echoué");
            System.err.println(e.getMessage());
        }
        return "Hello, World! v4";
    }
}

