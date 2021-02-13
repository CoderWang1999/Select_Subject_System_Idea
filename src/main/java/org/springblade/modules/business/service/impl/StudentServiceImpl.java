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
package org.springblade.modules.business.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.modules.business.entity.Student;
import org.springblade.modules.business.vo.StudentVO;
import org.springblade.modules.business.mapper.StudentMapper;
import org.springblade.modules.business.service.IStudentService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2021-02-05
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student> implements IStudentService {
	private final UserMapper userMapper;
	private final StudentMapper studentMapper;

	@Override
	public IPage<StudentVO> selectStudentPage(IPage<StudentVO> page, StudentVO student) {
		Long userId = SecureUtil.getUserId();
		User user = userMapper.selectById(userId);
		String userDeptId = user.getDeptId();
		List<StudentVO> vos = baseMapper.selectStudentPage(page, student, userDeptId);
		List<StudentVO> res = new ArrayList<>();
		for (StudentVO vo : vos) {
			vo.setBooleanChoose("否");
			Student student1 = new Student();
			student1.setStudentId(userId);
			Student one = studentMapper.selectOne(Condition.getQueryWrapper(student1));
			if (one != null && one.getTeacherId() != null && one.getTeacherId().equals(vo.getTeacherId())) {
				vo.setBooleanChoose("是");
			}
			res.add(vo);
		}
		page.setRecords(res);
		return page;
	}

}
