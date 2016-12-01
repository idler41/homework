package com.lfx.homework;

import java.awt.image.BufferedImage;

/**
 * 灰度化处理
 * @Author idler [idler41@163.com]
 * @Date 2016/11/28 下午9:30.
 */
public class ImgGray extends AbstractImg {

    @Override
    protected BufferedImage process(BufferedImage srcImg) {
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        BufferedImage dstImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final int color = srcImg.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                int newPixel = colorToRGB(255, gray, gray, gray);
                dstImg.setRGB(i, j, newPixel);
            }
        }
        return dstImg;
    }

    private int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

    @Override
    protected String ouputFileName() {
        return "gray.png";
    }

    public static void main(String[] args) {
        new ImgGray().excute();

    }
}
