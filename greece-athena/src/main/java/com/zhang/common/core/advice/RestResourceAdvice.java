package com.zhang.common.core.advice;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhang.common.core.errors.ErrorVM;
import com.zhang.common.core.errors.ExceptionTranslator;
import com.zhang.common.core.restful.CommonRestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouqin on 13/10/2016.
 */
@Slf4j
@ControllerAdvice("com.zhang.project.web.rest11")
public class RestResourceAdvice extends ExceptionTranslator implements ResponseBodyAdvice<Object> {

	@Resource
	private LocaleMessageSourceUtil localeMessageSourceUtil;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
		try {
			List<String> _envelope = request.getHeaders().get("envelope");
			boolean do_not_envelope = _envelope != null && _envelope.size() == 1 && _envelope.get(0).equals("false");

			if (body instanceof CommonRestResult || body instanceof ErrorVM || do_not_envelope) {
				if (body instanceof CommonRestResult && ((CommonRestResult) body).getContent() instanceof Page) {
					Page page = (Page) ((CommonRestResult) body).getContent();

					((CommonRestResult) body).setContent(page.getContent());
					((CommonRestResult) body).setMetadata(pageMetadata(page));
				}
				if (body instanceof CommonRestResult && ((CommonRestResult) body).getContent() instanceof IPage) {
                    IPage page = (IPage)((CommonRestResult) body).getContent();

                    ((CommonRestResult) body).setContent(page.getRecords());
                    ((CommonRestResult) body).setMetadata(pageMetadata(page));
                }
				
				if (body instanceof CommonRestResult) {
					// 处理message
					String message = localeMessageSourceUtil.getMessage(((CommonRestResult) body).getMessage());
					if (StringUtils.isNotBlank(message)) {
						((CommonRestResult) body).setMessage(message);
					}
				}
				
				return body;
			} else {
				String bodyString = ObjectUtil.isNull(body)? "null" : body.toString();
				throw new RuntimeException(StringUtils.join("process error,Rest Controller method return CommonRestResult , please use RestBusinessTemplate。 【body is ", bodyString, "】"));
			}
		} catch (RuntimeException e) {
			log.error("request process error", e);

			throw e;
		}
	}

	protected Map pageMetadata(Page page) {
		Map metadata = new HashMap();
		Map pageInfo = new HashMap();

		pageInfo.put("currentItemCount", page.getNumberOfElements());
		pageInfo.put("totalItems", page.getTotalElements());
		pageInfo.put("totalPages", page.getTotalPages());
		pageInfo.put("pageIndex", page.getNumber() + 1);
		pageInfo.put("itemsPerPage", page.getSize());

		metadata.put("page", pageInfo);
		return metadata;
	}

    protected Map pageMetadata(IPage page) {
        Map metadata = new HashMap();
        Map pageInfo = new HashMap();

        pageInfo.put("currentItemCount", page.getRecords().size());
        pageInfo.put("totalItems", page.getTotal());
        pageInfo.put("totalPages", page.getPages());
        pageInfo.put("pageIndex", page.getCurrent());
        pageInfo.put("itemsPerPage", page.getSize());

        metadata.put("page", pageInfo);
        return metadata;
    }
}
