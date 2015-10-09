package com.zcl.database;

import com.mysql.jdbc.StringUtils;
import com.zcl.entity.RecordCollection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2015/10/6.
 */
public class DataBaseMapper {
    private Logger logger = Logger.getLogger(DataBaseMapper.class.getName());

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://qcloud.zz.com:33306/rainbow";
    private String user = "fengkong";
    private String password = "RX7ZmRmyYbnmA8";

    public Connection getConn() {
        Connection conn = null;
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed()) {
                logger.info("数据库连接成功！");
            }
        } catch (Exception e) {
            logger.info("数据库连接失败！");
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }

    //查询催收人的拨打记录
    public List getList(String queryDate) {
        List<RecordCollection> totalRecordList = new ArrayList<RecordCollection>();
        List<RecordCollection> friendsTotalRecordList = new ArrayList<RecordCollection>();
        Connection conn = null;
        ResultSet rs = null;
        //统计催收人每天的总拨打时长和对债务人的拨打次数
        String sql_1 = "SELECT r.user_gid as userGid, r.collector_name AS collectorName,sum(r.collect_duration) AS collectDuration,count(gid) AS totalRecord FROM record_collection r WHERE r.collect_type = '0' AND r.create_time BETWEEN UNIX_TIMESTAMP(DATE_FORMAT('"+queryDate+ "', '%y-%m-%d 00:00:00')) AND UNIX_TIMESTAMP(DATE_FORMAT('"+queryDate+ "', '%y-%m-%d 23:59:59'))  GROUP BY r.user_gid ORDER BY count(gid) DESC";
        //统计催收人对债务人亲友的拨打量
        String sql_2 = "SELECT  c.user_gid as userGid, c.collector_name AS collectorName, count(c.gid) AS friendTotalRecord FROM record_collection c WHERE c.collect_type = '0' AND c.phone_source = 2 AND c.create_time BETWEEN UNIX_TIMESTAMP(DATE_FORMAT('" +queryDate+ "', '%y-%m-%d 00:00:00')) AND UNIX_TIMESTAMP(DATE_FORMAT('"+queryDate+ "', '%y-%m-%d 23:59:59')) GROUP BY  c.user_gid ORDER BY count(c.gid) DESC";
        try {
            conn = getConn();
            if (conn != null && !conn.isClosed()) {
                Statement statement = conn.createStatement();
                rs = statement.executeQuery(sql_1);
                RecordCollection record = null;
                while (rs.next()) {
                    record = new RecordCollection();
                    record.setUserGid(rs.getString("userGid"));
                    record.setCollectorNname(rs.getString("collectorName"));
                    record.setCollectDuration(rs.getString("collectDuration"));
                    record.setTotalRecord(rs.getInt("totalRecord"));

                    totalRecordList.add(record);
                }

                rs = statement.executeQuery(sql_2);
                while (rs.next()) {
                    record = new RecordCollection();
                    record.setUserGid(rs.getString("userGid"));
                    record.setCollectorNname(rs.getString("collectorName"));
                    record.setFriendTotalRecord(rs.getInt("friendTotalRecord"));

                    friendsTotalRecordList.add(record);
                }

                for (RecordCollection recordCollection : totalRecordList) {
                    String userGid = recordCollection.getUserGid();
                    for (RecordCollection friendsRecord : friendsTotalRecordList) {
                        if (friendsRecord.getUserGid().equals(userGid)) {
                            recordCollection.setFriendTotalRecord(friendsRecord.getFriendTotalRecord());
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalRecordList;
    }

    public static void main(String[] args) {
        DataBaseMapper database = new DataBaseMapper();
        List list = database.getList("2015-09-25");
        System.out.println(list.size());
    }

}
