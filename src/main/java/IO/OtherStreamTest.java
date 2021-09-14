package IO;

import org.junit.Test;

import java.io.*;

/*
其它流的使用
1.标准的输入，输出流
2.打印流
3.数据流
 */
public class OtherStreamTest {
    /*
    1.标准的输入、输出流
    1.1
    System.in:标准的输入流，默认从键盘输入
    System.out:标准的输出流，默认从控制台输出
    1.2
    System类的setIn(InputStream is) / setOut(PrintStream ps)方式重新指定输入和输出的流。

    1.3练习：
    从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，
    直至当输入“e”或者“exit”时，退出程序。

    方法一：使用Scanner实现，调用next()返回一个字符串
    方法二：使用System.in实现。System.in  --->  转换流 ---> BufferedReader的readLine()
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        try{
            InputStreamReader isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);
            while (true){
                System.out.println("请输入字符串：");
                String s = br.readLine();
                if ("e".equalsIgnoreCase(s) || " exit".equalsIgnoreCase(s)){
                    System.out.println("程序结束");
                    break;
                }
                String ans = s.toUpperCase();
                System.out.println(ans);
            }
        }catch (Exception e){

        }finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
    2. 打印流：PrintStream 和PrintWriter

    2.1 提供了一系列重载的print() 和 println()
    2.2 练习：
     */
    @Test
    public void test01(){
        PrintStream ps = null;
        try{
            FileOutputStream fos = new FileOutputStream(new File("hello.txt"));
            ps = new PrintStream(fos, true);
            if(ps != null){
                System.setOut(ps);
            }
            for(int i = 0;i <= 255;i++){
                System.out.println((char)i);
                if (i % 50 == 0){
                    System.out.println();
                }
            }
        }catch (Exception e){

        }finally {
            if (ps != null){
                ps.close();
            }
        }
    }
    /*
    3. 数据流
    3.1 DataInputStream 和 DataOutputStream
    3.2 作用：用于读取或写出基本数据类型的变量或字符串

    练习：将内存中的字符串、基本数据类型的变量写出到文件中。

    注意：处理异常的话，仍然应该使用try-catch-finally.
     */
    @Test
    public void test02() throws Exception{
        //1.
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
        //2.
        dos.writeUTF("嘿嘿");
        dos.flush();//刷新操作，将内存中的数据写入文件
        dos.writeInt(23);
        dos.flush();
        dos.writeBoolean(true);
        dos.flush();
        //3.
        dos.close();
    }
    /*
    将文件中存储的基本数据类型变量和字符串读取到内存中，保存在变量中。

    注意点：读取不同类型的数据的顺序要与当初写入文件时，保存的数据的顺序一致！

     */
    @Test
    public void test03() throws IOException {
        //1.
        DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
        //2.
        String name = dis.readUTF();
        int age = dis.readInt();
        boolean isMale = dis.readBoolean();

        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("isMale = " + isMale);

        //3.
        dis.close();

    }
}
