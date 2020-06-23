package com.pbj2019.damo.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: TestJsonToSql
 * @Author: pbj
 * @Date: 2019/9/10 14:43
 * @Description: TODO   大量的json嵌套数据转换为对象
 */
public class TestJsonToSql {

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

    //一个jsonarray字符串转换为json对象
    public static List<Position> jsonArrayToObject(String s) throws IOException {
        List<Position> result = new ArrayList<>();
        JSONArray js = JSONArray.parseArray(s);//字符串转换为json数组
        for (int i = 0; i < js.size(); i++) {
//            js.getString(i);
            Position position = JSON.parseObject(js.getString(i), Position.class);
            result.add(position);
            if (position.getChildren() != null) {
                List<Position> positions = jsonArrayToObject(position.getChildren().toJSONString());
                Iterator<Position> it = positions.iterator();
                while (it.hasNext()) {
                    Position next = it.next();
                    result.add(next);
                    if (next.getChildren() != null) {
                        List<Position> positions1 = jsonArrayToObject(next.getChildren().toJSONString());
                        Iterator<Position> it2 = positions1.iterator();
                        while (it2.hasNext()) {
                            result.add(it2.next());
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String s = readJsonData("E:\\IDEA\\IDEA\\pbj2019\\pbj_common\\src\\main\\java\\com\\pbj2019\\damo\\common\\entity\\1.txt");
        List<Position> positions = jsonArrayToObject(s);
        Iterator<Position> it = positions.iterator();
        int i = 0;
        while (it.hasNext()) {
            Position next = it.next();
            System.out.println(next.toString());
            i++;
//            //使用jdbc连接池插入数据
//            /*
//             * 告知驱动管理器，配置MySQL数据库应用
//             * 即---加载驱动
//             */
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            /*
//             * 获取连接
//             */
//
//            Connection con = null;
//            try {
//                //通过驱动管理器获取连接---三个参数：URL, 用户名、密码
//                //URL---统一资源定位符---格式如： http://192.168.5.12:9527/index.html
//                //                         协议 :// 对方机器的IP地址:端口号/资源名
//                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/txyc_user?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC", "root", "mysqladmin");
//
//                /*
//                 * 书写sql语句
//                 */
//                String sql = "insert into t_position (value2,parent,label) " +
//                        "values(" + next.getValue() + "," + next.getParent() + ",'" + next.getLabel()+ "')";
//
//
//                /*
//                 * 利用连接创建获取语句对象--Statement
//                 */
//                Statement stat = con.createStatement();
//
//                /*
//                 * 语句对象执行SQL
//                 */
//                int row = stat.executeUpdate(sql);
//
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } finally {
//                /*
//                 * 关闭连接
//                 */
//                if (con != null) {
//                    try {
//                        con.close();
//                    } catch (SQLException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
        }
        System.out.println("===" + i);
    }
}
