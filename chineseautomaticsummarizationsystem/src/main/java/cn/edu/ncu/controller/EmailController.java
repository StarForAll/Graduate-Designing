package cn.edu.ncu.controller;

import cn.edu.ncu.api.EmailApi;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmailDTO;
import cn.edu.ncu.pojo.dto.EmailQueryDTO;
import cn.edu.ncu.pojo.vo.EmailVO;
import cn.edu.ncu.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 邮件
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class EmailController implements EmailApi {

    @Autowired
    private EmailService emailService;
    @Override
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(@RequestBody @Validated EmailQueryDTO queryDTO) {
        return emailService.queryByPage(queryDTO);
    }
    @Override
    public ResponseDTO<Long> add(@RequestBody @Valid EmailDTO addTO){
        return emailService.add(addTO);
    }
    @Override
    public ResponseDTO<Long> update(@RequestBody @Valid EmailDTO updateDTO){
        return emailService.update(updateDTO);
    }

    @Override
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return emailService.delete(id);
    }

    @Override
    public ResponseDTO<EmailVO> detail(@PathVariable("id") Long id){
        return emailService.detail(id);
    }


    @Override
    public ResponseDTO<String> send(@PathVariable("id") Long id){
        return emailService.send(id);
    }
}
