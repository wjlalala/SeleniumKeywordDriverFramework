package executionEngine;
import config.ActionsKeywords;
import config.Constants;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import utility.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;



import utility.Log;


/**
 * create by Anthony on 2018/1/30
 */
public class DriverScript {

    // 声明一个public static的类对象，所以我们可以在main方法范围之外去使用。
    public static ActionsKeywords actionsKeywords;
    public static String sActionKeyword;
    // 下面是返回类型是方法，这里用到反射类
    public static Method method[];
    // 新建一个Properties对象
    public static Properties OR;
    public static String sPageObject;

    public static int iTestStep;
    public static int iTestLastStep;
    public static String sTestCaseID;
    public static String sRunMode;
    public static boolean bResult;
    public static String sData;


    // 这里我们初始化'ActionsKeywords'类的一个对象
    public DriverScript() throws NoSuchMethodException, SecurityException {
        actionsKeywords = new ActionsKeywords();
        method = actionsKeywords.getClass().getMethods();
    }

    public static void main(String[] args) throws Exception {

        ConfigurationSource source;
        File config = new File(Constants.Log_Path);
        source = new ConfigurationSource(new FileInputStream(config), config);
        Configurator.initialize(null, source);


        Log.startTestCase("Login_01");
        Log.info("加载和读取Excel数据文件");

        ExcelUtils.setExcelFile(Constants.Path_TestData);
        // 创建一个文件输入流对象
        FileInputStream fs = new FileInputStream(Constants.OR_PATH);
        // 创建一个Properties对象
        OR = new Properties(System.getProperties());
        // 加载全部对象仓库文件
        OR.load(fs);
        Log.info("开始执行测试用例。");
        DriverScript startEngine = new DriverScript();
        startEngine.execute_TestCase();
        Log.info("测试用例执行结束。");

    }

    private void execute_TestCase() throws Exception {
        int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

        for(int iTestCase=1; iTestCase<=iTotalTestCases; iTestCase++){
            bResult=true;
            sTestCaseID = ExcelUtils.getCellData(iTestCase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
            sRunMode = ExcelUtils.getCellData(iTestCase, Constants.Col_RunMode, Constants.Sheet_TestCases);
            if (sRunMode.equals("Yes")){
                iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
                iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
            }
            for(; iTestStep<= iTestLastStep; iTestStep++){
                sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword, Constants.Sheet_TestSteps);
                sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
                sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
                execute_Actions();
                if(bResult==false){
                    //Storing the result as Pass in the excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestCase,Constants.Col_Result,Constants.Sheet_TestCases);
                    Log.endTestCase(sTestCaseID);
                    break;
                }
            }
            if(bResult==true){
                //Storing the result as Pass in the excel sheet
                ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestCase,Constants.Col_Result,Constants.Sheet_TestCases);
                Log.endTestCase(sTestCaseID);
            }

        }
    }

    private static void execute_Actions() throws Exception {
        //循环遍历每一个关键字驱动方法（在actionskeywords.java中）
        //method variable contain all the method and method.length returns the total number of methods
        // 下面methid.length表示方法个数，method变量表示任何一个关键字方法，例如openBrowser()
        for (int i = 0; i < method.length; i++) {
            //开始对比代码中关键字方法名称和excel中关键字这列值是否匹配
            if (method[i].getName().equals(sActionKeyword)) {
                //一点匹配到关键字，并传递页面对象参数和动作关键字参数
                method[i].invoke(actionsKeywords, sPageObject, sData);
                //一旦任何关键字被执行，利用break语句去跳出for循环。
                if(bResult==true){
                    //If the executed test step value is true, Pass the test step in Excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
                    break;
                }else{
                    //If the executed test step value is false, Fail the test step in Excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
                    //In case of false, the test execution will not reach to last step of closing browser
                    //So it make sense to close the browser before moving on to next test case
                    ActionsKeywords.closeBrowser("","");
                    break;
                }
            }
        }
    }
}

