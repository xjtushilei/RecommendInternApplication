package com.xjtushilei.recommendintern.controller;


import com.xjtushilei.recommendintern.entity.JobInfo;
import com.xjtushilei.recommendintern.repository.JobInfoRepository;
import com.xjtushilei.recommendintern.repository.LogRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(description = "查询API")
public class MainController {

    @Autowired
    JobInfoRepository jobInfoRepository;

    @Autowired
    LogRepository logRepository;

    @GetMapping("/searchTitle")
    @ApiOperation(value = "根据标题查询", notes = "输入单个关键字，同时输入页数和每页的大小，返回结果查看")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "单个关键字", name = "key", defaultValue = "爱奇艺", required = true),
            @ApiImplicitParam(value = "第几页(从0开始)", name = "page", defaultValue = "0", required = true),
            @ApiImplicitParam(value = "每页显示多少内容", name = "size", defaultValue = "20", required = true)
    })
    public Page<JobInfo> get(String key, int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");

        Pageable pageable = PageRequest.of(page, size, sort);

        return jobInfoRepository.findAllByTitleContains(key, pageable);
    }

    @GetMapping("/searchTitleOrContent")
    @ApiOperation(value = "根据标题或者招聘内容查询", notes = "输入单个关键字，同时输入页数和每页的大小，返回结果查看")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "单个关键字", name = "key", defaultValue = "爱奇艺", required = true),
            @ApiImplicitParam(value = "第几页(从0开始)", name = "page", defaultValue = "0", required = true),
            @ApiImplicitParam(value = "每页显示多少内容", name = "size", defaultValue = "20", required = true)
    })
    public Page<JobInfo> get1(String key, int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");

        Pageable pageable = PageRequest.of(page, size, sort);

        return jobInfoRepository.findAllByTitleContainsOrContentContains(key, key, pageable);
    }

    @GetMapping("/findall")
    @ApiOperation(value = "获取所有招聘信息", notes = "输入页数和每页的大小，返回结果查看")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "第几页(从0开始)", name = "page", defaultValue = "0", required = true),
            @ApiImplicitParam(value = "每页显示多少内容", name = "size", defaultValue = "20", required = true)
    })
    public Page<JobInfo> get(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");

        Pageable pageable = PageRequest.of(page, size, sort);

        return jobInfoRepository.findAll(pageable);
    }


}
