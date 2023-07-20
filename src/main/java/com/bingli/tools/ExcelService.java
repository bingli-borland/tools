package com.bingli.tools;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelService extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (data.get(0) != null && data.get(0).equals("分部")) {
            for (int key : data.keySet()) {
                System.out.println("@ExcelProperty(\"" + data.get(key) + "\")");
            }
        }
        LOGGER.info("解析到的数据: {}", data);
        list.add(data);
    }

    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        LOGGER.info("解析到的表头数据: {}", headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    /**
     * 如果有必要，存储到数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}
