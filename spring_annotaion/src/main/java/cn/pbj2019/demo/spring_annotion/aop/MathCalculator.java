package cn.pbj2019.demo.spring_annotion.aop;

/**
 * @ClassName: MathCalculator
 * @Author: pbj
 * @Date: 2019/9/30 23:16
 * @Description: TODO
 */
public class MathCalculator {

    public int div(int i,int j) {
        System.out.println("正在运行除法运算。。。。。");
        return i/j;
    }
}
