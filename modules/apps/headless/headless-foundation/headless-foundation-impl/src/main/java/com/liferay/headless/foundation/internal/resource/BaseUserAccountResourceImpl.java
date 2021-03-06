/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.UserAccount;
import com.liferay.headless.foundation.resource.UserAccountResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceImpl
	implements UserAccountResource {

	@Override
	public UserAccount getMyUserAccount(Integer id) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getMyUserAccountPage(Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public UserAccount getUserAccount(Integer id) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<UserAccount> getUserAccountPage(
			String fullnamequery, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public UserAccount postUserAccount() throws Exception {
		return new UserAccount();
	}

	@Override
	public UserAccount postUserAccountBatchCreate() throws Exception {
		return new UserAccount();
	}

	@Override
	public UserAccount putUserAccount(Integer id) throws Exception {
		return new UserAccount();
	}

}