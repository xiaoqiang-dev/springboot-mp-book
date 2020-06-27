package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xq.mapper.EbookEntryMapper;
import com.xq.pojo.EbookCategory;
import com.xq.pojo.EbookEntry;
import com.xq.service.EbookEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class EbookEntryServiceImpl implements EbookEntryService {
    @Autowired
    private EbookEntryMapper ebookEntryMapper;

    /**
     * mp分页实现+条件查询
     * @return
     */
    @Override
    public Page<EbookEntry> findByPageAndClassify(Integer current, Integer pageSize, EbookEntry entry) {
        QueryWrapper wrapper=new QueryWrapper();
        if(current==null){
            current=1;
        }
        if(pageSize==null){
            pageSize=3;
        }
        if(entry.getCategoryid()!=null&&entry.getCategoryid()!=0){
            System.out.println("进来了："+entry.getCategoryid());
            wrapper.eq("categoryId",entry.getCategoryid());
        }else if(entry.getCategoryid()!=null){

        }
        Page<EbookEntry> page=new Page<>(current,pageSize);
        ebookEntryMapper.selectPage(page,wrapper);
        return page;
    }
}
