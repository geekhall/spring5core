package cn.geekhall.designpattern.template;

/**
 * NetworkCourseTest
 *
 * @author yiny
 * @date 2023/1/15 22:56
 */
public class NetworkCourseTest {
    public static void main(String[] args) {
        System.out.println("后端设计模式课程start---");
        NetworkCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();
        System.out.println("后端设计模式课程end---");

        System.out.println("前端设计模式课程start---");
        NetworkCourse feCourse = new FECourse(true);
        feCourse.createCourse();
        System.out.println("前端设计模式课程end---");

    }
}
