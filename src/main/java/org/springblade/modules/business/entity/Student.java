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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-02-05
 */
@Data
@TableName("t_student")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Student对象", description = "Student对象")
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;
    /**
     * 老师id
     */
    @ApiModelProperty(value = "老师id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long teacherId;


}
