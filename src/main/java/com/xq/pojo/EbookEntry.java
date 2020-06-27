package com.xq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class EbookEntry {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer categoryid;
    @NotBlank
    private String title;
    @NotBlank
    private String summary;
    @NotBlank
    private String uploaduser;
    private Date createdate;
    /**
     * 乐观锁 每次更新都会叠加1 默认0
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除 0代表未删除 1表示删除
     */
    @TableLogic
    private Integer deleted;
}
