import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class Graph {

    private final static String base = "https://www.wolframalpha.com/input?i=+plot+";
    private WebDriver driver;

    public Graph(){
        System.setProperty("webdriver.gecko.driver","C:\\WebProjects\\External\\geckodriver.exe");
        driver = new FirefoxDriver();
    }
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
        func = func.replaceAll("\\+-", "%2D")
                .replaceAll("\\+", "%2B")
                   .replaceAll("\\^", "%5E")
                   .replaceAll("\\(", "%28")
                   .replaceAll("\\)", "%29");
        String interval = "+from+" + String.format("%.3f", (left - 1d)).replace(",", ".")
                + "+to+" + String.format("%.3f", (right + 1d)).replace(",", ".");
        return base + func + interval;
    }

    public static String buildRequestToWolfram(String equation1, String equation2, double left, double right){
        equation1 = equation1.replaceAll("\\+", "%2B")
                             .replaceAll("\\^", "%5E");
        equation2 = equation2.replaceAll("\\+", "%2B")
                             .replaceAll("\\^", "%5E");
        String system = "%7B" + equation1 + "," + equation2 + "%7D";
        String interval = "+from+" + String.format("%.5f", (left - 1d)) + "+to+" + String.format("%.5f", (right + 1d));
        interval = "+x1" + interval + "+x2" + interval;
        return base + system + interval;
    }

    public static boolean downloadImgByURL(URL url, String fileName) {
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

            FileOutputStream fos = new FileOutputStream("C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР4\\Lab4\\plots\\" + fileName + ".jpg");
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

    public URL openWebpageAndGetImageUrl(URL url) {

        driver.get(url.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(15000));
        try {
            //wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("_3c8e"), 7));
          //  wait.until(ExpectedConditions.attributeContains(By.className("_3c8e"), "src", "Calculate"));
            wait.until((ExpectedCondition<Boolean>) driver -> driver.findElements(By.className("_3c8e"))
                    .stream().anyMatch((webElement -> webElement.getAttribute("src").contains("https:"))));
        }
        finally {
            List<WebElement> list = driver.findElements(By.className("_3c8e"));
            //System.out.println("size = " + list.size());
            try {
                Optional<String> optional = list.stream().map(webElement -> webElement.getAttribute("src")).filter(s -> s.contains("https:")).findFirst();
               // assert optional.orElse(null) != null;
                assert optional.orElse(null) != null;
                return new URL(optional.orElse(null));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public  void openWolframAndDownloadGraphImage(URL url, String fileName){
        downloadImgByURL(openWebpageAndGetImageUrl(url), fileName);
    }
}
