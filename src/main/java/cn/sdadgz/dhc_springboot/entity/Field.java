package cn.sdadgz.dhc_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
}
