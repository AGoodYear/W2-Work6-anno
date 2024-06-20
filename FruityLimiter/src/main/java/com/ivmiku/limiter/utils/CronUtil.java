package com.ivmiku.limiter.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CronUtil {
    /**
     * 每年format格式
     */
    public static final String YEAR = "ss mm HH dd MM ? yyyy";

    /**
     * 每周format格式
     */
    public static final String MONDAY = "ss mm HH ? * 1";
    public static final String TUESDAY = "ss mm HH ? * 2";
    public static final String WEDNESDAY = "ss mm HH ? * 3";
    public static final String THURSDAY = "ss mm HH ? * 4";
    public static final String FRIDAY = "ss mm HH ? * 5";
    public static final String SATURDAY = "ss mm HH ? * 6";
    public static final String SUNDAY = "ss mm HH ? * 7";

    /**
     * 每天format格式
     */
    public static final String EVERYDAY = "ss mm HH * * ?";

    /**
     * 间隔-每天format格式
     */
    public static final String INTERVAL_DAY = "0 0 0 1/param * ? ";

    /**
     * 间隔-每小时format格式
     */
    public static final String INTERVAL_HOUR = "0 0 0/param * * ?";

    /**
     * 间隔-指定小时的多少分钟
     */
    public static final String INTERVAL_HOUR_MINUTE = "0 minute */param * * ?";

    /**
     * 间隔-每分钟format格式
     */
    public static final String INTERVAL_MINUTE = "0 0/param * * * ? ";

    /**
     * 每分钟多少秒执行一次
     */
    public static final String INTERVAL_MINUTE_SECONDS = "seconds 0/param * * * ? ";

    /**
     * 间隔-每秒format格式
     */
    public static final String INTERVAL_SECONDS = "*/param * * * * ? ";


    /**
     * date格式化为String
     *
     * @param date       date
     * @param dateFormat format格式
     * @return String
     * @author longwei
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 时间转换Cron表达式
     *
     * @param date       date
     * @param dateFormat format格式
     * @return Cron表达式
     * @author longwei
     */
    public static String getCron(Date date, String dateFormat) {
        return formatDateByPattern(date, dateFormat);
    }


    /**
     * 间隔天转换Cron表达式
     *
     * @param param 天
     * @return Cron表达式
     * @author longwei
     */
    public static String getIntervalDayCron(String param) {
        return INTERVAL_DAY.replace("param", param);
    }

    /**
     * 间隔小时转换Cron表达式
     *
     * @param param 小时
     * @return Cron表达式
     * @author longwei
     */
    public static String getIntervalHourCron(String param) {
        return INTERVAL_HOUR.replace("param", param);
    }

    /**
     * 间隔分钟转换Cron表达式
     *
     * @param param 分钟
     * @return Cron表达式
     * @author longwei
     */
    public static String getIntervalMinuteCron(String param) {
        return INTERVAL_MINUTE.replace("param", param);
    }

    /**
     * 间隔秒转换Cron表达式
     * 使用此方法注意, quartz 中是不允许秒数超过60的，否则会报错，
     * 此方法 如果 s > 60 != 60 会有损失时间精度， 比如你想1分30秒执行一次
     * 可能只是会每分的30秒执行一次， 这样看来还是一分钟执行一次， 同样超过
     * 一小时也是.
     *
     * @param param 秒
     * @return Cron表达式
     * @author longwei
     */
    public static String getIntervalSecondsCron(String param) {

        Integer i = Integer.valueOf(param);

        // 判断多少秒
        if (i > 59) {

            // 超过 59 秒使用分钟 四舍五入 分钟会存在一些时间的偏移
//            long minutes = Math.round(i / 60.0);
            long minutes = i / 60; // 分钟

            // 判断有没有超过 59 分钟 否则轮小时
            if (minutes > 59) {

                ///606/ 小时 80 / 60 = 1.333 算出小时
                long hour = minutes / 60;

                // 计算出剩余多少分钟
                long min = minutes % 60;

                // 如果有多余分钟
                if (min > 0) {
                    // 替换字符串
                    String str = INTERVAL_HOUR_MINUTE.replace("param", hour + "");
                    return str.replace("minute", min + "");
                }

                return INTERVAL_HOUR.replace("param", hour + "");

            }

            // 判断剩余多少秒
            long seconds = i % 60;

            if (seconds > 0) {
                // 进行替换拼接cron 有可能就是 1分25秒执行一次 每分钟的第25s执行
                String str = INTERVAL_MINUTE_SECONDS.replace("param", minutes + "");

                return str.replace("seconds", seconds + "");
            }

            return INTERVAL_MINUTE.replace("param", minutes + "");
        }

        return INTERVAL_SECONDS.replace("param", param);
    }
}
