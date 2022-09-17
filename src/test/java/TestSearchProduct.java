import java.time.Duration;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSearchProduct {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    private String textProduct;
    private  int countNew;
    private  int countUsed;
    @Before
    public void setUp() throws Exception {
        textProduct = "tv";

        System.setProperty("webdriver.chrome.driver", "C:\\Chromedriver\\Chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        search();
    }

    private void search() {
        driver.get("https://www.mercadolibre.com.bo/#from=homecom");
        driver.findElement(By.id("cb1-edit")).click();
        driver.findElement(By.id("cb1-edit")).clear();
        driver.findElement(By.id("cb1-edit")).sendKeys(textProduct);
        driver.findElement(By.cssSelector(".nav-search")).submit();
        driver.get("https://listado.mercadolibre.com.bo/"+textProduct+"#D[A:"+textProduct+"]");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            String conditionTextUsed = driver.findElement(By.xpath("//main[@id='root-app']/div/div[2]/aside/section/div[4]/ul/li[2]/a/span[2]")).getText();
            conditionTextUsed = conditionTextUsed.replace("(", "").replace(")", "");

            System.out.println(conditionTextUsed);

            countUsed = Integer.parseInt(conditionTextUsed);
        } catch (NoSuchElementException e){
            countUsed = 0;
        }

        try {
            String conditionTextNew = driver.findElement(By.xpath("//main[@id='root-app']/div/div[2]/aside/section/div[4]/ul/li/a/span[2]")).getText();
            conditionTextNew = conditionTextNew.replace("(", "").replace(")", "");

            System.out.println(conditionTextNew);

            countNew = (int) Integer.parseInt(conditionTextNew);
        }
        catch (NoSuchElementException e){
            countNew = 0;
        }
    }

    @Test
    public void testSearchProductMinThree() throws Exception {
        boolean isMin3 = false;

        if (countNew >= 3) {
            isMin3 = true;
        }
        assertTrue(isMin3);

        isMin3 = false;

        if (countUsed >= 3) {
            isMin3 = true;
        }
        assertTrue(isMin3);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public static void main(String[] args) throws Exception {
        TestSearchProduct testSearchProduct = new TestSearchProduct();
        testSearchProduct.setUp();
        testSearchProduct.testSearchProductMinThree();
        testSearchProduct.tearDown();
    }
}

