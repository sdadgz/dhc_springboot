package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.entity.FriendLink;
import cn.sdadgz.dhc_springboot.mapper.FriendLinkMapper;
import cn.sdadgz.dhc_springboot.service.IFriendLinkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-13
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize) {

        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<FriendLink> lists = friendLinkMapper.getPage(startPage, pageSize);
        Long total = friendLinkMapper.selectCount(null);

        map.put(MagicValueUtil.RESULT_LISTS, lists);
        map.put(MagicValueUtil.RESULT_TOTAL, total);

        return map;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        friendLinkMapper.deleteBatchIds(ids);
    }

}
