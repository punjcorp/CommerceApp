package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.HierarchyTransformer;
import com.punj.app.ecommerce.domains.item.HierarchyDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.HierarchyBeanDTO;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class HierarchyLookupController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@PostMapping(ViewPathConstants.SEARCH_HIERARCHIES_URL)
	@ResponseBody
	public List<HierarchyBean> lookupHierarchies(@RequestBody @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		HierarchyBeanDTO hierarchiesBeanDTO = null;
		if (bindingResult.hasErrors())
			return null;
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			HierarchyDTO hierarchiesDTO = this.commonService.retrieveHierarchyByText(searchBean.getSearchText(), pager);
			hierarchiesBeanDTO = HierarchyTransformer.transformHierarchyDTO(hierarchiesDTO);

			logger.info("The hierarchies has been queries successfully based on the requested keywords");

		} catch (Exception e) {
			logger.error("There is an error while retrieving hierarchies for lookup screen", e);
			return null;
		}

		return hierarchiesBeanDTO.getHierarchies();
	}

}
