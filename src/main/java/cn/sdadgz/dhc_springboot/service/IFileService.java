package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-16
 */
public interface IFileService extends IService<File> {

    // md5是否重复
    File exists(String md5);

    // 根据ids批量虚拟删除
    void updateIsDelete(List<Integer> idList);

    // 名字是否存在
    String nameExists(String name) throws NoSuchAlgorithmException;

    // 获取分页
    Map<String, Object> getPage(int currentPage, int pageSize, String title);

    // 获取总数
    long count(String title);

}
