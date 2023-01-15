package cn.geekhall.designpattern.template;

/**
 * NetworkCourse
 *
 * @author yiny
 * @date 2023/1/15 22:52
 */
public abstract class NetworkCourse {
    protected final void createCourse() {
        // 1. 发布预习资料
        this.postPreResource();
        // 2. 制作 PPT 课件
        this.createPPT();
        // 3. 在线直播
        this.liveVideo();
        // 4. 提交课件、课堂笔记
        this.postNote();
        // 5. 提交源码
        this.postSource();
        // 6. 布置作业，有些课是没有作业的，有些课是有作业的
        // 如果有作业的话，检查作业，如果没作业，完成了
        if (needHomework()) {
            checkHomework();
        }
    }

    abstract void checkHomework();

    protected boolean needHomework() {
        return false;
    }

    final void postSource() {
        System.out.println("提交源码");
    }

    final void postNote() {
        System.out.println("提交课件、课堂笔记");
    }

    final void liveVideo() {
        System.out.println("在线直播");
    }

    final void createPPT() {
        System.out.println("制作 PPT 课件");
    }

    final void postPreResource() {
        System.out.println("发布预习资料");
    }

}
