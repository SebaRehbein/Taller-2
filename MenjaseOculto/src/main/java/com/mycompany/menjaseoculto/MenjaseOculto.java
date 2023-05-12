/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.menjaseoculto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Sebastian
 */
public class MenjaseOculto {
    public static void Esteganografia (File imagen, String mensaje, File imagenOculta) throws IOException {
        BufferedImage img = ImageIO.read(imagen);   
    }
}

