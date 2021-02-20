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

import org.springblade.modules.business.entity.Subject;
import org.springblade.modules.business.vo.SubjectVO;
import org.springblade.modules.business.mapper.SubjectMapper;
import org.springblade.modules.business.service.ISubjectService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-02-18
 */
@Service
public class SubjectServiceImpl extends BaseServiceImpl<SubjectMapper, Subject> implements ISubjectService {

	@Override
	public IPage<SubjectVO> selectSubjectPage(IPage<SubjectVO> page, SubjectVO subject) {
		return page.setRecords(baseMapper.selectSubjectPage(page, subject));
	}

}
