package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
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
 * @since 2022-10-22
 */
@Service
public class SecondTitleServiceImpl extends ServiceImpl<SecondTitleMapper, SecondTitle> implements ISecondTitleService {

    @Resource
    private SecondTitleMapper secondTitleMapper;

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize, String title) {

        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<SecondTitle> lists = secondTitleMapper.getPage(startPage, pageSize, title);
        long total = count(title);

        map.put(MagicValueUtil.RESULT_TOTAL, total);
        map.put(MagicValueUtil.RESULT_LISTS, lists);

        return map;
    }

    @Override
    public long count(String title) {
        LambdaQueryWrapper<SecondTitle> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(title != null, SecondTitle::getTitle, title);
        return secondTitleMapper.selectCount(wrapper);
    }
}
