package cn.sdadgz.dhc_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-22
 */
@Getter
@Setter
@TableName("second_title")
@ApiModel(value = "SecondTitle对象", description = "")
public class SecondTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    @TableField("`order`")
    private Integer order;

    private Integer firstTitleId;

    @TableField(exist = false)
    private FirstTitle firstTitle;

}
