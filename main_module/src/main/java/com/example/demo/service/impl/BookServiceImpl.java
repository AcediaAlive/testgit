package com.example.demo.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public BookEntity findBookByName(String name) {
        return bookDao.selectByName(name);
    }


    @Override
    @SentinelResource(value = "findBookByPrice", blockHandler = "findBookByPriceBlock")
    public List<BookEntity> findBookByPrice(double price) {
        return bookDao.getByPrice(price);
    }

    //注意细节，一定要跟原函数的返回值和形参一致，并且形参最后要加个BlockException参数
    //否则会报错，FlowException: null
    public List<BookEntity> findBookByPriceBlock(double price, BlockException ex) {
        //打印异常
        ex.printStackTrace();
        List<BookEntity> bl=new ArrayList<>();
        bl.add(BookEntity.builder().id(0L).price(price).name("System busy").author("Try again later").build());
        return bl;
    }

    @Override
    public void addBook(BookEntity book) {
        bookDao.insert(book);
    }

    @PostConstruct
    private static void initFlowRules() {
        // 创建一个FlowRule列表，用于存储流量控制规则
        List<FlowRule> rules = new ArrayList<>();
        // 创建一个新的FlowRule对象，设置流量控制的相关参数
        FlowRule rule = new FlowRule();
        // 设置受保护的资源名称
        rule.setResource("findBookByPrice");
        // 设置流量控制的方式为QPS（每秒查询率）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS的阈值为2，即每秒最多允许2次访问
        rule.setCount(2);
        // 将这个流量控制规则添加到规则列表中
        rules.add(rule);

        FlowRule rule2 = new FlowRule();
        rule2.setResource("test");
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(2);
        rules.add(rule2);

        // 加载这些规则到FlowRuleManager中，使其生效
        FlowRuleManager.loadRules(rules);
    }
}
