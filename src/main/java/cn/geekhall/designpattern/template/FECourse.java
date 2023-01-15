package cn.geekhall.designpattern.template;

/**
 * FECourse
 *
 * @author yiny
 * @date 2023/1/15 22:57
 */
public class FECourse extends NetworkCourse {
    private boolean needHomeworkFlag = false;
    @Override
    void checkHomework() {
        System.out.println("检查前端作业");
    }

    public FECourse(boolean needHomeworkFlag) {
        this.needHomeworkFlag = needHomeworkFlag;
    }
}
