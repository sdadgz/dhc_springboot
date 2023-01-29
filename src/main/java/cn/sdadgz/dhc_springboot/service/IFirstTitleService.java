package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.FirstTitle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-22
 */
public interface IFirstTitleService extends IService<FirstTitle> {

    // 获取分页
    Map<String, Object> getPage(int currentPage, int pageSize, String title);

    // 获取总数
    long count(String title);

    // 根据内容获取该一级标题
    FirstTitle getFirstTitleByTitle(String title);

}
