package com.xq.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.pojo.EbookCategory;
import com.xq.pojo.EbookEntry;

/**
 * @author Administrator
 */
public interface EbookEntryService {
    Page<EbookEntry> findByPageAndClassify(Integer current, Integer pageSize, EbookEntry ebookEntry);
}
