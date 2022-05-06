package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalStorageManipulations {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private String initialData =
            "{\"pushNotificationsEnabled\":true,\"acceptCookies\":true,\"appLanguage\":\"de\",\"device\":{\"token\":null,\"identifier\":null,\"domain\":null,\"tenantDeviceId\":null}}";

    public LocalStorageManipulations(WebDriver driver, WebDriverWait webDriverWait, Boolean acceptCookies) {
        this.driver = driver;
        wait = webDriverWait;
        if (!acceptCookies) {
            initialData = initialData.replace("\"acceptCookies\":true", "\"acceptCookies\":false");
        }
    }

    public void initializeApp(Duration duration) {
        AtomicInteger tries = new AtomicInteger();
        wait.until((driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete")));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        wait.pollingEvery(duration);
        wait.until(driver12 -> {
            // Just to show you how polling works
            System.out.println("Retrying at : " +new Date());

            WebStorage webStorage = (WebStorage) new Augmenter().augment(driver);
            // using local storage
            LocalStorage localStorage = webStorage.getLocalStorage();
            localStorage.setItem("acceptCookies", String.valueOf(true));
            localStorage.setItem("vuex", initialData);
            String vuexGetItems = localStorage.getItem("vuex");
            tries.getAndIncrement();
            // finish interval cycle if initial local storage was changed
            return vuexGetItems.equals(initialData) && tries.get() == 5;
        });
    }
}
