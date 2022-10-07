package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.*;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.config.FileConfig;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Slf4j
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements IImgService {

    @Value("${my.file-config.uploadPath}")
    private String uploadPath;

    @Value("${my.file-config.downloadPath}")
    private String downloadPath;

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private FileConfig fileConfig;

    // 根据路径插入
    @Override
    public Img insertByPath(String path, int essayId) {
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
        wrapper.in(Img::getEssayId, ids);
        imgMapper.delete(wrapper);
    }

    @Override
    public Img md5Exists(String md5) {

        LambdaQueryWrapper<Img> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Img::getMd5, md5);
        wrapper.eq(Img::getEssayId, MagicValueUtil.DEFAULT_ESSAY_ID);
        List<Img> imgs = imgMapper.selectList(wrapper);

        return imgs.size() > 0 ? imgs.get(0) : null;
    }

    @Override
    public Map<String, Object> uploadImg(MultipartFile file, Integer reduceX, Integer reduceY, String token) throws IOException, NoSuchAlgorithmException {
        // 初始化
        Img img = new Img();
        img.setCreateTime(TimeUtil.now());
        img.setEssayId(MagicValueUtil.DEFAULT_ESSAY_ID);
        Map<String, Object> map = new HashMap<>();

        // uuid
        String salt = Arrays.toString(file.getBytes());
        String uuid = IdUtil.uuid(salt);

        // 创建文件夹
        String bashPath = fileConfig.getImgUploadPath();
        File jFile = new File(bashPath);
        boolean mkdirs = jFile.mkdirs();
        log.info("创建文件夹 {} ，{}", bashPath, mkdirs ? "成功" : "失败");

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.getType(originalFilename);

        // 上传原图
        String username = IdUtil.getName(token);
        String filename = username + MagicValueUtil.UNDERLINE + uuid + type;
        String path = fileConfig.getImgUploadPath() + filename;
        FileUtil.uploadToServer(file, path);
        jFile = new File(path);

        // 获取md5
        String md5 = Md5Util.md5(jFile);
        img.setMd5(md5);
        Img exists = md5Exists(md5);
        // 存在，去重
        if (exists != null) {
            img.setUrl(exists.getUrl());
            img.setReduceUrl(exists.getReduceUrl());
            boolean delete = jFile.delete();
            log.info("重复文件 {} ，删除 {}", file.getName(), delete ? "成功" : "失败");
        } else {
            // 不重复
            img.setUrl(FileUtil.toUrl(path));
            img.setReduceUrl(FileUtil.setReduceImg(jFile, reduceX, reduceY));
        }

        // 上传数据库
        imgMapper.insert(img);
        map.put("id", img.getId());

        return map;
    }
}
