package com.djh.admin.model.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 公共返回结果
 * @author sunyu
 *
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class CommonResult<T> implements Serializable {
	
	/**
	 * serialVersionUID: 序列化操作的时候系统会把当前类的serialVersionUID写入到序列化文件中，当反序列化时系统会去检测文件中的serialVersionUID，判断它是否与当前类的serialVersionUID一致，如果一致就说明序列化类的版本与当前类版本是一样的，可以反序列化成功，否则失败。
	 *
	 * serialVersionUID有两种显示的生成方式：
	 * 一是默认的1L，比如：private static final long serialVersionUID = 1L;
	 * 二是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，
	 */
	private static final long serialVersionUID = -7268040542410707954L;

	/**
	 * 是否成功
	 */
	private Boolean success = false;

	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * 装载数据
	 */
	private T data;

	/**
	 * 错误代码
	 */
	private int code;



	/**
	 * 默认构造器
	 */
	public CommonResult(){ }

}
