/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-02-18
 */
@Data
@TableName("t_subject")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Subject对象", description = "Subject对象")
public class Subject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 题目名称
     */
    @ApiModelProperty(value = "题目名称")
    private String subjectName;
    /**
     * 题目描述
     */
    @ApiModelProperty(value = "题目描述")
    private String discretion;
    /**
     * 选题人姓名
     */
    @ApiModelProperty(value = "选题人姓名")
    private String studentName;
    /**
     * 选题时间
     */
    @ApiModelProperty(value = "选题时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date selectTime;
    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    private String progress;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}
