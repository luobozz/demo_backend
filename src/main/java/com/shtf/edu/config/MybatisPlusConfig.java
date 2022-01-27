package com.shtf.edu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig class
 *
 * @author chenlingyu
 * @date 2020/4/16 9:57
 */
@Configuration
@MapperScan(value={"com.shtf.edu.mapper"})
public class MybatisPlusConfig {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}
}
