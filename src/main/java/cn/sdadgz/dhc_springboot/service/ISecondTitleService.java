package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-22
 */
public interface ISecondTitleService extends IService<SecondTitle> {

    // 分页
    Map<String,Object> getPage(int currentPage,int pageSize,String title);

    // 总数
    long count(String title);

    // 清理冗余垃圾
    List<SecondTitle> getGC();

    /**
     * 获取指定二级标题
     *
     * @param firstTitleId 一级标题的id
     * @param title 二级标题的具体内容
     * @return 二级标题
     * */
    SecondTitle getSecondTitleByFirstTitleIdAndTitle(Integer firstTitleId,String title);

}
