package com.lfx.homework;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author idler [idler41@163.com]
 * @Date 2016/11/28 下午9:30.
 */
public abstract class AbstractImg {

    protected void excute(){
        try {
            File file = new File("test.png");
            BufferedImage imgToProcess = ImageIO.read(file);
            BufferedImage imgProcessed = process(imgToProcess);
            File newFile = new File(ouputFileName());
            ImageIO.write(imgProcessed, "png", newFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected abstract BufferedImage process(BufferedImage srcImg);

    protected abstract String ouputFileName();

}
