/**
 * This file contains all the classes needed for a item creation screen
 */

var SearchBean = function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

});

var AttributeBean = function() {
	this.attributeId;
	this.name;
	this.code;
	this.description;
	this.valCode;
	this.valName;
	this.valDescription;
	this.valSeqNo;
	this.attrValList;
}

$
		.extend(
				AttributeBean.prototype,
				{

					initialize : function(attributeId, name, code, description, valCode, valName, valDescription, valSeqNo) {
						this.attributeId = attributeId;
						this.name = name;
						this.code = code;
						this.description = description;
						this.valCode = valCode;
						this.valName = valName;
						this.valDescription = valDescription;
						this.valSeqNo = valSeqNo;
						this.attrValList = new Array();
					},
					renderSelectedAttribute : function(selectedAttributes) {
						if (selectedAttributes && selectedAttributes.length > 0) {

							var outputHtml = '<div class="row">';
							outputHtml += '<div class="col-xs-12 col-sm-12 col-md-12">';
							outputHtml += '<div class="form-group">';
							outputHtml += '<label><small><span>Selected Item Attributes</span></small></label>';
							outputHtml += '<div class="input-group">';
							outputHtml += '<div class="list-group col-12">';

							var outputColRightHtml = '';

							$.each(selectedAttributes, function(index, attribute) {
								outputColRightHtml += '<div class="row">';
								outputColRightHtml += '<div class="col">';
								outputColRightHtml += '<a class="list-group-item list-group-item-action" id="attrItem' + index + '" onClick="selectAttr(this, '
										+ index + ')">';
								outputColRightHtml += '<div class="form-check float-left">';
								outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.attributeId"';
								outputColRightHtml += 'name="style.selectedAttributes[' + index + '].attributeId" value="' + attribute.attributeId+ '"></input> ';
								outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.name"';
								outputColRightHtml += 'name="style.selectedAttributes[' + index + '].name" value="' + attribute.name+ '"></input> ';
								outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.code"';
								outputColRightHtml += 'name="style.selectedAttributes[' + index + '].code" value="' + attribute.code+ '"></input> ';
								outputColRightHtml += '</div>';
								outputColRightHtml += '<span class="mx-4">' + attribute.name + '</span></a>';
								outputColRightHtml += '</div>';
								outputColRightHtml += '<div class="col-1">';
								outputColRightHtml += '<button type="button" id="btnDeleteAttr"';
								outputColRightHtml += 'onClick="deleteAttr(\'' + attribute.code
										+ '\')" class="btn btn-danger btn-sm mx-1"><i class="far fa-trash-alt fa-2x"></i></button> ';

								outputColRightHtml += '</div>';
								outputColRightHtml += '</div>';

							});

							outputHtml += outputColRightHtml;
							outputHtml += '</div>';
							outputHtml += '</div>';
							outputHtml += '</div>';
							outputHtml += '</div>';
							outputHtml += '</div>';

							$('#selectedAttributes').html(outputHtml);
						} else {
							$('#selectedAttributes').html('');
						}

					},
					renderSKUs : function(selectedAttributes, finalSKUAttrVals) {
						if (selectedAttributes && selectedAttributes.length > 0) {
							var outerStructureHtml = '';
							var skuName='';
							$
									.each(
											finalSKUAttrVals,
											function(index, skuCombination) {

												skuName=style_name;
												
												var priceHtml = '<div class="form-group">';
												priceHtml += '<label><small><span>SKU Price</span></small></label>';
												priceHtml += '<div class="input-group">';
												priceHtml += '<input type="number" step="0.01" class="form-control input-sm"';
												priceHtml += 'id="skus'+index+'.itemOptions.currentPrice" name="skus['+index+'].itemOptions.currentPrice"';
												priceHtml += 'placeholder="Enter SKU Price" value="'+ style_price.toFixed(2) + '"></input>';
												priceHtml += '</div>';
												priceHtml += '</div>';

												var inventoryHtml = '<div class="form-group">';
												inventoryHtml += '<label><small><span>SKU Initial Stock Count</span></small></label>';
												inventoryHtml += '<div class="input-group">';
												inventoryHtml += '<input type="number" class="form-control input-sm"';
												inventoryHtml += 'id="skus'+index+'.itemOptions.stockStatus" name="skus['+index+'].itemOptions.stockStatus"';
												inventoryHtml += 'placeholder="Enter SKU Price" value="0.00"></input>';
												inventoryHtml += '</div>';
												inventoryHtml += '</div>';

												var imageHtml = '<div class="card-text preview-thumbnail-sku text-center">';
												imageHtml += '<img src="' + style_image_url + '" class="img-thumbnail"/><br/>';
												imageHtml += '<label><small><span>' + style_image_name + '</span></small></label>';
												imageHtml += '</div>';
												
												imageHtml += '<input type="hidden" id="skus'+index+'.itemImages0.name"';
												imageHtml += 'name="skus['+index+'].itemImages[0].name"';
												imageHtml += 'value="'+style_image_name+'"></input>';
												imageHtml += '<input type="hidden" id="skus'+index+'.itemImages0.imageURL"';
												imageHtml += 'name="skus['+index+'].itemImages[0].imageURL"';
												imageHtml += 'value="'+style_image_url_actual+'"></input>';
												
												imageHtml += '<input type="hidden" id="skus'+index+'.itemImages0.imageType"';
												imageHtml += 'name="skus['+index+'].itemImages[0].imageType"';
												imageHtml += 'value="'+style_image_type+'"></input>';
												imageHtml += '<input type="hidden" id="skus'+index+'.itemImages0.baseEncodedImage"';
												imageHtml += 'name="skus['+index+'].itemImages[0].baseEncodedImage"';
												imageHtml += 'value="'+style_encoded_image+'"></input>';

												var attributesHtml = '';
												attributesHtml += '<div class="row">';
												attributesHtml += '<div class="col-12">';
												attributesHtml += '<label><small><span>Selected SKU Attributes and Values</span></small></label>';
												attributesHtml += '</div>';
												attributesHtml += '</div>';

												$.each(skuCombination, function(attrIndex, attrVal) {
													attributesHtml += '<div class="row">';
													attributesHtml += '<div class="col">';
													attributesHtml += '<b>'+attrVal.name + '</b>  :  ';
													attributesHtml += '</div>';
													attributesHtml += '<div class="col">';
													attributesHtml += attrVal.valName;
													attributesHtml += '<input type="hidden" id="skus'+index+'.selectedAttributes'+attrIndex+'.attributeId"';
													attributesHtml += 'name="skus['+index+'].selectedAttributes['+attrIndex+'].attributeId"';
													attributesHtml += 'value="'+attrVal.attributeId+'"></input>';
													
													skuName+=' '+attrVal.valCode;
													
													attributesHtml += '<input type="hidden" id="skus'+index+'.selectedAttributes'+attrIndex+'.valCode"';
													attributesHtml += 'name="skus['+index+'].selectedAttributes['+attrIndex+'].valCode"';
													attributesHtml += 'value="'+attrVal.valCode+'"></input>';													
													
													
													attributesHtml += '<input type="hidden" id="skus'+index+'.selectedAttributes'+attrIndex+'.name"';
													attributesHtml += 'name="skus['+index+'].selectedAttributes['+attrIndex+'].name"';
													attributesHtml += 'value="'+attrVal.name+'"></input>';
													
													attributesHtml += '<input type="hidden" id="skus'+index+'.selectedAttributes'+attrIndex+'.valName"';
													attributesHtml += 'name="skus['+index+'].selectedAttributes['+attrIndex+'].valName"';
													attributesHtml += 'value="'+attrVal.valName+'"></input>';													
													
													
													attributesHtml += '</div>';
													attributesHtml += '</div>';
												});

												
												
												var skuNameHtml = '<div class="form-group">';
												skuNameHtml += '<label><small><span>SKU Name</span></small></label>';
												skuNameHtml += '<div class="input-group">';
												skuNameHtml += '<input type="text" class="form-control input-sm"';
												skuNameHtml += 'id="skus'+index+'.name" name="skus['+index+'].name"';
												skuNameHtml += 'placeholder="Enter SKU Name" value="'+ skuName + '"></input>';
												skuNameHtml += '</div>';
												skuNameHtml += '</div>';
												
												outerStructureHtml += '<div class="row">';
												outerStructureHtml += '<div class="col-2">';
												outerStructureHtml += imageHtml;
												outerStructureHtml += '</div>';
												outerStructureHtml += '<div class="col">';
												outerStructureHtml += '<div class="col-12">';
												outerStructureHtml += skuNameHtml;
												outerStructureHtml += '</div>';
												outerStructureHtml += '<div class="col-12">';
												outerStructureHtml += priceHtml;
												outerStructureHtml += '</div>';
												outerStructureHtml += '</div>';
												outerStructureHtml += '<div class="col">';
												outerStructureHtml += inventoryHtml;
												outerStructureHtml += attributesHtml;
												outerStructureHtml += '</div>';
												outerStructureHtml += '</div>';

											});
							$('#skuDataList').html(outerStructureHtml);
							$('#skuDetails').removeClass('d-none');
							$('#skuTitleDiv').html('<span>SKU details</span><span class="mx-2"><b>('+finalSKUAttrVals.length+' SKUs Generated)</b></span>');
							

						} else {
							$('#skuDataList').html('');
							$('#skuDetails').addClass('d-none');
						}

					},
					generateSKUCombinations : function(selectedAttributes) {
						var cloneAttributeList = $.extend(true, [], selectedAttributes);
						var finalSKUCombinationList = new Array();
						var skuCombination;

						if (cloneAttributeList && cloneAttributeList.length > 1) {

							finalSKUCombinationList = this.calculateSKUFromList(cloneAttributeList);

						} else if (cloneAttributeList && cloneAttributeList.length == 1) {

							$.each(cloneAttributeList, function(index, attribute) {
								$.each(attribute.attrValList, function(index, attrVal) {
									skuCombination = new Array();
									skuCombination.push(attrVal);
									finalSKUCombinationList.push(skuCombination);
								});
							});

						}
						return finalSKUCombinationList;
					},
					calculateSKUFromList : function(attributeList) {

						if (attributeList && attributeList.length == 2) {
							return this.combineAttrValues(attributeList[0], attributeList[1]);
						} else if (attributeList && attributeList.length > 2) {
							var reducedAttr = attributeList[0];
							var reducedAttrList = $.extend(true, [], attributeList);
							reducedAttrList.splice(0, 1);
							var skuCombinations = this.calculateSKUFromList(reducedAttrList);
							return this.combineSKUCombinations(reducedAttr, skuCombinations);
						}
					},
					combineSKUCombinations : function(attrOne, skuCombinations) {
						var attrOneVals = attrOne.attrValList;
						var finalCombinations = new Array();
						var newCombination;
						$.each(skuCombinations, function(index, skuCombination) {
							$.each(attrOneVals, function(index, attrOneVal) {
								newCombination = $.extend(true, [], skuCombination);
								newCombination.push(attrOneVal);
								finalCombinations.push(newCombination);
							});

						});

						return finalCombinations;

					},
					combineAttrValues : function(attrOne, attrTwo) {
						var attrOneVals = attrOne.attrValList;
						var attrTwoVals = attrTwo.attrValList;

						var skuCombination;
						var finalCombinations = new Array();

						$.each(attrOneVals, function(index, attrOneVal) {
							$.each(attrTwoVals, function(index, attrTwoVal) {
								skuCombination = new Array();
								skuCombination.push(attrOneVal);
								skuCombination.push(attrTwoVal);
								finalCombinations.push(skuCombination);
							});

						});

						return finalCombinations;

					}

				});