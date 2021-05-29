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
package org.springblade.modules.business.controller;

import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.business.entity.Teacher;
import org.springblade.modules.business.service.ITeacherService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.business.entity.Student;
import org.springblade.modules.business.vo.StudentVO;
import org.springblade.modules.business.service.IStudentService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * @author CoderWang
 * @since 2021-02-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("blade-student/student")
@Api(value = "选择指导老师", tags = "选择指导老师")
public class StudentController extends BladeController {

	private IStudentService studentService;
	private ITeacherService teacherService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入student")
	public R<Student> detail(Student student) {
		Student detail = studentService.getOne(Condition.getQueryWrapper(student));
		return R.data(detail);
	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入student")
	public R<IPage<StudentVO>> page(StudentVO student, Query query) {
		IPage<StudentVO> pages = studentService.selectStudentPage(Condition.getPage(query), student);
		return R.data(pages);
	}

	/**
	 * 选择老师
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "选择老师", notes = "传入student")
	@Transactional(rollbackFor = Exception.class)
	public synchronized R save(@ApiParam(value = "老师id", required = true) @RequestParam(required = true) String teacherId) {
		Teacher t = new Teacher();
		t.setTeacherId(Long.parseLong(teacherId));
		Teacher teacher = teacherService.getOne(Condition.getQueryWrapper(t));
		Integer residualAmount = teacher.getResidualAmount();
		if (residualAmount == 0) {
			return R.fail("老师名额已满，请选择其他老师！");
		}
		teacher.setResidualAmount(residualAmount - 1);
		teacherService.updateById(teacher);
		BladeUser user = SecureUtil.getUser();
		Long userId = user.getUserId();
		Student student = new Student();
		student.setStudentId(userId);
		Student one = studentService.getOne(Condition.getQueryWrapper(student));
		if (one != null) {
			Long tId = one.getTeacherId();
			Teacher teacher1 = new Teacher();
			teacher1.setTeacherId(tId);
			Teacher teacher2 = teacherService.getOne(Condition.getQueryWrapper(teacher1));
			teacher2.setResidualAmount(teacher2.getResidualAmount() + 1);
			teacherService.updateById(teacher2);
			one.setTeacherId(Long.parseLong(teacherId));
			return R.status(studentService.updateById(one));
		}
		student.setTeacherId(Long.parseLong(teacherId));
		return R.status(studentService.save(student));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入student")
	public R update(@Valid @RequestBody Student student) {
		return R.status(studentService.updateById(student));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入student")
	public R submit(@Valid @RequestBody Student student) {
		return R.status(studentService.saveOrUpdate(student));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(studentService.deleteLogic(Func.toLongList(ids)));
	}
}
