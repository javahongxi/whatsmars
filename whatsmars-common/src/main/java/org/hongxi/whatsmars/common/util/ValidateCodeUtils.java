package org.hongxi.whatsmars.common.util;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by jenny on 4/15/15.
 */
public class ValidateCodeUtils {

    private static final int WIDTH = 85;
    private static final int HEIGHT = 40;


    /**
     * 生成验证码图片
     * @return key为code字符串，value位图片
     */
    public static Map.Entry<String,BufferedImage> generate() {

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //在内存中创建图象
        Graphics2D g = image.createGraphics(); //获取图形上下文

        //设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));  //设定字体
        //随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));

        Random random = new Random(); //生成随机类

        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuffer sb = new StringBuffer(); //取随机产生的认证码(4位数字)

        for (int i = 0; i < 4; i++) {
            String code = String.valueOf(random.nextInt(10));
            sb.append(code);
            //将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(code, 13 * i + 18, 27);
        }

        Map<String,BufferedImage> map = new HashMap<String, BufferedImage>();
        map.put(sb.toString(),image);

        return map.entrySet().iterator().next();
    }

    /*
     * 给定范围获得随机颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
