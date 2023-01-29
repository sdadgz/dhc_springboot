package cn.sdadgz.dhc_springboot.entity;

import cn.sdadgz.dhc_springboot.Utils.GeneralUtil;
import cn.sdadgz.dhc_springboot.mapper.FirstTitleMapper;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.IFirstTitleService;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
@Getter
@Setter
@ApiModel(value = "Field对象", description = "")
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer essayId;

    @TableField(exist = false)
    private Essay essay;

    private String field;

    private Integer firstTitleId;

    private Integer secondTitleId;

    public void setFieldAndFirstTitleIdAndSecondTitleId(String field) {
        this.field = field;
        String[] s = field.split(" ");
        if (s.length >= 1) {
            FirstTitle firstTitle = GeneralUtil.getFirstTitleService().getFirstTitleByTitle(s[0]);
            setFirstTitleId(firstTitle.getId());
        }
        if (s.length >= 2) {
            SecondTitle secondtitle = GeneralUtil.getSecondTitleService().getSecondTitleByFirstTitleIdAndTitle(getFirstTitleId(), s[1]);
            setSecondTitleId(secondtitle.getId());
        }
    }

    public void synchronousField() {
        FirstTitle firstTitle = GeneralUtil.getFirstTitleMapper().selectById(getFirstTitleId());
        SecondTitle secondTitle = GeneralUtil.getSecondTitleMapper().selectById(getSecondTitleId());
        String newField = firstTitle.getTitle();
        if (!GeneralUtil.isNull(secondTitle)) {
            newField += " " + secondTitle.getTitle();
        }
        setField(newField);
    }
}
