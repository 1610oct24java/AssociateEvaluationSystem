var config = require('../nightwatch.conf.js');

module.exports = {
  'Invalid Login 1' : function (browser) {
	var data	= browser.globals;
  	data.recruiterLogin(browser, 'lsdkjfhwelkfhewlk', 'lsdkjfhwelkfhewlk');

  }
};
