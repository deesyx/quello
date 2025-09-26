package org.dreven.quello.service.llm;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResponseFormat;
import com.alibaba.dashscope.common.Role;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DashScopeLLMClient extends BailianClient {

    @Value("${bailian.api-key:}")
    private String bailianApiKey;

    @SneakyThrows
    public String getTextAnswer(String modelName, String systemMessage, String userMessage) {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(systemMessage)
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userMessage)
                .build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(bailianApiKey)
                .model(modelName)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();

        Generation gen = new Generation();
        GenerationResult result = gen.call(param);
        String output = extractStringOutput(result);
        log.info("模型输出：{}", output);
        return output;
    }

    @SneakyThrows
    public Flowable<GenerationResult> getStreamAnswer(String modelName, String systemMessage, String userMessage) {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(systemMessage)
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userMessage)
                .build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(bailianApiKey)
                .model(modelName)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();

        Generation gen = new Generation();
        return gen.streamCall(param);
    }

    /**
     * qwen-max 系列
     * qwen-plus 系列（非思考模式）
     * qwen-turbo 系列（非思考模式）
     * qwen-开源系列
     */
    @SneakyThrows
    public <T> T getJsonAnswer(String modelName, String systemMessage, String userMessage, Class<T> clazz) {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(systemMessage)
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userMessage)
                .build();
        ResponseFormat jsonMode = ResponseFormat.builder().type("json_object").build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(bailianApiKey)
                .model(modelName)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .responseFormat(jsonMode)
                .build();

        Generation gen = new Generation();
        GenerationResult result = gen.call(param);
        String output = extractStringOutput(result);
        log.info("模型输出：{}", output);
        try {
            return JsonUtils.parseObject(output, clazz);
        } catch (Exception e) {
            log.info("json转换失败", e);
            return null;
        }
    }

    private String extractStringOutput(GenerationResult result) {
        Optional<GenerationOutput.Choice> choice = result.getOutput().getChoices().stream().findFirst();
        if (choice.isEmpty()) {
            return "";
        }
        Message message = choice.get().getMessage();
        return message.getContent();
    }
}
