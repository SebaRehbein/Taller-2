/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mensajeoculto;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sebastian
 */
public class MensajeOculto {

    public static void main(String[] args) {
    // Direccion de la imagen
    String inputPath = "C:\\Users\\Sebastian\\Desktop\\Universidad\\imagen_taller\\maradona.png";
    
    // Mensaje
    String message = "Este mensaje esta oculto en la imagen";
    
    // Ocultar el mensaje en la imagen
    hideMessage(inputPath, message);
}

private static void hideMessage(String inputPath, String message) {
    try {
        // Cargar la imagen
        BufferedImage image = ImageIO.read(new File(inputPath));
        int width = image.getWidth();
        int height = image.getHeight();

        // Array, el mensaje a bytes
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;

        // Verificar si el mensaje se puede ocultar en la imagen
        int pixelCount = width * height;
        int requiredPixels = messageLength * 8 + 32; // 8 bits por byte + 32 bits para el tamaño del mensaje
        
        if (requiredPixels > pixelCount) {
            System.out.println("El mensaje es demasiado largo para ocultarlo en la imagen.");
            return;
        }

        // Ocultar el tamaño del mensaje en los primeros 32 bits (4 bytes) de la imagen
        int bitIndex = 0;
        
        for (int i = 0; i < 4; i++) {
            int sizeByte = (messageLength >> (i * 8)) & 0xFF;
            int pixelIndex = i * 2;
            setPixelLSB(image, pixelIndex, sizeByte, bitIndex);
            bitIndex += 2;
        }

        // Ocultar cada byte del mensaje en los píxeles de la imagen
        for (int i = 0; i < messageLength; i++) {
            byte messageByte = messageBytes[i];
            int pixelIndex = (i + 4) * 2;
            setPixelLSB(image, pixelIndex, messageByte, bitIndex);
            bitIndex += 8;
        }

        // Guardar la imagen con el mensaje oculto
        String outputImagePath = "imagen_modificada.png";
        ImageIO.write(image, "png", new File(outputImagePath));
        System.out.println("El mensaje ha sido ocultado en la imagen correctamente.");

    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static void setPixelLSB(BufferedImage image, int pixelIndex, int value, int bitIndex) {
    int x = pixelIndex % image.getWidth();
    int y = pixelIndex / image.getWidth();

    Color color = new Color(image.getRGB(x, y));
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();

    // Modificar el bit menos significativo del componente de color
    int mask = 1 << bitIndex;
    red = (red & ~1) | ((value & mask) >>> bitIndex);
    green = (green & ~1) | ((value & mask) >>> bitIndex);
    blue = (blue & ~1) | ((value & mask) >>> bitIndex);

    // Actualizar el color del píxel
    Color modifiedColor = new Color(red, green, blue);
    image.setRGB(x, y, modifiedColor.getRGB());
}

}
