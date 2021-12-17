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
import top.ycshang.crm.common.entity.CrmContacts;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.dto.PageDto;
import top.ycshang.crm.security.api.ApiController;
import top.ycshang.crm.service.CrmClientsService;
import top.ycshang.crm.service.CrmContactsService;

import javax.annotation.Resource;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:29
 **/
@RestController
@RequestMapping("/api/crm/contacts")
@Api(value = "联系人操作接口", tags = {"联系人操作接口"})
public class CrmContactsController extends ApiController {
    @Resource
    private CrmContactsService crmContactsService;

    @Resource
    private CrmClientsService crmClientsService;

    @PostMapping("/page")
    @ApiOperation(value = "联系人分页查询", httpMethod = "POST", produces = "联系人分页查询")
    public SystemResult<?> page(@RequestBody PageDto pageDto) {
        if (pageDto != null) {
            int pageNum = pageDto.getPageNum();
            int pageSize = pageDto.getPageSize();
            //模糊查询字段
            String contactsName = pageDto.getKeywords();
            IPage<CrmContacts> iPage = new Page<>(pageNum, pageSize);
            IPage<CrmContacts> page;
            if (contactsName == null || "".equals(contactsName)) {
                page = crmContactsService.page(iPage);
            } else {
                page = crmContactsService.page(iPage, new QueryWrapper<CrmContacts>().lambda().like(CrmContacts::getContactsName, contactsName));
            }
            //找出客户id对应的name
            for (CrmContacts crmContacts : page.getRecords()) {
                CrmClients crmClients = crmClientsService.getById(crmContacts.getClientId());
                crmContacts.setClientName(crmClients.getClientName());
            }
            return SystemResult.createByCodeSuccess(1, "执行成功！", page);
        } else {
            return null;
        }
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增联系人", httpMethod = "POST", produces = "新增联系人")
    public SystemResult<?> save(@RequestBody CrmContacts crmContacts) {
        boolean flag = false;
        if (crmContacts != null) {
            flag = crmContactsService.save(crmContacts);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", true);
        } else {
            return SystemResult.createByError();
        }

    }

    /**
     * 用户的更新
     */

    @PostMapping("/update")
    @ApiOperation(value = "更新联系人", httpMethod = "POST", produces = "更新联系人")
    public SystemResult<?> updateUserAndRole(@RequestBody CrmContacts crmContacts) {
        boolean flag = false;
        if (crmContacts != null) {
            flag = crmContactsService.updateById(crmContacts);
        }
        if (flag) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", flag);
        } else {
            return SystemResult.createByError();
        }

    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除联系人", httpMethod = "POST", produces = "删除联系人")
    public SystemResult<?> delete(@RequestBody CrmContacts crmContacts) {
        if (crmContacts != null) {
            // 删除用户
            boolean removeById = crmContactsService.removeById(crmContacts.getClientId());
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