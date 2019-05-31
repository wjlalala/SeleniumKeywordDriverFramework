package config;

import executionEngine.DriverScript;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utility.ExcelUtils;
import utility.Log;

import java.util.concurrent.TimeUnit;

import static executionEngine.DriverScript.OR;

public class ActionsKeywords {

    public static WebDriver driver;

    /**
     * 以下方法，我们针对dataEngine.xlsx文件中的action_keyword这列的关键字都进行封装
     * 等关键字框架快设计完了，我们再来调整，读取配置文件去启动不同测试浏览器和测试地址
     * 这样就不会代码写死这两个参数。
     */
    public static void openBrowser(String browser, String data) {

        // 这里，我们暂时都写死用chrome来进行自动化测试
        try{
            System.setProperty("webdriver.chrome.driver","lib/chromedriver");
            driver = new ChromeDriver();
            Log.info("Start chrome");
        }catch(Exception e){
            Log.info("无法启动浏览器 --- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }

    public static void openUrl(String url, String data) {
        try {
            Log.info("Maximize window");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get(Constants.URL);
            Log.info("Open test URL");
        }catch(Exception e){
        Log.info("无法打开测试环境地址 --- " + e.getMessage());
        DriverScript.bResult = false;
    }

    }

    public static void click(String object, String data) {
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
            Log.info("Click item: "+object);
        }catch(Exception e){
            Log.error("无法点击元素--- " + e.getMessage());
            DriverScript.bResult = false;
        }

    }

    public static void input(String object, String data){
        try{
            Log.info("开始在 "+object+" 输入文本");
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
        }catch(Exception e){
            Log.error("无法输入文本 --- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }

    public static void inputPassword(String object, String data){
        try{
            Log.info("密码框输入...");
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
        }catch(Exception e){
            Log.error("密码框无法输入--- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }


    public static void waitFor() throws Exception {
        try{
            Log.info("等待五秒");
            Thread.sleep(5000);
        }catch(Exception e){
            Log.error("无法等待 --- " + e.getMessage());
            DriverScript.bResult = false;
        }

    }

    public static void closeBrowser(String object, String data) {
        try{
            Log.info("关闭并退出浏览器");
            driver.quit();
        }catch(Exception e){
            Log.error("无法关闭浏览器--- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }
}