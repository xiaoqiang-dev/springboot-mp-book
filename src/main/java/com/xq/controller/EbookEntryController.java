package com.xq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xq.mapper.EbookCategoryMapper;
import com.xq.mapper.EbookEntryMapper;
import com.xq.pojo.EbookCategory;
import com.xq.pojo.EbookEntry;
import com.xq.service.EbookEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator
 */
@Api("图书表操作")
@Controller
public class EbookEntryController {
    @Resource
    private EbookEntryService ebookEntryServiceImpl;

    @Resource
    private EbookEntryMapper ebookEntryMapper;

    @Resource
    private EbookCategoryMapper ebookCategoryMapper;

    @ApiOperation(value = "结果查询",notes = "对ebook_entry表的一些查询操作")
    @RequestMapping({"/findAll","/","/findByClassify"})
    public ModelAndView find(HttpServletRequest request, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize, EbookEntry entry){
        ModelAndView modelAndView=new ModelAndView("index");
        //清空检验失败提示语
        request.getSession().removeAttribute("yanzheng");

        System.out.println("classify===="+entry.getCategoryid());
        if(request.getSession().getAttribute("classify")!=null&&!request.getSession().getAttribute("classify").equals("")){
            request.getSession().removeAttribute("classify");
            request.getSession().setAttribute("classify",entry.getCategoryid());
        }

        //分类表 用于主页面的查询下拉框
        List<EbookCategory> categories = ebookCategoryMapper.selectList(null);
        //图书表的查询内容
        Page<EbookEntry> pageEntry = ebookEntryServiceImpl.findByPageAndClassify(current, pageSize, entry);
        modelAndView.addObject("categories",categories);
        modelAndView.addObject("pageEntry",pageEntry);
        return modelAndView;
    }

    @ApiOperation(value = "对图书表的删除操作",notes = "异步删除")
    @DeleteMapping("/deleteBook")
    @ResponseBody
    public String deleteBook(@RequestParam(required = false) Integer id){
        System.out.println("进入删除操作，对编号"+id+"进行删除！");
        String operation="0";
        try {
            operation=Integer.toString(ebookEntryMapper.deleteById(id));
        } catch (Exception e) {
            System.out.println("删除失败！请核实bug原因！");
            e.printStackTrace();
        }
        System.out.println(operation+"条影响数目！");
        return operation;
    }

    @ApiOperation(value = "页面跳转",notes = "执行修改/增加页面的跳转")
    @GetMapping("/skip")
    public String skip(EbookEntry entry, Model model){
        model.addAttribute("mdCate",ebookCategoryMapper.selectList(null));
        System.out.println("id==="+entry.getId());
        if(entry.getId()!=null&&!entry.getId().equals("")){
            EbookEntry ebookEntry = ebookEntryMapper.selectById(entry.getId());
            model.addAttribute("mdEntry",ebookEntry);
        }else {
            model.addAttribute("mdEntry",entry);
        }
        return "modify";
    }

    /**
     * 增加 数据非空校验
     * @param entry
     * @return
     */
    @ApiOperation(value = "增加功能操作",notes = "对图书表的增加操作")
    @PostMapping("/inst")
    public String inst(@Validated EbookEntry entry, BindingResult result,HttpServletRequest request){
        //数据校验 如果对应属性存在空值，返回增加界面
        if(result.hasErrors()){
            request.getSession().setAttribute("yanzheng","请把数据补充完整！");
            return "redirect:/skip";
        }else {
            //清空检验失败提示语
            request.getSession().removeAttribute("yanzheng");
            System.out.println("进入增加操作！");
            try {
                ebookEntryMapper.insert(entry);
                System.out.println("增加成功！");
            } catch (Exception e) {
                System.out.println("增加失败！");
                e.printStackTrace();
            }
            return "redirect:/findAll";
        }
    }
    @ApiOperation(value = "修改功能操作",notes = "对图书表的修改操作")
    @PostMapping("/modify")
    public String modify(EbookEntry entry){
        System.out.println("喝了酒："+entry);
        Integer id = entry.getId();
        EbookEntry ee = ebookEntryMapper.selectById(id);
        entry.setVersion(ee.getVersion());
        System.out.println("执行修改操作！");
        try {
            ebookEntryMapper.updateById(entry);
            System.out.println("修改成功！");
        } catch (Exception e) {
            System.out.println("修改失败！");
            e.printStackTrace();
        }
        return "redirect:/findAll";
    }
}
