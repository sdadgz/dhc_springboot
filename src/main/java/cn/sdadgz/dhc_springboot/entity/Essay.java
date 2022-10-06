package cn.sdadgz.dhc_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @since 2022-09-27
 */
@Getter
@Setter
@ApiModel(value = "Essay对象", description = "")
public class Essay implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    private String text;

    private String title;

    private LocalDateTime createTime;

    @JsonIgnore
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @JsonIgnore
    private String dir;

}
