/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.models.common.CommerceMultipartFile;
import com.punj.app.ecommerce.models.item.ItemImageBean;

/**
 * @author admin
 *
 */
public class ItemTransformer {

	private static final Logger logger = LogManager.getLogger();

	private ItemTransformer() {
		throw new IllegalStateException("ItemTransformer class");
	}

	public static List<ItemImageBean> tranformItemImages(List<ItemImage> itemImages) {

		List<ItemImageBean> itemImageBeans = new ArrayList<>(itemImages.size());
		ItemImageBean itemImageBean;
		for(ItemImage itemImage:itemImages) {
			itemImageBean=ItemTransformer.tranformItemImage(itemImage);
			itemImageBeans.add(itemImageBean);
		}
		
		logger.info("The item images has been transformed to item images bean successfully");
		return itemImageBeans;
	}

	public static ItemImageBean tranformItemImage(ItemImage itemImage) {
		ItemImageBean itemImageBean = new ItemImageBean();

		itemImageBean.setCreatedBy(itemImage.getCreatedBy());
		itemImageBean.setCreatedDate(itemImage.getCreatedDate());
		
		itemImageBean.setImageType(itemImage.getImageType());
		itemImageBean.setImageURL(itemImage.getImageURL());
		itemImageBean.setItemId(itemImage.getItemId());
		itemImageBean.setItemImageId(itemImage.getItemImageId());
		itemImageBean.setName(itemImage.getName());
		
		MultipartFile multipartFile = new CommerceMultipartFile(itemImage.getImageData(), itemImage.getImageURL(), itemImage.getImageType());

		itemImageBean.setImageData(multipartFile);
		logger.info("The item image has been transformed to item image bean successfully");
		
		return itemImageBean;
	}

	
	public static List<ItemImage> tranformItemImageBeans(List<ItemImageBean> itemImageBeans) throws IOException {

		List<ItemImage> itemImages = new ArrayList<>(itemImageBeans.size());
		ItemImage itemImage;
		for(ItemImageBean itemImageBean:itemImageBeans) {
			itemImage=ItemTransformer.tranformItemImageBean(itemImageBean);
			itemImages.add(itemImage);
		}
		
		logger.info("The item image beans has been transformed to item images successfully");
		return itemImages;
	}

	public static ItemImage tranformItemImageBean(ItemImageBean itemImageBean) throws IOException {
		ItemImage itemImage = new ItemImage();

		itemImage.setCreatedBy(itemImageBean.getCreatedBy());
		itemImage.setCreatedDate(itemImageBean.getCreatedDate());
		
		itemImage.setItemId(itemImageBean.getItemId());
		itemImage.setItemImageId(itemImageBean.getItemImageId());
		itemImage.setName(itemImageBean.getName());
		
		MultipartFile multipartFile = itemImageBean.getImageData();

		if(multipartFile!=null) {
			itemImage.setImageData(multipartFile.getBytes());
			itemImage.setImageType(multipartFile.getContentType());
			itemImage.setImageURL(multipartFile.getName());

		}
			
		logger.info("The item image has been transformed to item image bean successfully");
		
		return itemImage;
	}	
	
}
