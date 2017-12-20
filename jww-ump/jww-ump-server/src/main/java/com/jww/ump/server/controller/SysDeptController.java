package com.jww.ump.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jww.common.core.model.PageModel;
import com.jww.common.web.BaseController;
import com.jww.common.web.model.ResultModel;
import com.jww.common.web.util.ResultUtil;
import com.jww.ump.model.UmpDeptModel;
import com.jww.ump.model.UmpTreeModel;
import com.jww.ump.rpc.api.UmpDeptService;
import com.xiaoleilu.hutool.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 部门管理控制器
 *
 * @Author: Ricky Wang
 * @Date: 17/12/1 11:23:17
 */
@RestController
@RequestMapping("/dept")
@Slf4j
public class SysDeptController extends BaseController {

    @Autowired
    private UmpDeptService umpDeptService;

    /**
     * 根据用户ID查询
     *
     * @param id
     * @return ResultModel<UmpDeptModel>
     * @author wanyong
     * @date 2017-12-05 13:35
     */
    @GetMapping("/query/{id}")
    public ResultModel<UmpDeptModel> query(@PathVariable Long id) {
        Assert.notNull(id);
        UmpDeptModel umpDeptModel = umpDeptService.queryOne(id);
        return ResultUtil.ok(umpDeptModel);
    }

    @GetMapping("/query/{current}/{size}/{deptName}")
    public ResultModel query(@PathVariable int current, @PathVariable int size, @PathVariable String deptName) {
        log.info("DeptController->query: current={},size={},deptName={}", current, size, deptName);
        // PageModel<UmpUserModel> pageModel = new PageModel<UmpUserModel>(current, size);
        PageModel<UmpDeptModel> pageModel = new PageModel<UmpDeptModel>();
        pageModel.setCurrent(current);
        pageModel.setSize(size);
        if ("all".equals(deptName.trim())) {
            pageModel = (PageModel<UmpDeptModel>) umpDeptService.selectPage(pageModel);
        } else {
            EntityWrapper entityWrapper = new EntityWrapper<UmpDeptModel>();
            entityWrapper.like("dept_name", deptName);
            pageModel = (PageModel<UmpDeptModel>) umpDeptService.selectPage(pageModel, entityWrapper);
        }
        return ResultUtil.ok(pageModel);
    }

    @PostMapping("/queryListPage")
    public ResultModel queryListPage(@RequestBody PageModel<UmpDeptModel> pageModel) {
        pageModel = (PageModel<UmpDeptModel>) umpDeptService.queryListPage(pageModel);
        return ResultUtil.ok(pageModel);
    }

    @PostMapping("/add")
    public ResultModel add(@Valid @RequestBody UmpDeptModel umpDeptModel) {
        log.info("DeptController->add: UmpDeptModel={}", umpDeptModel);
        if (umpDeptModel != null) {
            umpDeptModel.setUnitId(Long.valueOf(1));
            Date now = new Date();
            umpDeptModel.setCreateTime(now);
            umpDeptModel.setCreateBy(this.getCurrUser());
            umpDeptModel.setUpdateBy(this.getCurrUser());
            umpDeptModel.setUpdateTime(now);
            if(umpDeptModel.getParentId()==null){
                umpDeptModel.setParentId(0L);
            }
        }

        umpDeptService.insert(umpDeptModel);
        return ResultUtil.ok();
    }

    @PutMapping("/modify")
    public ResultModel modify(@RequestBody UmpDeptModel umpDeptModel) {
        log.info("DeptController->mod: UmpDeptModel={}", umpDeptModel);
//        if(umpDeptModel.getEnable()==null){
//            umpDeptModel.setEnable(0);
//        }
        umpDeptModel.setUpdateBy(this.getCurrUser());
        umpDeptModel.setUpdateTime(new Date());
        umpDeptService.modifyById(umpDeptModel);
        return ResultUtil.ok();
    }

    @DeleteMapping("/delBatchByIds")
    public ResultModel delBatchByIds(@RequestBody List<Long> ids) {
        Assert.notNull(ids);
        return ResultUtil.ok(umpDeptService.delBatchByIds(ids));
    }

    @GetMapping("/queryTree/{id}")
    public ResultModel queryTree(@PathVariable(required = false) Long id) {
        List<UmpTreeModel> list = umpDeptService.queryTree(id);
        return ResultUtil.ok(list);
    }

    @GetMapping("/queryTree")
    public ResultModel queryTree() {
        List<UmpTreeModel> list = umpDeptService.queryTree();
        return ResultUtil.ok(list);
    }

}
