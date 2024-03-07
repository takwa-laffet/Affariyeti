package tn.esprit.affariety.controllers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QRCodeGenerator {

    public static Image generateQRCode(String content, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

            Image qrCodeImage = convertToImage(bitMatrix);
            return qrCodeImage;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Image convertToImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                pixels[index] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // Black or White
            }
        }

        Image qrCodeImage = new Image(String.valueOf(pixels), width, height, true, true);
        return qrCodeImage;
    }
}
