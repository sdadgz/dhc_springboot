package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Img;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
