package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.IdUtil;
import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.Utils.TimeUtil;
import cn.sdadgz.dhc_springboot.entity.File;
import cn.sdadgz.dhc_springboot.mapper.FileMapper;
import cn.sdadgz.dhc_springboot.service.IFileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-16
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public File exists(String md5) {

        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getMd5, md5);
        List<File> files = fileMapper.selectList(wrapper);
        if (files.size() > 0) {
            return files.get(0);
        }

        return null;
    }

    @Override
    public void updateIsDelete(List<Integer> idList, boolean bool) {
        File file = new File();
        file.setIsDelete(bool);

        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(File::getId, idList);
        fileMapper.update(file, wrapper);

    }

    @Override
    public String nameExists(String name) throws NoSuchAlgorithmException {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getOriginalFilename, name);
        boolean exists = fileMapper.exists(wrapper);
        if (exists) {
            String type = FileUtil.getType(name);
            name += IdUtil.uuid(wrapper + name) + type;
        }
        return name;
    }

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize, String title) {

        // 初始化
        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<File> lists = fileMapper.getPage(startPage, pageSize, title);
        long total = count(title);

        map.put(MagicValueUtil.RESULT_LISTS, lists);
        map.put(MagicValueUtil.RESULT_TOTAL, total);

        return map;
    }

    @Override
    public long count(String title) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(File::getOriginalFilename, title);
        return fileMapper.selectCount(wrapper);
    }
}
