/**
 * 
 */
package com.punj.app.ecommerce.services.gst;

import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.gstr.GSTHSN;
import com.punj.app.ecommerce.domains.gstr.GSTInvoice;

/**
 * @author admin
 *
 */
public interface GSTRService {

	public List<GSTHSN> retrieveGSTRHSNData(Integer locationId, String finMonth);

	public Map<Customer, Map<String, List<GSTInvoice>>> retrieveGSTRInvoiceData(Integer locationId, String finMonth);

}
