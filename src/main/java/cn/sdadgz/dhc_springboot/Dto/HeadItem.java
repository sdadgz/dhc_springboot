package cn.sdadgz.dhc_springboot.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeadItem {
    // 返回一二级标题
    private String label;
    private List<HeadItem> children;
}
