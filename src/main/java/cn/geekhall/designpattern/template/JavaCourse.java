package cn.geekhall.designpattern.template;

/**
 * JavaCourse
 *
 * @author yiny
 * @date 2023/1/15 22:54
 */
public class JavaCourse extends NetworkCourse {
    @Override
    void checkHomework() {
        System.out.println("检查 Java 作业");
    }
}
