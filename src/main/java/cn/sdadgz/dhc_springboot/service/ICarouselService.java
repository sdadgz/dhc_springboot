package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
public interface ICarouselService extends IService<Carousel> {

    // 根据essayId批量删除
    void deleteByEssayIds(List<Integer> idList);

    // 获取分页
    Map<String,Object> getPage(int currentPage,int pageSize,String title);

}
