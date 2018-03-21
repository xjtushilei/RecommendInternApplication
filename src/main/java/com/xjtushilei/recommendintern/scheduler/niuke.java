package com.xjtushilei.recommendintern.scheduler;

import com.jayway.jsonpath.JsonPath;
import com.xjtushilei.recommendintern.entity.JobInfo;
import com.xjtushilei.recommendintern.entity.Log;
import com.xjtushilei.recommendintern.repository.JobInfoRepository;
import com.xjtushilei.recommendintern.repository.LogRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class niuke {
    @Autowired
    JobInfoRepository jobInfoRepository;

    @Autowired
    LogRepository logRepository;

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void get() {
        RestTemplate restTemplate = new RestTemplate();
        List<String> addList = Arrays.asList("北京", "上海", "杭州", "成都", "深圳", "西安", "苏州", "广州", "南京", "青岛", "大连", "武汉", "北京/杭州", "北京上海");
//        List<String> addList = Arrays.asList("北京", "上1海");
        addList.parallelStream().forEach(add -> {
            int totalPage = JsonPath.read(restTemplate.getForObject("https://www.nowcoder.com/recommend-intern/list?token=&page=1&address=" + add, String.class), "$.data.totalPage");
            List<String> pageList = new ArrayList<>();
            for (int i = 1; i <= totalPage; i++) {
                pageList.add(String.valueOf(i));
            }
            pageList.parallelStream().forEach(page -> {
                String data = restTemplate.getForObject("https://www.nowcoder.com/recommend-intern/list?token=&page=" + page + "&address=" + add, String.class);
                List<Map<String, Object>> list = JsonPath.read(data, "$.data.jobList[*]");
                list.parallelStream().forEach(map -> {
                    String url = "https://www.nowcoder.com/recommend-intern/" + map.get("internCompanyId") + "?jobId=" + map.get("id");
                    try {
                        String content = Jsoup.connect(url).get().select(".nk-content").text();
                        JobInfo jobInfo = new JobInfo("niuke" + map.get("id"),
                                (String) map.get("title"),
                                url,
                                new Date((long) map.get("updateTime")),
                                (String) map.get("address"), content);
//                        System.out.println(jobInfo.getTitle());
                        jobInfoRepository.save(jobInfo);
                    } catch (IOException e) {
                        logRepository.save(new Log(e.getMessage() + "page：" + page + "-address:" + add, new Date(), "失败"));

                    }
                });
            });
            System.out.println("更新地区:" + add + "-time:" + new Date().toLocaleString());
        });
        logRepository.save(new Log("定时任务-牛客网更新成功", new Date(), "成功"));
    }
}
