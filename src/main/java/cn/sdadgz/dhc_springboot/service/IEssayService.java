package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Essay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
public interface IEssayService extends IService<Essay> {

    // 获取essay分页根据field
    List<Essay> getEssayPageByField(String field, int currentPage, int pageSize);

    // 获取总数
    Long getEssayTotalByField(String field);

}
