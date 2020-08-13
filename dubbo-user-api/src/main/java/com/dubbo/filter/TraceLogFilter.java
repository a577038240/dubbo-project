package com.dubbo.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import java.util.UUID;

/**
 * @author ChenHao
 * @description TODO 写点注释吧
 * @date 2020-08-11 9:14
 * @Since V1.0.0
 */
@Activate(group = {CommonConstants.CONSUMER})
@Slf4j
public class TraceLogFilter implements Filter {
    private static final String TRACE_ID = "trace_id";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(TRACE_ID);
        if (!StringUtils.isBlank(traceId)) {
            log.info("trace_id exist, trace_id = {}",traceId);
        } else {
            traceId = UUID.randomUUID().toString();
            log.info("trace_id not exist,generator trace_id = {}",traceId);
        }
        invocation.getAttachments().put(TRACE_ID, traceId);

        return invoker.invoke(invocation);
    }
}
