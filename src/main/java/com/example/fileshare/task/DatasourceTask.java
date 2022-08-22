package com.example.fileshare.task;

import com.example.fileshare.common.SessionManager;
import com.example.fileshare.vo.OnlineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Simple class description
 *
 * @author zhaojianmin 2022-07-25 09:44
 * @since 1.0
 */
@Component
@Slf4j
public class DatasourceTask {

    @Autowired
    private DataSource dataSource;


    @Scheduled(cron = "*/10 * * * * ?")
    public void configureTasks() throws Exception{
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select update_time from t_edit limit 1");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                log.info((resultSet.getString(1)));
            }
        }
        log.debug("定时任务执行完毕");
    }

}
