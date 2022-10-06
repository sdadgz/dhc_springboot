package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.Md5Util;
import cn.sdadgz.dhc_springboot.Utils.TimeUtil;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements IImgService {

    @Value("${my.file-config.uploadPath}")
    private String uploadPath;

    @Value("${my.file-config.downloadPath}")
    private String downloadPath;

    @Resource
    private ImgMapper imgMapper;

    // 根据路径插入
    @Override
    public Img insertByPath(String path,int essayId) {
        // 初始化
        Img img = new Img();
        img.setCreateTime(TimeUtil.now());
        img.setEssayId(essayId);

        // 解析路径
        String url = FileUtil.toUrl(path);
        img.setUrl(url);

        // md5
        File file = new File(path);
        String md5 = Md5Util.md5(file);
        img.setMd5(md5);

        // 上传
        imgMapper.insert(img);

        return img;
    }

    @Override
    public void deleteByEssayIds(List<Integer> ids) {
        LambdaQueryWrapper<Img> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Img::getEssayId,ids);
        imgMapper.delete(wrapper);
    }
}
