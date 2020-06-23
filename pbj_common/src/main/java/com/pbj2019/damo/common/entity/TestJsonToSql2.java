package com.pbj2019.damo.common.entity;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: TestJsonToSql2
 * @Author: pbj
 * @Date: 2019/9/11 15:32
 * @Description: TODO
 */
public class TestJsonToSql2 {
    /**
     * 读取json文件并且转换成字符串   filePath文件的路径
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String readJsonData(String pactFile) throws IOException {
        // 读取文件数据
        //System.out.println("读取文件数据util");

        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
    }

    public static void main(String[] args) throws IOException {
        String s = readJsonData("E:\\IDEA\\IDEA\\pbj2019\\pbj_common\\src\\main\\java\\com\\pbj2019\\damo\\common\\entity\\3.txt");
        String str = s.replace(" ","");
        String str2 = str.replace("\"","");
        List<String> res = Arrays.asList(str2.split(","));
        Iterator<String> it = res.iterator();
        int i = 0;
        while (it.hasNext()) {
//            System.out.println(it.next());
            i++;
                        //使用jdbc连接池插入数据
            /*
             * 告知驱动管理器，配置MySQL数据库应用
             * 即---加载驱动
             */
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /*
             * 获取连接
             */

            Connection con = null;
            try {
                //通过驱动管理器获取连接---三个参数：URL, 用户名、密码
                //URL---统一资源定位符---格式如： http://192.168.5.12:9527/index.html
                //                         协议 :// 对方机器的IP地址:端口号/资源名
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/txyc_user?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "root", "mysqladmin");

                /*
                 * 书写sql语句
                 */
                String sql = "insert into t_school (school_name) " +
                        "values('" + it.next()+ "')";


                /*
                 * 利用连接创建获取语句对象--Statement
                 */
                Statement stat = con.createStatement();

                /*
                 * 语句对象执行SQL
                 */
                int row = stat.executeUpdate(sql);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                /*
                 * 关闭连接
                 */
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


            }
        }
        System.out.println("==="+i);
    }
}

