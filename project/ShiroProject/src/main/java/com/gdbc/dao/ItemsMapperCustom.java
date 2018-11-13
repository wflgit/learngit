package com.gdbc.dao;


import java.util.List;

import com.gdbc.domain.ItemsCustom;
import com.gdbc.domain.ItemsQueryVo;


/**
 * 
 * <p>
 * Title: ItemsMapperCustom
 * </p>
 * <p>
 * Description:商品自定义mapper
 * </p>
 */
public interface ItemsMapperCustom {
	// 商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception;

}
