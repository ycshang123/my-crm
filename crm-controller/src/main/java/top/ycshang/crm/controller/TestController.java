package top.ycshang.crm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ycshang.crm.common.vo.SystemResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:32
 **/
@RestController
@Api(produces = "测试接口", tags = {"测试接口"})
public class TestController {

    @PostMapping("test")
    @ApiOperation(value = "测试接口", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> test() {
        Book book1 = new Book("1", "金发碧眼为什么很少在白人以外的种族出现", "biokiwi", "https://pic4.zhimg.com/v2-f05c0e1005121e89e53762704d05b28c_fhd.jpg?source=8673f162");
        Book book2 = new Book("2", "哈利波特》原著中与电影中人物有哪些差别？", "kalinnn", "https://pic2.zhimg.com/v2-86bcc639835a991b61602b73310604b8_fhd.jpg?source=8673f162");
        Book book3 = new Book("3", "有哪些适合情侣两个人一起玩的桌游？", "北邙", "https://pic1.zhimg.com/v2-89f0e1611118a7e15694060542eeba7a_fhd.jpg?source=8673f162");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        return SystemResult.createBySuccess(books);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Book {
    private String id;
    private String title;
    private String author;
    private String image;
}