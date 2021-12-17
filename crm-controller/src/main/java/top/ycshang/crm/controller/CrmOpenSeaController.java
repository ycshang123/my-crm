package top.ycshang.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import top.ycshang.crm.dto.OpenSeaDto;
import top.ycshang.crm.security.api.ApiController;
import top.ycshang.crm.service.CrmClientsService;

import javax.annotation.Resource;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:29
 **/
@RestController
@RequestMapping("/api/crm/opensea")
@Api(value = "公海操作接口", tags = {"公海操作接口"})
public class CrmOpenSeaController extends ApiController {
    @Resource
    private CrmClientsService crmClientsService;

    @PostMapping("/page")
    @ApiOperation(value = "列表分页查询", httpMethod = "POST", produces = "列表分页查询")
    public SystemResult<?> page(@RequestBody OpenSeaDto openSeaDto) {
        if (openSeaDto != null) {
            int pageNum = openSeaDto.getPageNum();
            int pageSize = openSeaDto.getPageSize();
            String clientName = openSeaDto.getClientName();
            String clientType = openSeaDto.getClientType();
            String clientTelephone = openSeaDto.getClientTelephone();

            IPage<CrmClients> iPage = new Page<>(pageNum, pageSize);
            IPage<CrmClients> page;
            LambdaQueryWrapper<CrmClients> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CrmClients::getClientLastStatus, 2);

            if (clientName != null && !"".equals(clientName)) {
                lambdaQueryWrapper.like(CrmClients::getClientName, clientName);
            }
            if (clientType != null && !"".equals(clientType)) {
                lambdaQueryWrapper.like(CrmClients::getClientType, clientType);
            }
            if (clientTelephone != null && !"".equals(clientTelephone)) {
                lambdaQueryWrapper.like(CrmClients::getClientTelephone, clientTelephone);
            }
            page = crmClientsService.page(iPage, lambdaQueryWrapper);
            return SystemResult.createByCodeSuccess(1, "执行成功！", page);
        } else {
            return null;
        }
    }
}