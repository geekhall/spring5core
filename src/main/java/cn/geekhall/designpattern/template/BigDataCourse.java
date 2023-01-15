package cn.geekhall.designpattern.template;

/**
 * BigDataCourse
 *
 * @author yiny
 * @date 2023/1/15 22:55
 */
public class BigDataCourse extends NetworkCourse {
    private boolean needHomeworkFlag = false;

    public BigDataCourse(boolean needHomeworkFlag) {
        this.needHomeworkFlag = needHomeworkFlag;
    }

    @Override
    void checkHomework() {
        System.out.println("检查大数据作业");
    }

    @Override
    protected boolean needHomework() {
        return this.needHomeworkFlag;
    }
}
