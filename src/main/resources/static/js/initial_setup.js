/**
 * This JS file has all the code needed for initial setup screen
 */

$.fn.extend({
  animateCss: function(animationName, callback) {
    var animationEnd = (function(el) {
      var animations = {
        animation: 'animationend',
        OAnimation: 'oAnimationEnd',
        MozAnimation: 'mozAnimationEnd',
        WebkitAnimation: 'webkitAnimationEnd',
      };

      for (var t in animations) {
        if (el.style[t] !== undefined) {
          return animations[t];
        }
      }
    })(document.createElement('div'));

    this.addClass('animated ' + animationName).one(animationEnd, function() {
      $(this).removeClass('animated ' + animationName);

      if (typeof callback === 'function') callback();
    });

    return this;
  },
});

$(function() {
	
	$('#btnSetupStart').click(function(){
		$('#welcomeScreen').animateCss('slideOutUp', function(){
			$('#welcomeScreen').addClass('d-none');
		});
		
		$('#step1').removeClass('d-none');
		$('#step1').animateCss('fadeInUp');
	});
	
	
	$('.feature-list').hover(function () {
		$(this).animateCss('bounce');
	});
	
	$('.next-btn').click(function() {
		var currentSetupStep=$(this).closest('.setup-step');
		var nextSetupStep=currentSetupStep.next();

		if(validateStep(currentSetupStep[0].id)){
			showNextStep(currentSetupStep[0].id, nextSetupStep[0].id, nextSetupStep[0]);
		}
		
		
		
	});

	$('.prev-btn').click(function() {
		var currentSetupStep=$(this).closest('.setup-step');
		var previousSetupStep=currentSetupStep.prev();
		
		showPreviousStep(currentSetupStep[0].id, previousSetupStep[0].id, currentSetupStep[0]);
		
	});

	$('.finish-btn').click(function() {
		if(validateAdminDetails()){
			$('#configureLocationForm').submit();
		}
	});

});

function showNextStep(currentId,nextId,nextStep){
	$('#'+currentId).animateCss('bounceOutLeft', function(){
		$('#'+currentId).hide();
		$('#'+nextId).css('display','inline-block');
	});
	
	$("#progressbar li").eq($('.setup-step').index(nextStep)).addClass("active");
	
	$('#'+nextId).animateCss('bounceInRight');
	
}


function showPreviousStep(currentId,previousId,currentStep){
	$('#'+currentId).animateCss('bounceOutRight', function(){
		$('#'+currentId).hide();
		$('#'+previousId).css('display','inline-block');
	});
	
	$("#progressbar li").eq($('.setup-step').index(currentStep)).removeClass("active");
	
	$('#'+previousId).addClass('animated bounceInLeft');
	
}

