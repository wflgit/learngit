package com.gdbc.service.impl;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdbc.dao.ItemsMapper;
import com.gdbc.dao.ItemsMapperCustom;
import com.gdbc.domain.Items;
import com.gdbc.domain.ItemsCustom;
import com.gdbc.domain.ItemsQueryVo;
import com.gdbc.exception.CustomException;
import com.gdbc.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService {
	
	//注入mapper
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;

	//商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {
		
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	public ItemsCustom findItemsById(int id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		//如果查询的商品信息为空，抛出系统 自定义的异常
		if(items==null){
				throw new CustomException("修改商品信息不存在");
		}
		//在这里随着需求的变量，需要查询商品的其它的相关信息，返回到controller
		
		ItemsCustom itemsCustom = new ItemsCustom();
		//将items的属性拷贝到itemsCustom
		BeanUtils.copyProperties(items, itemsCustom);
		
		return itemsCustom;
	}

	public void updateItems(Integer id,ItemsCustom itemsCustom) throws Exception {
		//写业务代码
		
		//对于关键业务数据的非空校验
		if(id == null){
			//抛出异常，提示调用接口的用户，id不能为空
			//...
		}
		//itemsMapper.updateByPrimaryKey(itemsCustom);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
		
	}

	public void deleleItems(Integer id) throws Exception {
		//关于级联删除
		//如果在数据库表中配置外键，设置为级联删除 ，在程序中去删除父表记录，子表由数据库mysql自动删除,
		//此方法不建议使用，尽量将业务逻辑代码都在service维护。
		
		//通过下边程序进行删除，
		//先删除id关联的子表的数据(商品关联的所有子表全部删除 )
		//...调用mapper删除子表
		//再删除父表（商品表）
		//....调用mapper删除父亲表
		
		
	}

}
