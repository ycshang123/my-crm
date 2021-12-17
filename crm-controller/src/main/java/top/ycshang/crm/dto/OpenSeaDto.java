package top.ycshang.crm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:28
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OpenSeaDto extends PageDto {
    private String clientName;
    private String clientType;
    private String clientTelephone;

    public OpenSeaDto() {
        super();
    }
}