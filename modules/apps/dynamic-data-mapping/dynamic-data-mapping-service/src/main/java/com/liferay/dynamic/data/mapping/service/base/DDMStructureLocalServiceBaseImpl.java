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

package com.liferay.dynamic.data.mapping.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceLinkPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstancePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLinkFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLinkPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructurePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureVersionPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplatePersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the ddm structure local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl
 * @see com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDMStructureLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDMStructureLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil} to access the ddm structure local service.
	 */

	/**
	 * Adds the ddm structure to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructure addDDMStructure(DDMStructure ddmStructure) {
		ddmStructure.setNew(true);

		return ddmStructurePersistence.update(ddmStructure);
	}

	/**
	 * Creates a new ddm structure with the primary key. Does not add the ddm structure to the database.
	 *
	 * @param structureId the primary key for the new ddm structure
	 * @return the new ddm structure
	 */
	@Override
	@Transactional(enabled = false)
	public DDMStructure createDDMStructure(long structureId) {
		return ddmStructurePersistence.create(structureId);
	}

	/**
	 * Deletes the ddm structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureId the primary key of the ddm structure
	 * @return the ddm structure that was removed
	 * @throws PortalException if a ddm structure with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructure deleteDDMStructure(long structureId)
		throws PortalException {
		return ddmStructurePersistence.remove(structureId);
	}

	/**
	 * Deletes the ddm structure from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructure deleteDDMStructure(DDMStructure ddmStructure) {
		return ddmStructurePersistence.remove(ddmStructure);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDMStructure.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return ddmStructurePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return ddmStructurePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return ddmStructurePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return ddmStructurePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return ddmStructurePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDMStructure fetchDDMStructure(long structureId) {
		return ddmStructurePersistence.fetchByPrimaryKey(structureId);
	}

	/**
	 * Returns the ddm structure matching the UUID and group.
	 *
	 * @param uuid the ddm structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure, or <code>null</code> if a matching ddm structure could not be found
	 */
	@Override
	public DDMStructure fetchDDMStructureByUuidAndGroupId(String uuid,
		long groupId) {
		return ddmStructurePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm structure with the primary key.
	 *
	 * @param structureId the primary key of the ddm structure
	 * @return the ddm structure
	 * @throws PortalException if a ddm structure with the primary key could not be found
	 */
	@Override
	public DDMStructure getDDMStructure(long structureId)
		throws PortalException {
		return ddmStructurePersistence.findByPrimaryKey(structureId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ddmStructureLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMStructure.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(ddmStructureLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DDMStructure.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("structureId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(ddmStructureLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMStructure.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");

					StagedModelType stagedModelType = exportActionableDynamicQuery.getStagedModelType();

					long referrerClassNameId = stagedModelType.getReferrerClassNameId();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
							"classNameId");

					if ((referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
							(referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {
						dynamicQuery.add(classNameIdProperty.eq(
								stagedModelType.getReferrerClassNameId()));
					}
					else if (referrerClassNameId == StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {
						dynamicQuery.add(classNameIdProperty.isNotNull());
					}
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDMStructure>() {
				@Override
				public void performAction(DDMStructure ddmStructure)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						ddmStructure);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDMStructure.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddmStructureLocalService.deleteDDMStructure((DDMStructure)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddmStructurePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the ddm structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structures
	 * @param companyId the primary key of the company
	 * @return the matching ddm structures, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructure> getDDMStructuresByUuidAndCompanyId(String uuid,
		long companyId) {
		return ddmStructurePersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of ddm structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structures
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm structures
	 * @param end the upper bound of the range of ddm structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm structures, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructure> getDDMStructuresByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return ddmStructurePersistence.findByUuid_C(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	 * Returns the ddm structure matching the UUID and group.
	 *
	 * @param uuid the ddm structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure
	 * @throws PortalException if a matching ddm structure could not be found
	 */
	@Override
	public DDMStructure getDDMStructureByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return ddmStructurePersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structures
	 * @param end the upper bound of the range of ddm structures (not inclusive)
	 * @return the range of ddm structures
	 */
	@Override
	public List<DDMStructure> getDDMStructures(int start, int end) {
		return ddmStructurePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ddm structures.
	 *
	 * @return the number of ddm structures
	 */
	@Override
	public int getDDMStructuresCount() {
		return ddmStructurePersistence.countAll();
	}

	/**
	 * Updates the ddm structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructure updateDDMStructure(DDMStructure ddmStructure) {
		return ddmStructurePersistence.update(ddmStructure);
	}

	/**
	 * Returns the ddm structure local service.
	 *
	 * @return the ddm structure local service
	 */
	public DDMStructureLocalService getDDMStructureLocalService() {
		return ddmStructureLocalService;
	}

	/**
	 * Sets the ddm structure local service.
	 *
	 * @param ddmStructureLocalService the ddm structure local service
	 */
	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
		this.ddmStructureLocalService = ddmStructureLocalService;
	}

	/**
	 * Returns the ddm structure persistence.
	 *
	 * @return the ddm structure persistence
	 */
	public DDMStructurePersistence getDDMStructurePersistence() {
		return ddmStructurePersistence;
	}

	/**
	 * Sets the ddm structure persistence.
	 *
	 * @param ddmStructurePersistence the ddm structure persistence
	 */
	public void setDDMStructurePersistence(
		DDMStructurePersistence ddmStructurePersistence) {
		this.ddmStructurePersistence = ddmStructurePersistence;
	}

	/**
	 * Returns the ddm structure finder.
	 *
	 * @return the ddm structure finder
	 */
	public DDMStructureFinder getDDMStructureFinder() {
		return ddmStructureFinder;
	}

	/**
	 * Sets the ddm structure finder.
	 *
	 * @param ddmStructureFinder the ddm structure finder
	 */
	public void setDDMStructureFinder(DDMStructureFinder ddmStructureFinder) {
		this.ddmStructureFinder = ddmStructureFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the ddm data provider instance local service.
	 *
	 * @return the ddm data provider instance local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService getDDMDataProviderInstanceLocalService() {
		return ddmDataProviderInstanceLocalService;
	}

	/**
	 * Sets the ddm data provider instance local service.
	 *
	 * @param ddmDataProviderInstanceLocalService the ddm data provider instance local service
	 */
	public void setDDMDataProviderInstanceLocalService(
		com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService) {
		this.ddmDataProviderInstanceLocalService = ddmDataProviderInstanceLocalService;
	}

	/**
	 * Returns the ddm data provider instance persistence.
	 *
	 * @return the ddm data provider instance persistence
	 */
	public DDMDataProviderInstancePersistence getDDMDataProviderInstancePersistence() {
		return ddmDataProviderInstancePersistence;
	}

	/**
	 * Sets the ddm data provider instance persistence.
	 *
	 * @param ddmDataProviderInstancePersistence the ddm data provider instance persistence
	 */
	public void setDDMDataProviderInstancePersistence(
		DDMDataProviderInstancePersistence ddmDataProviderInstancePersistence) {
		this.ddmDataProviderInstancePersistence = ddmDataProviderInstancePersistence;
	}

	/**
	 * Returns the ddm data provider instance finder.
	 *
	 * @return the ddm data provider instance finder
	 */
	public DDMDataProviderInstanceFinder getDDMDataProviderInstanceFinder() {
		return ddmDataProviderInstanceFinder;
	}

	/**
	 * Sets the ddm data provider instance finder.
	 *
	 * @param ddmDataProviderInstanceFinder the ddm data provider instance finder
	 */
	public void setDDMDataProviderInstanceFinder(
		DDMDataProviderInstanceFinder ddmDataProviderInstanceFinder) {
		this.ddmDataProviderInstanceFinder = ddmDataProviderInstanceFinder;
	}

	/**
	 * Returns the ddm data provider instance link local service.
	 *
	 * @return the ddm data provider instance link local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService getDDMDataProviderInstanceLinkLocalService() {
		return ddmDataProviderInstanceLinkLocalService;
	}

	/**
	 * Sets the ddm data provider instance link local service.
	 *
	 * @param ddmDataProviderInstanceLinkLocalService the ddm data provider instance link local service
	 */
	public void setDDMDataProviderInstanceLinkLocalService(
		com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService ddmDataProviderInstanceLinkLocalService) {
		this.ddmDataProviderInstanceLinkLocalService = ddmDataProviderInstanceLinkLocalService;
	}

	/**
	 * Returns the ddm data provider instance link persistence.
	 *
	 * @return the ddm data provider instance link persistence
	 */
	public DDMDataProviderInstanceLinkPersistence getDDMDataProviderInstanceLinkPersistence() {
		return ddmDataProviderInstanceLinkPersistence;
	}

	/**
	 * Sets the ddm data provider instance link persistence.
	 *
	 * @param ddmDataProviderInstanceLinkPersistence the ddm data provider instance link persistence
	 */
	public void setDDMDataProviderInstanceLinkPersistence(
		DDMDataProviderInstanceLinkPersistence ddmDataProviderInstanceLinkPersistence) {
		this.ddmDataProviderInstanceLinkPersistence = ddmDataProviderInstanceLinkPersistence;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the ddm structure layout local service.
	 *
	 * @return the ddm structure layout local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService getDDMStructureLayoutLocalService() {
		return ddmStructureLayoutLocalService;
	}

	/**
	 * Sets the ddm structure layout local service.
	 *
	 * @param ddmStructureLayoutLocalService the ddm structure layout local service
	 */
	public void setDDMStructureLayoutLocalService(
		com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {
		this.ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	/**
	 * Returns the ddm structure layout persistence.
	 *
	 * @return the ddm structure layout persistence
	 */
	public DDMStructureLayoutPersistence getDDMStructureLayoutPersistence() {
		return ddmStructureLayoutPersistence;
	}

	/**
	 * Sets the ddm structure layout persistence.
	 *
	 * @param ddmStructureLayoutPersistence the ddm structure layout persistence
	 */
	public void setDDMStructureLayoutPersistence(
		DDMStructureLayoutPersistence ddmStructureLayoutPersistence) {
		this.ddmStructureLayoutPersistence = ddmStructureLayoutPersistence;
	}

	/**
	 * Returns the ddm structure link local service.
	 *
	 * @return the ddm structure link local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService getDDMStructureLinkLocalService() {
		return ddmStructureLinkLocalService;
	}

	/**
	 * Sets the ddm structure link local service.
	 *
	 * @param ddmStructureLinkLocalService the ddm structure link local service
	 */
	public void setDDMStructureLinkLocalService(
		com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		this.ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	/**
	 * Returns the ddm structure link persistence.
	 *
	 * @return the ddm structure link persistence
	 */
	public DDMStructureLinkPersistence getDDMStructureLinkPersistence() {
		return ddmStructureLinkPersistence;
	}

	/**
	 * Sets the ddm structure link persistence.
	 *
	 * @param ddmStructureLinkPersistence the ddm structure link persistence
	 */
	public void setDDMStructureLinkPersistence(
		DDMStructureLinkPersistence ddmStructureLinkPersistence) {
		this.ddmStructureLinkPersistence = ddmStructureLinkPersistence;
	}

	/**
	 * Returns the ddm structure link finder.
	 *
	 * @return the ddm structure link finder
	 */
	public DDMStructureLinkFinder getDDMStructureLinkFinder() {
		return ddmStructureLinkFinder;
	}

	/**
	 * Sets the ddm structure link finder.
	 *
	 * @param ddmStructureLinkFinder the ddm structure link finder
	 */
	public void setDDMStructureLinkFinder(
		DDMStructureLinkFinder ddmStructureLinkFinder) {
		this.ddmStructureLinkFinder = ddmStructureLinkFinder;
	}

	/**
	 * Returns the ddm structure version local service.
	 *
	 * @return the ddm structure version local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService getDDMStructureVersionLocalService() {
		return ddmStructureVersionLocalService;
	}

	/**
	 * Sets the ddm structure version local service.
	 *
	 * @param ddmStructureVersionLocalService the ddm structure version local service
	 */
	public void setDDMStructureVersionLocalService(
		com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService ddmStructureVersionLocalService) {
		this.ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	/**
	 * Returns the ddm structure version persistence.
	 *
	 * @return the ddm structure version persistence
	 */
	public DDMStructureVersionPersistence getDDMStructureVersionPersistence() {
		return ddmStructureVersionPersistence;
	}

	/**
	 * Sets the ddm structure version persistence.
	 *
	 * @param ddmStructureVersionPersistence the ddm structure version persistence
	 */
	public void setDDMStructureVersionPersistence(
		DDMStructureVersionPersistence ddmStructureVersionPersistence) {
		this.ddmStructureVersionPersistence = ddmStructureVersionPersistence;
	}

	/**
	 * Returns the ddm template local service.
	 *
	 * @return the ddm template local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService getDDMTemplateLocalService() {
		return ddmTemplateLocalService;
	}

	/**
	 * Sets the ddm template local service.
	 *
	 * @param ddmTemplateLocalService the ddm template local service
	 */
	public void setDDMTemplateLocalService(
		com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService ddmTemplateLocalService) {
		this.ddmTemplateLocalService = ddmTemplateLocalService;
	}

	/**
	 * Returns the ddm template persistence.
	 *
	 * @return the ddm template persistence
	 */
	public DDMTemplatePersistence getDDMTemplatePersistence() {
		return ddmTemplatePersistence;
	}

	/**
	 * Sets the ddm template persistence.
	 *
	 * @param ddmTemplatePersistence the ddm template persistence
	 */
	public void setDDMTemplatePersistence(
		DDMTemplatePersistence ddmTemplatePersistence) {
		this.ddmTemplatePersistence = ddmTemplatePersistence;
	}

	/**
	 * Returns the ddm template finder.
	 *
	 * @return the ddm template finder
	 */
	public DDMTemplateFinder getDDMTemplateFinder() {
		return ddmTemplateFinder;
	}

	/**
	 * Sets the ddm template finder.
	 *
	 * @param ddmTemplateFinder the ddm template finder
	 */
	public void setDDMTemplateFinder(DDMTemplateFinder ddmTemplateFinder) {
		this.ddmTemplateFinder = ddmTemplateFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.dynamic.data.mapping.model.DDMStructure",
			ddmStructureLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.dynamic.data.mapping.model.DDMStructure");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDMStructureLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDMStructure.class;
	}

	protected String getModelClassName() {
		return DDMStructure.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmStructurePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = DDMStructureLocalService.class)
	protected DDMStructureLocalService ddmStructureLocalService;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureFinder.class)
	protected DDMStructureFinder ddmStructureFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService;
	@BeanReference(type = DDMDataProviderInstancePersistence.class)
	protected DDMDataProviderInstancePersistence ddmDataProviderInstancePersistence;
	@BeanReference(type = DDMDataProviderInstanceFinder.class)
	protected DDMDataProviderInstanceFinder ddmDataProviderInstanceFinder;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService ddmDataProviderInstanceLinkLocalService;
	@BeanReference(type = DDMDataProviderInstanceLinkPersistence.class)
	protected DDMDataProviderInstanceLinkPersistence ddmDataProviderInstanceLinkPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService ddmStructureLayoutLocalService;
	@BeanReference(type = DDMStructureLayoutPersistence.class)
	protected DDMStructureLayoutPersistence ddmStructureLayoutPersistence;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService ddmStructureLinkLocalService;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMStructureLinkFinder.class)
	protected DDMStructureLinkFinder ddmStructureLinkFinder;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService ddmStructureVersionLocalService;
	@BeanReference(type = DDMStructureVersionPersistence.class)
	protected DDMStructureVersionPersistence ddmStructureVersionPersistence;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService ddmTemplateLocalService;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = DDMTemplateFinder.class)
	protected DDMTemplateFinder ddmTemplateFinder;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}