
package config;

public class Constants {

    // 这里定义为public static的类型，方便其他任何类进行访问和调用
    public static final String URL = "https://www.baidu.com";
    public static final String Path_TestData = "dataEngine/dataEngine.xlsx";
    public static final String File_TestData = "dataEngine.xlsx";
    public static final String OR_PATH = "object/OR.txt";
    public static final String Log_Path = "src/log4j.xml";

    // dataEngine.xlsx中一些单元格的索引值
    public static final int Col_TestCaseID = 0;
    public static final int Col_TestScenarioID =1 ;
    public static final int Col_PageObject =3 ;
    public static final int Col_ActionKeyword =4 ;
    public static final int Col_RunMode =2 ;
    public static final int Col_Result =3 ;
    public static final int Col_DataSet = 5;
    public static final int Col_TestStepResult =6;

    public static final String KEYWORD_FAIL = "FAIL";
    public static final String KEYWORD_PASS = "PASS";

    // DataEngmine.excel中sheet的名称
    public static final String Sheet_TestSteps = "Test Steps";
    public static final String Sheet_TestCases = "Test Cases";

    // 测试登录用到的用户数据
    public static final String UserName = "wwzwj19";
    public static final String Password = "wj123456";
}