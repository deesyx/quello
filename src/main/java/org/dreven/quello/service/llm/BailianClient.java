package org.dreven.quello.service.llm;

import cn.hutool.core.util.StrUtil;

public class BailianClient {

    protected String formatToJson(String output) {
        if (StrUtil.isBlank(output)) {
            return null;
        }
        // 删除markdown格式
        if (output.contains("```json")) {
            output = output.substring(output.indexOf("```json") + 7, output.lastIndexOf("```"));
        }

        // 删除json中的注释
        // output = output.replaceAll("://", ":##");// http:// https://这种
        // Pattern pattern = Pattern.compile("//[^\n]*");
        // output = pattern.matcher(output).replaceAll("").trim();
        // output = output.replaceAll(":##", "://");
        return output;
    }
}
