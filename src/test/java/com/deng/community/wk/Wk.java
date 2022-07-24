package com.deng.community.wk;

import java.io.IOException;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public class Wk {
    public static void main(String[] args) {
        String cmd="D:/javaWeb/wkhtmltopdf/bin/wkhtmltoimage   https://www.nowcoder.com  D:/javaWeb/data/wk-images/1.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
