package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoNeedLogin;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.FileAddDTO;
import cn.edu.ncu.pojo.dto.FileQueryDTO;
import cn.edu.ncu.pojo.vo.FileVO;
import cn.edu.ncu.pojo.vo.UploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @Author: XiongZhiCong
 * @Description: 文件服务API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_FILE})
public interface FileApi {

    @ApiOperation(value = "文件本地上传", notes = "文件本地上传")
    @PostMapping("/api/file/localUpload/{moduleType}")
     ResponseDTO<UploadVO> localUpload(@RequestParam("file")MultipartFile file, @PathVariable Integer moduleType) throws Exception ;

    @ApiOperation(value = "获取本地文件URL", notes = "获取文件URL")
    @PostMapping("/api/file/get")
    ResponseDTO<String> localGetFile(String path) ;

    @ApiOperation(value = "文件阿里云上传", notes = "文件阿里云上传")
    @PostMapping("/api/file/aliYunUpload/{moduleType}")
     ResponseDTO<UploadVO> aliYunUpload(@RequestParam("file")MultipartFile file, @PathVariable Integer moduleType) throws Exception ;

    @ApiOperation(value = "获取阿里云文件URL", notes = "获取阿里云文件URL")
    @PostMapping("/api/file/aliYunGet")
     ResponseDTO<String> aliYunGet(String path) ;

    @ApiOperation(value = "文件七牛云上传", notes = "文件七牛云上传")
    @PostMapping("/api/file/qiNiuUpload/{moduleType}")
     ResponseDTO<UploadVO> qiNiuUpload(@RequestParam("file")MultipartFile file, @PathVariable Integer moduleType) throws Exception ;

    @ApiOperation(value = "获取七牛云文件URL", notes = "获取七牛云URL")
    @PostMapping("/api/file/qiNiuGet")
     ResponseDTO<String> qiNiuGet(String path) ;

    @ApiOperation(value = "系统文件查询")
    @PostMapping("/api/file/query")
     ResponseDTO<PageResultDTO<FileVO>> queryListByPage(@RequestBody FileQueryDTO queryDTO) ;

    @ApiOperation(value = "系统文件下载通用接口（流下载）")
    @GetMapping("/api/file/downLoad")
    @NoNeedLogin
     ResponseEntity<byte[]> downLoadById(Long id, HttpServletRequest request) throws UnsupportedEncodingException;

    @ApiOperation(value = "系统文件保存通用接口")
    @PostMapping("/api/file/save")
     ResponseDTO<String> saveFile(@Valid @RequestBody FileAddDTO addDTO) ;
}
