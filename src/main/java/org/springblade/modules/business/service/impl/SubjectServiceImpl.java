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
import org.springblade.modules.business.entity.Subject;
import org.springblade.modules.business.vo.SubjectVO;
import org.springblade.modules.business.mapper.SubjectMapper;
import org.springblade.modules.business.service.ISubjectService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.Date;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2021-02-18
 */
@Service
@AllArgsConstructor
public class SubjectServiceImpl extends BaseServiceImpl<SubjectMapper, Subject> implements ISubjectService {
	private final UserMapper userMapper;
	private final SubjectMapper subjectMapper;
	@Override
	public IPage<SubjectVO> selectSubjectPage(IPage<SubjectVO> page, SubjectVO subject) {
		return page.setRecords(baseMapper.selectSubjectPage(page, subject));
	}

	@Override
	public Boolean select(String id) {
		try {
			Long userId = SecureUtil.getUserId();
			User user = userMapper.selectById(userId);
			String userName = user.getRealName();
			Subject s = new Subject();
			s.setStudentName(userName);
			Subject one = baseMapper.selectOne(Condition.getQueryWrapper(s));
			//当前登录学生已有选题
			if (one != null) {
				one.setStudentName(null);
				one.setSelectTime(null);
				one.setProgress("待选择");
				subjectMapper.updateById(one);
			}
			Subject subject = baseMapper.selectById(id);
			subject.setProgress("已被选");
			subject.setSelectTime(new Date());
			subject.setStudentName(userName);
			baseMapper.updateById(subject);
			return true;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
	}

	@Override
	public Boolean editProgress(String id, String progress) {
		Subject subject = baseMapper.selectById(id);
		if (subject == null) {
			return false;
		}
		subject.setProgress(progress);
		baseMapper.updateById(subject);
		return true;
	}
}
