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
package org.springblade.modules.business.vo;

import org.springblade.modules.business.entity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.springblade.modules.business.entity.Teacher;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudentVO对象", description = "StudentVO对象")
public class StudentVO extends Teacher {
	private static final long serialVersionUID = 1L;
	/*老师姓名*/
	private String teacherName;
	/*职称*/
	private String postName;
	/*所属学院*/
	private String dept;
	/*剩余名额*/
	private Integer residualAmount;
	/*联系方式*/
	private String teacherPhoneNumber;
	/*已选择*/
	private String booleanChoose;
}
