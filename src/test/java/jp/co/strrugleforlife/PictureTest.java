package jp.co.strrugleforlife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class PictureTest {

    public static void main(String[] args) {

        BufferedImage image = null;

        // すくりむ実施日
        Calendar matchDay = Calendar.getInstance();

        // 現在の日付を取得
        Calendar todayCal = Calendar.getInstance();

        // 今週の金曜日を取得
        matchDay.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);

        // ロースター作成時点の日付が今週金曜日よりも後ろの場合
        if (todayCal.after(matchDay)) {
            // ７日加算
            matchDay.add(Calendar.DATE,7);
        }

        //日付の表示のフォーマットを作成
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM/d");

        //表示用に文字列に変換
        String dateString = dateFormatter.format(matchDay.getTime());

        //  ファイル読み込み
        try
        {
              image = ImageIO.read(new File("src/main/resources/static/image/roster.jpg"));
        } catch (Exception e) {
              e.printStackTrace();
        }

        Graphics graphics = image.createGraphics();

        //  いたずら書き
        Font titleFont = new Font("HGP創英角ﾎﾟｯﾌﾟ体",Font.ITALIC,30);

        Font clanFont = new Font("HGP創英角ﾎﾟｯﾌﾟ体",Font.ITALIC,25);

        graphics.setFont(titleFont);
        graphics.setColor(Color.darkGray );
        graphics.drawString("すとりむ　ろーすたー",10,50);
        // すくりむの日付を出力
        graphics.drawString(dateString, 950, 50);


        graphics.setFont(clanFont);

        // 1 ～ 10 No出力位置 X
        int x1 = 50;

        // 1 ～ 10 クラン名出力位置 X
        int x2 = 100;
        // 1 ～ 10 クラン名出力位置 Y
        int y2 = 80;

        // クラン名間隔 Y
        int y2Plus = 50;

        int y2Plus_line = 5;

        // 1 ～ 10 クラン名出力
        for (int i = 1; i <= 10; i++) {
            // クラン名出力
            graphics.drawString(i + ".", x1, y2 += y2Plus);
            graphics.drawString("Strrugle For Life", x2, y2);
            graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
        }

        // 11 ～ 20 No出力位置 X
        x1 = 690;

        // 11 ～ 20 クラン名出力位置 X
        x2 = 740;
        // 11 ～ 20 クラン名出力位置 Y
        y2 = 80;

        // 11 ～ 20 クラン名出力
        for (int i = 1, no = 11; i <= 10; i++, no++) {
            // クラン名出力
            graphics.drawString(no + ".", x1, y2 += y2Plus);
            graphics.drawString("Strrugle For Life", x2, y2);
            graphics.drawLine(x1, y2 + y2Plus_line, 1040, y2 + y2Plus_line);
        }

//        graphics.drawString("2.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("3.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("4.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("5.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("6.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("7.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("8.", x1, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("9.", 50, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
//
//        graphics.drawString("10.", 50, y2 += y2Plus);
//        graphics.drawString("Strrugle For Life", x2, y2);
//        graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);

        //  ファイル保存
            try
            {
                  ImageIO.write(image, "jpeg", new File("src/main/resources/static/image/rosterConfirm.jpg"));
            }
            catch (Exception e)
            {
                  e.printStackTrace();
            }

            System.out.println("終わりました");

    }
}

