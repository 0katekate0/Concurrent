package com.qg.utils;
import com.qg.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/***
 * Kafka 工具类
 *
 * created by 北大课工场
 */
@Component
public class KafkaUtil {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;

    /***
     * 发送普通日志的方法
     * @param message
     * @return
     */
    public String sendInfoMessage(String message){
        try{
            kafkaTemplate.send(kafkaConfig.getModuleName()+"-"+ KafkaConfig.LogType.INFO, KafkaConfig.qgKey,message);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
    /***
     * 发送异常日志的方法
     * @param em
     * @return
     */
    public String sendErrorMessage(Exception em){
        try{
            Writer writer=new StringWriter();
            PrintWriter pw=new PrintWriter(writer);
            em.printStackTrace(pw);
            kafkaTemplate.send(kafkaConfig.getModuleName()+"-"+ KafkaConfig.LogType.ERROR, KafkaConfig.qgKey,writer.toString());
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
