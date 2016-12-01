package com.lfx.homework;

import java.awt.image.BufferedImage;

/**
 * 对比度与亮度处理
 * @Author idler [idler41@163.com]
 * @Date 2016/11/30 下午10:58.
 */
public class ContrastFilter extends AbstractImg {
    private float contrast = 1.5f; // default value;
    private float brightness = 1.0f; // default value;

    @Override
    protected BufferedImage process(BufferedImage srcImg) {
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();

        BufferedImage dstImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        srcImg.getRGB(0, 0, width, height, inPixels, 0, width);

        // calculate RED, GREEN, BLUE means of pixel
        int index = 0;
        int[] rgbmeans = new int[3];
        double redSum = 0, greenSum = 0, blueSum = 0;
        double total = height * width;
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                redSum += tr;
                greenSum += tg;
                blueSum += tb;
            }
        }

        rgbmeans[0] = (int) (redSum / total);
        rgbmeans[1] = (int) (greenSum / total);
        rgbmeans[2] = (int) (blueSum / total);

        // adjust contrast and brightness algorithm, here
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;

                // remove means
                tr -= rgbmeans[0];
                tg -= rgbmeans[1];
                tb -= rgbmeans[2];

                // adjust contrast now !!!
                tr = (int) (tr * getContrast());
                tg = (int) (tg * getContrast());
                tb = (int) (tb * getContrast());

                // adjust brightness
                tr += (int) (rgbmeans[0] * getBrightness());
                tg += (int) (rgbmeans[1] * getBrightness());
                tb += (int) (rgbmeans[2] * getBrightness());
                outPixels[index] = (ta << 24) | (clamp(tr) << 16) | (clamp(tg) << 8) | clamp(tb);
                dstImg.setRGB(col, row, outPixels[index]);

            }
        }
        return dstImg;
    }

    @Override
    protected String ouputFileName() {
        return "contrast.png";
    }


    public int clamp(int value) {
        return value > 255 ? 255 : (value < 0 ? 0 : value);
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public static void main(String[] args) {
        new ContrastFilter().excute();
    }
}
