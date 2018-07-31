

window.chartColors = {
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(201, 203, 207)'
};

/**
 * This function will be executed once the dashboard page has loaded successfully
 * 
 */
$(function() {
	
	prepareDates();
	
	
});
	
function prepareDates(){
	var chartDates=dates;
	var chartType='line';
	
	var revenueLabel='Revenue';
	var revenueArray=dashboardData[revenueLabel];
	var revenueConfig=prepareNewWidget(chartType, chartDates, revenueLabel, revenueArray);
	renderWidgetChart('revenue_canvas',revenueConfig);
	
	var discountLabel='Discount';
	var discountArray=dashboardData[discountLabel];
	var discountConfig=prepareNewWidget(chartType, chartDates, discountLabel, discountArray);
	renderWidgetChart('discount_canvas',discountConfig);
	
	var discountPctLabel='Discount %';
	var discountPctArray=dashboardData[discountPctLabel];
	var discountPctConfig=prepareNewWidget(chartType, chartDates, discountPctLabel, discountPctArray);
	renderWidgetChart('discount_percent_canvas',discountPctConfig);
		
	var saleCountLabel='Sales Count';
	var saleCountArray=dashboardData[saleCountLabel];
	var saleCountConfig=prepareNewWidget(chartType, chartDates, saleCountLabel, saleCountArray);
	renderWidgetChart('sales_count_canvas',saleCountConfig);
	
	
	var custCountLabel='Customer Count';
	var custCountArray=dashboardData[custCountLabel];
	var custCountConfig=prepareNewWidget(chartType, chartDates, custCountLabel, custCountArray);
	renderWidgetChart('customer_count_canvas',custCountConfig);
	
	
	/*var revenueLabel='Profit';
	var revenueArray=dashboardData[revenueLabel];
	var revenueConfig=prepareNewWidget(chartType, chartDates, revenueLabel, revenueArray);
	renderWidgetChart('profit_canvas',revenueConfig);*/
	
	
	var basketValueLabel='Basket Value';
	var basketValueArray=dashboardData[basketValueLabel];
	var basketValueConfig=prepareNewWidget(chartType, chartDates, basketValueLabel, basketValueArray);
	renderWidgetChart('basket_value_canvas',basketValueConfig);
	
	
	var basketSizeLabel='Basket Size';
	var basketSizeArray=dashboardData[basketSizeLabel];
	var basketSizeConfig=prepareNewWidget(chartType, chartDates, basketSizeLabel, basketSizeArray);
	renderWidgetChart('basket_size_canvas',basketSizeConfig);
	
}


function renderWidgetChart(chartContainer, chartConfig){
	var ctx = document.getElementById(chartContainer).getContext('2d');
	window.myLine = new Chart(ctx, chartConfig);
}

function prepareNewWidget(chartType, labels, widgetLabel,widgetData){
	var config = {
			type: chartType,
			data: {
				labels: labels,
				datasets: [{
					label: widgetLabel,
					backgroundColor: window.chartColors.orange,
					borderColor: window.chartColors.orange,
					data: widgetData,
					fill: false,
				}]
			},
			options: {
				responsive: true,
				tooltips: {
					mode: 'index',
					intersect: false,
				},
				hover: {
					mode: 'nearest',
					intersect: true
				},
				scales: {
				    xAxes: [{
				        ticks: {
				            autoSkip: false
				        }
				    }]
				}
			}
		};
	
	return config;
}