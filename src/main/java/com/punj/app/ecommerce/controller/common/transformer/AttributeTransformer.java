/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.AttributeDTO;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.AttributeBeanDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class AttributeTransformer {

	private static final Logger logger = LogManager.getLogger();

	private AttributeTransformer() {
		throw new IllegalStateException("AttributeTransformer class");
	}

	/**
	 * This method is used to transform the item attributes information retrieved from the database to bean objects to represent on screen
	 * 
	 * @param attributeDTO
	 * @return The bean object for entity
	 */
	public static AttributeBeanDTO transformAttributeDTO(AttributeDTO attributeDTO) {

		AttributeBeanDTO attributeBeanDTO = new AttributeBeanDTO();

		List<AttributeBean> attributeBeanList = AttributeTransformer.transformAttributes(attributeDTO.getAttributes());
		attributeBeanDTO.setAttributes(attributeBeanList);

		Pager tmpPager = attributeBeanDTO.getPager();
		if(tmpPager !=null) {
			Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.SEARCH_ATTRIBUTES_URL);
			attributeBeanDTO.setPager(pager);			
		}
		logger.info("The attribute details has been transformed successfully");
		return attributeBeanDTO;
	}

	public static List<AttributeBean> transformAttributes(List<Attribute> attributes) {

		List<AttributeBean> attributeBeans = new ArrayList<>(attributes.size());
		AttributeBean attributeBean;

		for (Attribute attribute : attributes) {
			attributeBean = AttributeTransformer.transformAttribute(attribute);
			attributeBeans.add(attributeBean);
		}

		logger.info("The attributes list has been transformed to bean list successfully");

		return attributeBeans;
	}

	public static AttributeBean transformAttribute(Attribute attribute) {

		AttributeBean attributeBean = new AttributeBean();

		attributeBean.setAttributeId(attribute.getAttributeId());
		attributeBean.setCode(attribute.getCode());
		attributeBean.setName(attribute.getName());
		attributeBean.setDescription(attribute.getDescription());
		attributeBean.setValCode(attribute.getValCode());
		attributeBean.setValName(attribute.getValName());
		attributeBean.setValDescription(attribute.getValDesc());
		attributeBean.setValSeqNo(attribute.getValSeqNo());

		logger.info("The attribute data has been transformed to bean successfully");
		return attributeBean;
	}

}
