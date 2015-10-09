package com.zcl.service;

import com.zcl.database.DataBaseMapper;
import com.zcl.entity.RecordCollection;
import com.zcl.util.ExcelUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2015/10/7.
 */
public class ReportService {

    private Logger logger = Logger.getLogger(ReportService.class.getName());

    public String exportExcel(String queryDate){
        String flag = "0";
        FileOutputStream out = null;
        try {
            String title = "催收员拨打量统计";
            String[] headers ={"编号","催收人","拨打时长(秒)","债务人总拨打量","亲友拨打量"};
            List<RecordCollection> dataList = new DataBaseMapper().getList(queryDate);
            String fileName = queryDate + "_统计催收员拨打信息("+new SimpleDateFormat("yyyyMMdd_HHssmm").format(new Date())+").xls";
            File file = new File(fileName);
            if(file.exists()){
                file.delete();
            }
            out = new FileOutputStream(file);

            ExcelUtil excelUtil = new ExcelUtil();
            excelUtil.exportExcel(title,headers,dataList,out);
            flag = "1";
            logger.info("生成文件成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
