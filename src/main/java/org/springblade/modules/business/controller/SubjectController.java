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
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.business.entity.Subject;
import org.springblade.modules.business.vo.SubjectVO;
import org.springblade.modules.business.service.ISubjectService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * @author CoderWang
 * @since 2021-02-018
 */
@RestController
@AllArgsConstructor
@RequestMapping("blade-subject/subject")
@Api(value = "选题管理", tags = "选题管理")
public class SubjectController extends BladeController {

	private ISubjectService subjectService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入subject")
	public R<Subject> detail(Subject subject) {
		Subject detail = subjectService.getOne(Condition.getQueryWrapper(subject));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入subject")
	public R<IPage<Subject>> list(Subject subject, Query query) {
		IPage<Subject> pages = subjectService.page(Condition.getPage(query), Condition.getQueryWrapper(subject));
		return R.data(pages);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入subject")
	public R<IPage<SubjectVO>> page(SubjectVO subject, Query query) {
		IPage<SubjectVO> pages = subjectService.selectSubjectPage(Condition.getPage(query), subject);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入subject")
	public R save(@Valid @RequestBody Subject subject) {
		subject.setStudentName(null);
		subject.setProgress("待选择");
		return R.status(subjectService.save(subject));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入subject")
	public R update(@Valid @RequestBody Subject subject) {
		return R.status(subjectService.updateById(subject));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(subjectService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 选题
	 */
	@PostMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "选题", notes = "传入id")
	public synchronized R select(@ApiParam(value = "主键id", required = true) @RequestParam String id) {
		Subject subject = subjectService.getById(id);
		if (StringUtils.isNotEmpty(subject.getStudentName())) {
			return R.fail("此选题已被其他同学选择！");
		}
		return R.status(subjectService.select(id));
	}

	/**
	 * 修改进度
	 */
	@PostMapping("/editProgress")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "修改进度", notes = "传入progress")
	public R editProgress(@ApiParam(value = "主键id", required = true) @RequestParam String id, @ApiParam(value = "进度", required = true) @RequestParam String progress) {
		return R.status(subjectService.editProgress(id, progress));
	}
}
