package com.attendance.server.controller;

import com.attendance.server.service.QrcodeGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qrcode")
public class QrController {

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrCode(@RequestParam String token) {
        try {
            // Generates a 250x250 pixel QR code containing the session token
            byte[] qrImage = QrcodeGenerator.generateQRCodeImage(token, 250, 250);
            return ResponseEntity.ok(qrImage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}