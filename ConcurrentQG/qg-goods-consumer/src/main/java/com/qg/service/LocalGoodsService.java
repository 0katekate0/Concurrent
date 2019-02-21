package com.qg.service;

import com.qg.dto.ReturnResult;

public interface LocalGoodsService {

    public ReturnResult queryGoodsById(String id) throws Exception;

    public ReturnResult goodsGetMessage(String token, String goodsId) throws Exception;

    public ReturnResult flushGetGoodsStatus(String token, String goodsId) throws Exception;
}
