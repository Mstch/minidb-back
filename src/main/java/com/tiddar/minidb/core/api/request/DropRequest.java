package com.tiddar.minidb.core.api.request;

import lombok.Data;

/**
 * @author zhangweichen
 * @date 2019/9/20 2:39 下午
 */
@Data
public class DropRequest  extends Request{
    String name;
}
