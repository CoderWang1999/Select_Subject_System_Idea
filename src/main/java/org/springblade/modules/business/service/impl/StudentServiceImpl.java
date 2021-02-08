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
import org.springblade.modules.business.entity.Student;
import org.springblade.modules.business.entity.Teacher;
import org.springblade.modules.business.mapper.TeacherMapper;
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
 *  服务实现类
 *
 * @author Blade
 * @since 2021-02-05
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student> implements IStudentService {
	private final UserMapper userMapper;
	@Override
	public IPage<StudentVO> selectStudentPage(IPage<StudentVO> page, StudentVO student) {
		List<StudentVO> vos = baseMapper.selectStudentPage(page, student);
		List<StudentVO> res=new ArrayList<>();
		if (vos.size()==0){
			return null;
		}
		for (StudentVO vo : vos) {
			Long studentId = vo.getStudentId();
			Long teacherId = vo.getTeacherId();
			User teacher = userMapper.selectById(teacherId);
			User stu = userMapper.selectById(studentId);
			vo.setTeacherName(teacher.getRealName());
			vo.setStudentName(stu.getRealName());
			res.add(vo);
		}
		page.setRecords(res);
		return page;
	}

}
