package com.athena.omega.mvp.component;

import com.athena.omega.mvp.action.BaseAction;
import com.athena.omega.mvp.component.form.FormElement;
import com.athena.omega.mvp.enums.Orientation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 表单
 *
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Form extends BaseComponent {
    /**
     * 表单元素
     */
    private final List<FormElement> elements = new ArrayList<>();
    /**
     * 表单操作
     */
    private final List<BaseAction> actions = new ArrayList<>();
    /**
     * 附件
     */
    private final Map<String, List<File>> attachments = new HashMap<>();
    /**
     * 最后值
     */
    private final Map<String, Object> lastValues = new HashMap<>();
    /**
     * 是否有验证码
     */
    private boolean captcha = false;
    /**
     * 文本
     */
    private String label;
    /**
     * 提交按钮文本
     */
    private String submitLabel;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 是否模态
     */
    private boolean modal = false;
    /**
     * 方向
     */
    private Orientation orientation = Orientation.AUTO;
    /**
     * 是否固定
     */
    private boolean fixed = false;
    /**
     * 描述
     */
    private String description;
    /**
     * 模型
     */
    private Object model;
    /**
     * 更新回调
     */
    private Consumer<Form> onUpdateCallback;
    /**
     * 提交
     */
    private Consumer<Form> onSubmit;
    /**
     * 最后事件
     */
    private String lastEvent;
    /**
     * 最后事件消息
     */
    private String lastEventMessage;
}
