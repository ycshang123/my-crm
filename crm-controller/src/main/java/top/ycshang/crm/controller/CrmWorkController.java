package top.ycshang.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.ycshang.crm.common.entity.CrmWork;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.dto.PageDto;
import top.ycshang.crm.security.api.ApiController;
import top.ycshang.crm.service.CrmWorkService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:30
 **/
@RestController
@RequestMapping("/api/crm/work")
@Api(value = "工作台操作接口", tags = {"工作台操作接口"})
public class CrmWorkController extends ApiController {
    @Resource
    private CrmWorkService crmWorkService;

    @PostMapping("/page")
    @ApiOperation(value = "工作台记录分页查询", httpMethod = "POST", produces = "工作台记录分页查询")
    public SystemResult<?> page(@RequestBody PageDto pageDto) {
        if (pageDto != null) {
            //每页多少条
            int pageNum = pageDto.getPageNum();
            int pageSize = pageDto.getPageSize();
            String workName = pageDto.getKeywords();

            IPage<CrmWork> iPage = new Page<>(pageNum, pageSize);
            IPage<CrmWork> page;
            if (workName == null || "".equals(workName)) {
                page = crmWorkService.page(iPage);
            } else {
                page = crmWorkService.page(iPage, new QueryWrapper<CrmWork>().lambda().like(CrmWork::getWorkName, workName));
            }
            return SystemResult.createByCodeSuccess(1, "执行成功！", page);
        } else {
            return null;
        }
    }


    @PostMapping("/list")
    @ApiOperation(value = "工作台记录列表", httpMethod = "POST", produces = "工作台记录列表")
    public SystemResult<?> list() {
        List<CrmWork> list = crmWorkService.list();
        return SystemResult.createByCodeSuccess(1, "成功", list);
    }

    @PostMapping("/save")
    @ApiOperation(value = "插入工作台记录", httpMethod = "POST", produces = "新增工作台记录")
    public SystemResult<?> save(@RequestBody CrmWork obj) {
        boolean flag = false;
        if (obj != null) {
            flag = crmWorkService.save(obj);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", true);
        } else {
            return SystemResult.createByError();
        }

    }

    @PostMapping("{id}")
    @ApiOperation(value = "获取单条工作台记录", httpMethod = "POST", produces = "获取单条工作台记录")
    public SystemResult<?> selectOne(@PathVariable Serializable id) {
        return SystemResult.createBySuccess(this.crmWorkService.getById(id));
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新工作台记录", httpMethod = "POST", produces = "更新工作台记录")
    public SystemResult<?> updateUserAndRole(@RequestBody CrmWork obj) {
        boolean flag = false;
        if (obj != null) {
            flag = crmWorkService.updateById(obj);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", true);
        } else {
            return SystemResult.createByError();
        }

    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除工作台记录", httpMethod = "POST", produces = "删除工作台记录")
    public SystemResult<?> delete(@RequestBody CrmWork obj) {
        if (obj != null) {
            boolean removeById = crmWorkService.removeById(obj.getWorkId());
            if (removeById) {
                return SystemResult.createByCodeSuccess(1, "执行成功！", true);
            } else {
                return SystemResult.createByError();
            }
        } else {
            return SystemResult.createByError();
        }
    }
}