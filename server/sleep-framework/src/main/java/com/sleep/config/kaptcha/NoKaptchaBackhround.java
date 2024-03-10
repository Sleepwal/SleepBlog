package com.sleep.config.kaptcha;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/24 20:51
 */
public class NoKaptchaBackhround extends Configurable implements BackgroundProducer {

    public NoKaptchaBackhround(){
    }

    @Override
    public BufferedImage addBackground(BufferedImage bufferedImage) {
        int width = 400;
        int height = 125;
        BufferedImage imageWithBackground = new BufferedImage(width, height, 1);
        Graphics2D graph = (Graphics2D)imageWithBackground.getGraphics();
        graph.fill(new Rectangle2D.Double(0.0D, 0.0D, (double)width, (double)height));
        graph.drawImage(bufferedImage, 0, 0, null);
        return imageWithBackground;
    }
}

