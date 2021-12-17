package top.ycshang.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {
    private Integer pageNum;
    private Integer pageSize;
    private String keywords;
}