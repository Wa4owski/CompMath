import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Graph {

    private final static String base = new String("https://www.wolframalpha.com/input?i=+plot+");

    @Deprecated
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Deprecated
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String buildRequestToWolfram(String func, double left, double right){
        func = func.replaceAll("\\+", "%2B")
                   .replaceAll("\\^", "%5E");
        String interval = "+from+" + (left-1d) + "+to+" + (right+1d);
        return base + func + interval;
    }

    public static String buildRequestToWolfram(String equation1, String equation2, double left, double right){
        equation1 = equation1.replaceAll("\\+", "%2B")
                             .replaceAll("\\^", "%5E");
        equation2 = equation2.replaceAll("\\+", "%2B")
                             .replaceAll("\\^", "%5E");
        String system = "%7B" + equation1 + "," + equation2 + "%7D";
        String interval = "+from+" + (left - 1d) + "+to+" + (right + 1d);
        interval = "+x1" + interval + "+x2" + interval;
        return base + system + interval;
    }

    public static boolean downloadImgByURL(URL url) {
        try (InputStream in = new BufferedInputStream(url.openStream());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            FileOutputStream fos = new FileOutputStream("C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР2\\Lab2\\graph_image.jpg");
            fos.write(response);
            fos.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static URL openWebpageAndGetImageUrl(URL url) {

        System.setProperty("webdriver.gecko.driver","C:\\WebProjects\\External\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        //implicit wait
        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        //URL launch
        driver.get(url.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("_3c8e"), 2));
        List<WebElement> list = driver.findElements(By.className("_3c8e"));

//        System.out.println("size = " + list.size());
//        for (WebElement l : list) {
//            String p = l.getAttribute("src");
//            System.out.println("Page Source is : " + p);
//        }
        try {
            return new URL(list.get(1).getAttribute("src"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //driver.close();
        return null;
    }

    public static void openWolframAndDownloadGraphImage(URL url){
        downloadImgByURL(openWebpageAndGetImageUrl(url));
    }
}
