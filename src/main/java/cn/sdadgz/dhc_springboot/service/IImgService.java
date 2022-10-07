package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Img;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
public interface IImgService extends IService<Img> {

    // 根据已经存在的图片路径插入
    Img insertByPath(String path, int essayId);

    // 根据essayId删除
    void deleteByEssayIds(List<Integer> ids);

    // 是否已经上传
    Img md5Exists(String md5);

    // 上传图片
    Map<String,Object> uploadImg(MultipartFile file,Integer reduceX,Integer reduceY,String token) throws IOException, NoSuchAlgorithmException;

}
