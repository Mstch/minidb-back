package com.tiddar.minidb.core.api;

import com.tiddar.minidb.core.api.request.InsertRequest;
import com.tiddar.minidb.core.api.request.SelectRequest;
import com.tiddar.minidb.core.model.Result;

/**
 * @author zhangweichen
 * @date 2019/9/12 10:51 上午
 */
public interface DML {

    int insert(InsertRequest request);

    Result select(SelectRequest request);

    int update();

    int delete();


}
