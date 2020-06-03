package com.plat.common.tool;

import org.apache.commons.lang3.StringUtils;

public class StringTool {

    /**
     * 将字符串转换成驼峰形式
     *
     * @param property 待转换的字符串
     * @return String
     */
    public static String convertToCamelCase(String property) {
        if (property.contains("_")) {
            String[] ns = property.split("_");
            StringBuilder buffer = new StringBuilder();
            for (String n : ns) {
                buffer.append(StringUtils.capitalize(n));
            }
            return StringUtils.uncapitalize(buffer.toString());
        }
        return property;
    }

    /**
     * 将字符串转换成下划线格式
     *
     * @param source
     * @return
     */
    public static String convertToSnakeCase(String source) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char chCase = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(chCase);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

}
