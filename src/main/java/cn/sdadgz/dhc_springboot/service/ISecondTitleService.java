package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
