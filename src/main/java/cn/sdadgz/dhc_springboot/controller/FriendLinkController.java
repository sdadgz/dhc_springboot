package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.FriendLink;
import cn.sdadgz.dhc_springboot.mapper.FriendLinkMapper;
import cn.sdadgz.dhc_springboot.service.IFriendLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-13
 */
@RestController
@RequestMapping("/friendLink")
public class FriendLinkController {

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Resource
    private IFriendLinkService friendLinkService;

    // 更新
    @PutMapping("/update")
    public Result update(@RequestBody FriendLink friendLink){

        friendLinkMapper.updateById(friendLink);

        return Result.success();
    }

    // 上传
    @PostMapping("/upload")
    public Result upload(@RequestBody FriendLink friendLink) {

        friendLinkMapper.insert(friendLink);

        return Result.success();
    }

    // 分页
    @GetMapping("/page")
    public Result getPage(@RequestParam("currentPage") int currentPage,
                          @RequestParam("pageSize") int pageSize) {

        Map<String, Object> map = friendLinkService.getPage(currentPage, pageSize);

        return Result.success(map);
    }

    // 删除
    @DeleteMapping
    public Result deleteBatch(@RequestBody Map<String, List<Integer>> requestMap) {

        List<Integer> idList = requestMap.get(MagicValueUtil.REQUEST_LISTS);

        friendLinkService.deleteByIds(idList);

        return Result.success();
    }

}
