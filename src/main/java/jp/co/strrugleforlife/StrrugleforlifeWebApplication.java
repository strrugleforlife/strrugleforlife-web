package jp.co.strrugleforlife;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.strrugleforlife.domain.Clan;

@Controller
@SpringBootApplication
@EnableAutoConfiguration
@RequestMapping("/")
public class StrrugleforlifeWebApplication {

    @GetMapping
    public String home() {
    	    return "manage";
    }

    @GetMapping("roster")
    public String roster() {
        return "roster";
    }

    /**
     * アップロードファイル読み込み
     * @param file
     * @param redirectAttributes
     * @param model
     * @return
     * @throws IOException
     * @throws FontFormatException
     */
    @PostMapping("/upload")////new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException, FontFormatException {

        // 読み込んだCSVファイルの内容を格納
        List<String> lines = new ArrayList<String>();
        List<Clan> clanList = new ArrayList<Clan>();
        int no = 1;
        String line = null;

        // CSVファイルが空の場合は処理しない
        if (file.isEmpty()) {
//            return "roster";
        }

        try {
            InputStream stream = file.getInputStream();
            Reader reader = new InputStreamReader(stream);
            BufferedReader buf= new BufferedReader(reader);
            while((line = buf.readLine()) != null) {
                Clan clan = new Clan();
                lines.add(line);
                clan.setClan(line);
                clan.setNo(no);
                no++;
                clanList.add(clan);
            }

            line = buf.readLine();

        } catch (IOException e) {
            line = "Can't read contents.";
            lines.add(line);
            e.printStackTrace();
        }

        // ロースター作成

        model.addAttribute("clanList", clanList);
        model.addAttribute("base64data", createRoster(lines).toString());


     // レスポンスデータとして返却
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(b.length);
//        return new HttpEntity<byte[]>(b, headers);

        return "rosterConfirm";
    }


    // ロースターを作成
    public StringBuffer  createRoster(List<String> clanList) throws IOException, FontFormatException {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);


        byte[] bImage = null;

        StringBuffer data = new StringBuffer();

        // BufferedImageを作成
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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

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
        Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/main/resources/static/font/KTEGAKI.ttf"));

        titleFont = titleFont.deriveFont(27F);

        Font clanFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/main/resources/static/font/KTEGAKI.ttf"));

        clanFont = clanFont.deriveFont(25F);


        String title = "すとりむ　ろーすたー";


        graphics.setFont(titleFont);
        graphics.setColor(Color.darkGray );
        graphics.drawString(title,50,50);
        // すくりむの日付を出力
        graphics.drawString(dateString, 870, 50);


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

        int clanNumber = 0;

        if (!CollectionUtils.isEmpty(clanList)) {
            clanNumber = clanList.size();
        }



        // 1 ～ 10 クラン名出力
        for (int i = 1, index = 0; i <= 10; i++, index++) {
            // Noを出力
            graphics.drawString(i + ".", x1, y2 += y2Plus);

            // Listの中身が殻の場合は空欄出力
            if (i <= clanNumber) {
                graphics.drawString(clanList.get(index), x2, y2);
                graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
            } else {
                graphics.drawString("", x2, y2);
                graphics.drawLine(x1, y2 + y2Plus_line, 400, y2 + y2Plus_line);
            }

        }


        // 11 ～ 20 No出力位置 X
        x1 = 690;

        // 11 ～ 20 クラン名出力位置 X
        x2 = 740;
        // 11 ～ 20 クラン名出力位置 Y
        y2 = 80;

        // 11 ～ 20 クラン名出力
        for (int i = 1, index = 10, no = 11; i <= 10; i++, no++, index++) {
            // クラン名出力
            graphics.drawString(no + ".", x1, y2 += y2Plus);

         // Listの中身が殻の場合は空欄出力
            if (index < clanNumber) {
                graphics.drawString(clanList.get(index), x2, y2);
                graphics.drawLine(x1, y2 + y2Plus_line, 1040, y2 + y2Plus_line);
            } else {
                graphics.drawString("", x2, y2);
                graphics.drawLine(x1, y2 + y2Plus_line, 1040, y2 + y2Plus_line);
            }
        }

        //  ファイル保存
            try
            {
                  ImageIO.write(image, "jpeg", new File("src/main/resources/static/image/rosterConfirm.jpg"));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baos);


             // 読み終わった画像をバイト出力へ。
                ImageIO.write(image, "jpeg", bos);
                bos.flush();
                bos.close();
                bImage = baos.toByteArray();

                // バイト配列→BASE64へ変換する
//                Base64 base64 = new Base64();
//                byte[] encoded = Base64.encodeBase64(bImage);
    //
//                String base64Image = new String(encoded,"ASCII");
    //
                String base64 = new String(Base64.encodeBase64(bImage),"ASCII");






//                String base64 = new String(Base64.encodeBase64(image.getByte()),"ASCII");
                data.append("data:image/jpeg;base64,");
                data.append(base64);
            }
            catch (Exception e)
            {
                  e.printStackTrace();
            }

         // byteへ変換
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            bout.write(ImageIO.read(image));
//
//            ResourceLoader resourceLoader = null;
//
//            Resource resource = resourceLoader.getResource(image.toString());
//            InputStream images = resource.getInputStream();


            return data;
//            return bImage;


//            System.out.println("終わりました");
    }

    public static void main(String[] arguments) {
        SpringApplication.run(StrrugleforlifeWebApplication.class, arguments);
    }

}