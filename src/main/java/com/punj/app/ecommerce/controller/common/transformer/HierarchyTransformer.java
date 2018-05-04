/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.HierarchyDTO;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.HierarchyBeanDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class HierarchyTransformer {

	private static final Logger logger = LogManager.getLogger();

	private HierarchyTransformer() {
		throw new IllegalStateException("HierarchyTransformer class");
	}

	/**
	 * This method is used to transform the hierarchy information retrieved from the database to bean objects to represent on screen
	 * 
	 * @param hierarchyDTO
	 * @return The bean object for entity
	 */
	public static HierarchyBeanDTO transformHierarchyDTO(HierarchyDTO hierarchyDTO) {

		HierarchyBeanDTO hierarchiesBeanDTO = new HierarchyBeanDTO();

		List<HierarchyBean> hierarchiesBeanList= HierarchyTransformer.transformHierarchies(hierarchyDTO.getHierarchies());
		hierarchiesBeanDTO.setHierarchies(hierarchiesBeanList);
		
		Pager tmpPager = hierarchyDTO.getPager();
		Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
				ViewPathConstants.SEARCH_HIERARCHIES_URL);
		hierarchiesBeanDTO.setPager(pager);
		
		logger.info("The hierarchy details has been transformed successfully");
		return hierarchiesBeanDTO;
	}

	public static List<HierarchyBean> transformHierarchies(List<Hierarchy> hierarchies) {

		List<HierarchyBean> hierarchyBeans = new ArrayList<>(hierarchies.size());
		HierarchyBean hierarchyBean;

		for (Hierarchy hierarchy : hierarchies) {
			hierarchyBean = HierarchyTransformer.transformHierarchy(hierarchy);
			hierarchyBeans.add(hierarchyBean);
		}

		logger.info("The hierarchies list has been transformed to bean list successfully");

		return hierarchyBeans;
	}

	public static HierarchyBean transformHierarchy(Hierarchy hierarchy) {

		HierarchyBean hierarchyBean = new HierarchyBean();

		hierarchyBean.setHierarchyId(hierarchy.getHierarchyId());
		hierarchyBean.setSortOrder(hierarchy.getSortOrder());
		hierarchyBean.setCode(hierarchy.getCode());
		hierarchyBean.setCreatedBy(hierarchy.getCreatedBy());
		hierarchyBean.setName(hierarchy.getName());
		hierarchyBean.setDescription(hierarchy.getDescription());
		hierarchyBean.setHiddenFlag(hierarchy.getHiddenFlag());

		logger.info("The hierarchy data has been transformed to bean successfully");
		return hierarchyBean;
	}
	
	public static Hierarchy transformHierarchyBean(HierarchyBean hierarchyBean) {

		Hierarchy hierarchy = new Hierarchy();

		hierarchy.setHierarchyId(hierarchyBean.getHierarchyId());
		hierarchy.setSortOrder(hierarchyBean.getSortOrder());
		hierarchy.setCode(hierarchyBean.getCode());
		hierarchy.setCreatedBy(hierarchyBean.getCreatedBy());
		hierarchy.setName(hierarchyBean.getName());
		hierarchy.setDescription(hierarchyBean.getDescription());
		hierarchy.setHiddenFlag(hierarchyBean.getHiddenFlag());

		logger.info("The hierarchy bean data has been transformed to domain object successfully");
		return hierarchy;
	}	
	

}
