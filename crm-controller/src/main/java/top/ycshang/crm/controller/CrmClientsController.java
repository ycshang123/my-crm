package top.ycshang.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ycshang.crm.common.entity.CrmClients;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.dto.PageDto;
import top.ycshang.crm.security.api.ApiController;
import top.ycshang.crm.service.CrmClientsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:27
 **/
@RestController
@RequestMapping("/api/crm/clients")
@Api(value = "客户操作接口", tags = {"客户操作接口"})
public class CrmClientsController extends ApiController {
    @Resource
    private CrmClientsService crmClientsService;

    /**
     * 客户列表查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "客户列表查询", httpMethod = "POST", produces = "客户列表查询")
    public SystemResult<?> list() {
        List<CrmClients> clientsList = crmClientsService.list();
        return SystemResult.createByCodeSuccess(1, "执行成功！", clientsList);
    }

    @PostMapping("/page")
    @ApiOperation(value = "客户列表分页查询", httpMethod = "POST", produces = "客户列表分页查询")
    public SystemResult<?> page(@RequestBody PageDto pageDto) {
        //获取当前页
        if (pageDto != null) {
            //每页多少条
            int pageNum = pageDto.getPageNum();
            int pageSize = pageDto.getPageSize();
            //模糊查询的字段
            String clientName = pageDto.getKeywords();
            IPage<CrmClients> iPage = new Page<>(pageNum, pageSize);
            IPage<CrmClients> page;
            if (clientName != null && !"".equals(clientName)) {
                page = crmClientsService.page(iPage, new QueryWrapper<CrmClients>().lambda().like(CrmClients::getClientName, clientName));
            } else {
                page = crmClientsService.page(iPage);
            }
            return SystemResult.createByCodeSuccess(1, "执行成功！", page);
        } else {
            return null;
        }
    }


    /**
     * 新增客户
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增客户", httpMethod = "POST", produces = "新增客户")
    public SystemResult<?> save(@RequestBody CrmClients crmClients) {
        boolean flag = false;
        if (crmClients != null) {
            flag = crmClientsService.save(crmClients);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", true);
        } else {
            return SystemResult.createByError();
        }
    }

    /**
     * 更新客户
     */

    @PostMapping("/update")
    @ApiOperation(value = "更新客户", httpMethod = "POST", produces = "更新客户")
    public SystemResult<?> updateUserAndRole(@RequestBody CrmClients crmClients) {
        boolean flag = false;
        if (crmClients != null) {
            flag = crmClientsService.updateById(crmClients);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", true);
        } else {
            return SystemResult.createByError();
        }

    }

    /**
     * 删除客户
     */

    @PostMapping("/delete")
    @ApiOperation(value = "删除客户", httpMethod = "POST", produces = "删除客户")
    public SystemResult<?> delete(@RequestBody CrmClients crmClients) {
        if (crmClients != null) {
            // 删除用户
            boolean removeById = crmClientsService.removeById(crmClients.getClientId());
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
