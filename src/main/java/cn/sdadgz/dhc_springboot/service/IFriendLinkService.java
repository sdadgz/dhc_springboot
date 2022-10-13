package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-13
 */
public interface IFriendLinkService extends IService<FriendLink> {

    // 获取分页
    Map<String,Object> getPage(int currentPage,int pageSize);

    // 批量删除
    void deleteByIds(List<Integer> ids);

}
