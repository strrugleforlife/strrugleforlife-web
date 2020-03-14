package jp.co.strrugleforlife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     */
    @PostMapping("/upload")////new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {

        // 読み込んだCSVファイルの内容を格納
        List<String> lines = new ArrayList<String>();
        List<Clan> clanList = new ArrayList<Clan>();
        int no = 1;
        String line = null;

        // CSVファイルが空の場合は処理しない
        if (file.isEmpty()) {
            return "roster";
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

        model.addAttribute("clanList", clanList);

        return "rosterConfirm";
    }

    public static void main(String[] arguments) {
        SpringApplication.run(StrrugleforlifeWebApplication.class, arguments);
    }

}