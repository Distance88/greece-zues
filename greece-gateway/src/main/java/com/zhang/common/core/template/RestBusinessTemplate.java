package com.zhang.common.core.template;


import com.zhang.common.core.exception.BizCoreException;
import com.zhang.common.core.exception.ErrorCode;
import com.zhang.common.core.restful.CommonRestResult;
import com.zhang.common.core.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/17/8:56
 */
@Slf4j
public class RestBusinessTemplate {

	private static final Logger BIZ_LOG = LoggerFactory.getLogger("bizCoreException");

	/**
	 * 通过模板执行业务，这里不需要事务
	 * 
	 * @param callback
	 * @return
	 */
	public static CommonRestResult execute(Callback callback) {
		return doExecute(callback, null);
	}


	/**
	 * 使用默认的事务管理器执行业务
	 * 
	 * @param callback
	 * @return
	 */
	public static CommonRestResult transaction(Callback callback) {

		TransactionTemplate transactionTemplate = ApplicationContextUtil.getBean(TransactionTemplate.class);

		Assert.notNull(transactionTemplate, "default TransactionTemplate must not be null");
		return doExecute(callback, transactionTemplate);

	}

	/**
	 * 使用指定的事务管理器执行业务
	 * 
	 * @param callback
	 * @return
	 */
	public static CommonRestResult transaction(Callback callback, TransactionTemplate transactionTemplate) {

		Assert.notNull(transactionTemplate, "param transactionTemplate must not be null");
		return doExecute(callback, transactionTemplate);

	}

	private static CommonRestResult doExecute(Callback callback, TransactionTemplate transactionTemplate) {

		CommonRestResult restResult = new CommonRestResult();
		restResult.setMessage("");
		try {
			restResult.setStatus(CommonRestResult.SUCCESS);

			Object object = null;
			if (transactionTemplate != null) {
				object = transactionTemplate.execute(status -> {
					return callback.doExecute();
				});
			} else {
				object = callback.doExecute();
			}

			restResult.setContent(object);
		} catch (BizCoreException e) {
			// 这里是业务异常
			BIZ_LOG.error("业务异常:code={}, message={}", e.getCode(), e.getMessage());

			restResult.setCode(ErrorCode.SYSTEM_EXCEPTION.getCode());
			restResult.setStatus(CommonRestResult.FAIL);
			restResult.setMessage(e.getMessage());

			if(e.getContent() != null && e.getCode() == ErrorCode.REDIRECT){
				restResult.getMetadata().put("redirectContent", e.getContent());
			}

			if(e.getContent() != null && e.getCode() == ErrorCode.REFRESH){
				restResult.getMetadata().put("refreshContent", e.getContent());
			}

			// 这里设置特殊业务吗
			if (e.getCode() != null) {
				restResult.setCode(e.getCode().getCode());
			}
		} catch (Exception e) {
			// 这里是系统异常
			log.error("系统异常", e);

			restResult.setCode(ErrorCode.SYSTEM_EXCEPTION.getCode());
			restResult.setStatus(CommonRestResult.FAIL);
			restResult.setMessage(e.getMessage());
		}

		return restResult;
	}

	/**
	 * 执行回调
	 * 
	 * @param <T>
	 */
	public interface Callback<T> {
		public T doExecute();
	}
}
