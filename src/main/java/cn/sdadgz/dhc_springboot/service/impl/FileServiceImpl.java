package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.IdUtil;
import cn.sdadgz.dhc_springboot.Utils.TimeUtil;
import cn.sdadgz.dhc_springboot.entity.File;
import cn.sdadgz.dhc_springboot.mapper.FileMapper;
import cn.sdadgz.dhc_springboot.service.IFileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    public void updateIsDelete(List<Integer> idList) {
        File file = new File();
        file.setIsDelete(true);

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
}
