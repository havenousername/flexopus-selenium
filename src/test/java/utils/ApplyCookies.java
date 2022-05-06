package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApplyCookies {
    private final WebDriver driver;

    public ApplyCookies(WebDriver driver) {
        this.driver = driver;
    }

    public void apply() {
        File file = new File("cookies.data");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineParse = line.split(";");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = formatter.parse(lineParse[4]);
                Cookie cookie = new Cookie(
                        lineParse[0],
                        lineParse[1],
                        lineParse[2],
                        lineParse[3],
                        date,
                        Boolean.getBoolean(lineParse[5])
                );
                driver.manage().addCookie(cookie);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveCookies() {
        File file = new File("cookies.data");
        try {
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (Cookie cookie : driver.manage().getCookies()) {
                writer.write(
                        cookie.getName() + ";" +
                                cookie.getValue() + ";" +
                                cookie.getDomain() + ";" +
                                cookie.getPath() + ";" +
                                cookie.getExpiry() + ";" +
                                cookie.isSecure());
                writer.newLine();
            }

            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
