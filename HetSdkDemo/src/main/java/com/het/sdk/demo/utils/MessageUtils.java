package com.het.sdk.demo.utils;

import android.content.Context;

import com.het.basic.utils.SharePreferencesUtil;
import com.het.basic.utils.StringUtils;
import com.het.sdk.demo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MessageUtils {
    public MessageUtils() {
    }

    public static String limitStrLength(String fromString, int limitLength) {
        if(StringUtils.isBlank(fromString)) {
            return "";
        } else {
            limitLength = limitLength < 0?0:limitLength;
            StringBuilder t = new StringBuilder();
            return (StringUtils.size(fromString) > limitLength?t.append(fromString.substring(0, limitLength)).append("..."):t.append(fromString)).toString();
        }
    }

    public static String getFriendlyTime(Context context, Date date) {
        if(date == null) {
            return "";
        } else {
            String ftime = "";
            SimpleDateFormat formatStrToDate = new SimpleDateFormat("yyyy-MM-dd ");
            Calendar cal = Calendar.getInstance();
            String curDate = formatStrToDate.format(cal.getTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            cal2.add(10, 8);
            Date date1 = cal2.getTime();
            String paramDate = formatStrToDate.format(date1);
            if(!curDate.equals(paramDate)) {
                return paramDate;
            } else {
                int hour = (int)((cal.getTimeInMillis() - date1.getTime()) / 3600000L);
                int min = (int)((cal.getTimeInMillis() - date1.getTime()) / 60000L);
                if(hour == 0) {
                    if(min <= 1) {
                        ftime = context.getString(R.string.common_msg_just);
                    } else if(min > 1 && min <= 2) {
                        ftime = context.getString(R.string.common_msg_one_min_ago);
                    } else {
                        ftime = Math.max(min, 0) + " " + context.getString(R.string.common_msg_min_ago);
                    }
                } else if(hour <= 2) {
                    ftime = context.getString(R.string.common_msg_one_hour_ago);
                } else {
                    ftime = hour + " " + context.getString(R.string.common_msg_hour_ago);
                }

                return ftime;
            }
        }
    }

    public static String findCustomValue(Context context, int messageType, int titleOrIcon) {
        String value = "";
        switch(messageType) {
            case 0:
                if(titleOrIcon == 1) {
                    value = SharePreferencesUtil.getString(context, "deviced_msg_title_name");
                } else {
                    value = SharePreferencesUtil.getString(context, "deviced_msg_title_icon");
                }
                break;
            case 1:
                if(titleOrIcon == 1) {
                    value = SharePreferencesUtil.getString(context, "friend_msg_title_name");
                } else {
                    value = SharePreferencesUtil.getString(context, "friend_msg_title_icon");
                }
                break;
            case 2:
                if(titleOrIcon == 1) {
                    value = SharePreferencesUtil.getString(context, "deviced_msg_title_name");
                } else {
                    value = SharePreferencesUtil.getString(context, "deviced_msg_title_icon");
                }
                break;
            case 3:
                if(titleOrIcon == 1) {
                    value = SharePreferencesUtil.getString(context, "posts_msg_title_name");
                } else {
                    value = SharePreferencesUtil.getString(context, "posts_msg_title_icon");
                }
        }

        if(value == null) {
            value = "";
        }

        return value;
    }
}
