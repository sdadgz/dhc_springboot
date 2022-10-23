package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.entity.FirstTitle;
import cn.sdadgz.dhc_springboot.mapper.FirstTitleMapper;
import cn.sdadgz.dhc_springboot.service.IFirstTitleService;
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
public class FirstTitleServiceImpl extends ServiceImpl<FirstTitleMapper, FirstTitle> implements IFirstTitleService {

    @Resource
    private FirstTitleMapper firstTitleMapper;

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize, String title) {

        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<FirstTitle> lists = firstTitleMapper.getPage(startPage, pageSize, title);
        long total = count(title);

        map.put(MagicValueUtil.RESULT_LISTS, lists);
        map.put(MagicValueUtil.RESULT_TOTAL, total);

        return map;
    }

    @Override
    public long count(String title) {
        LambdaQueryWrapper<FirstTitle> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(title != null, FirstTitle::getTitle, title);
        return firstTitleMapper.selectCount(wrapper);
    }
}
