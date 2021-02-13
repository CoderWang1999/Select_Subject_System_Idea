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
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.business.entity.Teacher;
import org.springblade.modules.business.vo.TeacherVO;
import org.springblade.modules.business.service.ITeacherService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 控制器
 *
 * @author CoderWang
 * @since 2021-02-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("blade-amount/teacher")
@Api(value = "可辅导学生数量管理", tags = "可辅导学生数量管理")
public class TeacherController extends BladeController {

	private ITeacherService teacherService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入teacher")
	public R<Teacher> detail(Teacher teacher) {
		Long id = teacher.getId();
		TeacherVO detail = teacherService.findById(id);
		return R.data(detail);
	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入teacher")
	public R<IPage<TeacherVO>> page(TeacherVO teacher, Query query) {
		IPage<TeacherVO> pages = teacherService.selectTeacherPage(Condition.getPage(query), teacher);
		return R.data(pages);
	}

	/**
	 * 设置可辅导学生数量
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "设置可辅导学生数量", notes = "传入数量")
	public R save(@ApiParam(value = "数量", required = true) @RequestParam(required = true) Integer studentAmount) {
		Teacher teacher = new Teacher();
		teacher.setStudentAmount(studentAmount);
		BladeUser user = SecureUtil.getUser();
		Long userId = user.getUserId();
		teacher.setTeacherId(userId);
		teacher.setResidualAmount(studentAmount);
		return R.status(teacherService.save(teacher));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入teacher")
	public R update(@ApiParam(value = "id", required = true) @RequestParam(required = true) String id,
					@ApiParam(value = "数量", required = true) @RequestParam(required = true) Integer studentAmount) {
		Teacher teacher = teacherService.getById(id);
		if (teacher!=null){
			//修改之前的人数
			Integer residualAmount = teacher.getResidualAmount();
			//修改之前的剩余名额
			Integer studentAmount1 = teacher.getStudentAmount();
			//修改之前已有学生数
			Integer amount=studentAmount1-residualAmount;
			//如果已有学生数量小于等于传入的修改值则执行修改
			if (amount<=studentAmount){
				teacher.setStudentAmount(studentAmount);
				teacher.setResidualAmount(studentAmount-amount);
				return R.status(teacherService.updateById(teacher));
			}
			else {
				return R.fail("当前已有学生数量大于您要修改的值，修改失败！");
			}
		}
		return null;
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<Long> list = Func.toLongList(ids);
		for (Long aLong : list) {
			Teacher byId = teacherService.getById(aLong);
			Integer studentAmount = byId.getStudentAmount();
			Integer residualAmount = byId.getResidualAmount();
			if (!studentAmount.toString().equals(residualAmount.toString())){
				return R.fail("删除失败，请核实您有无学生！");
			}
		}
		return R.status(teacherService.deleteLogic(Func.toLongList(ids)));
	}

}
