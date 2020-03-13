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

    @PostMapping("/upload")////new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) throws IOException {

    	if (file.isEmpty()) {
    	    return "roster";
    	  }
    	List<String> lines = new ArrayList<String>();
    	List<Clan> clanList = new ArrayList<Clan>();

        String line = null;
        try {
            InputStream stream = file.getInputStream();
            Reader reader = new InputStreamReader(stream);
            BufferedReader buf= new BufferedReader(reader);
            while((line = buf.readLine()) != null) {
                lines.add(line);
                Clan clan = new Clan();
                clan.setClan(line);
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