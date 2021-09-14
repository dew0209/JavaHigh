package collection.base.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    //Properties:常用来处理配置文件。key和value都是String类型
    public static void main(String[] args) {
        FileInputStream fis = null;

        try {
            Properties pros = new Properties();
            File file = new File("jdbc.properties");
            System.out.println(file.getAbsoluteFile());
            fis = new FileInputStream("jdbc.properties");
            pros.load(fis);
            String name = pros.getProperty("name");
            System.out.println(name);
            String password = pros.getProperty("password");
            System.out.println(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
